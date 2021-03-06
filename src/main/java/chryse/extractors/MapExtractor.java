/*
 	This file is part of Lachelein: MapleStory Web Database
 	Copyright (C) 2017  Alan Morel <alan.morel@nyu.edu>
	Copyright (C) 2017  Brenterino <therealspookster@gmail.com>

	Permission is hereby granted, free of charge, to any person obtaining
	a copy of this software and associated documentation files (the
	"Software"), to deal in the Software without restriction, including
	without limitation the rights to use, copy, modify, merge, publish,
	distribute, sublicense, and/or sell copies of the Software, and to
	permit persons to whom the Software is furnished to do so, subject
	to the following conditions:

	The above copyright notice and this permission notice shall be included
	in all copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
	OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
	THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
	OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
	ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
	OTHER DEALINGS IN THE SOFTWARE.
*/

package chryse.extractors;

import chryse.Database;
import chryse.Extractor;
import chryse.Target;
import chryse.Utility;
import chryse.entities.map.Map;
import chryse.entities.map.MapMonster;
import chryse.entities.map.MapNPC;
import chryse.entities.map.MapPortal;
import wz.WzObject;
import wz.WzProperty;
import wz.common.WzDataTool;

public class MapExtractor extends Extractor {

	public MapExtractor(Target target) {
		super(target, "Map");
	}

	@Override
	public void parse(WzObject<?, ?> parent, String path) {

		if (path.length() > 13 && !path.contains(".wz/M")) {
			return;
		}

		String[] block = {
				"MapHelper",
				"AreaCode",
				"Obj",
				"WorldMap",
				"Back",
				"Tile",
				"Physics"
		};

		if (Utility.contains(path, block)) {
			return;
		}

		WzObject<?, ?> miniMap = parent.getChild("miniMap");
		if (miniMap != null) {
			WzProperty<?> image = (WzProperty<?>) miniMap.getChild("canvas");
			extractImage(image, path);
		}
		WzObject<?, ?> info = parent.getChild("info");

		int id = getId(path);
		Map map = Database.getMap(id);

		String bgm = WzDataTool.getString(info, "bgm", "");
		map.bgm = bgm.length() > 0 ? bgm.split("/")[1] : bgm;

		map.returnMap = WzDataTool.getInteger(info, "returnMap", -1);
		map.mapMark = WzDataTool.getString(info, "mapMark", "");

		addLife(parent, map);
		addPortals(parent, map);
	}

	private void addPortals(WzObject<?, ?> parent, Map map) {
		WzObject<?, ?> portals = parent.getChild("portal");

		if (portals == null) {
			return;
		}

		for (WzObject<?, ?> child : portals) {
			int destination = WzDataTool.getInteger(child, "tm", -1);

			MapPortal portal = new MapPortal(destination);
			portal.x = WzDataTool.getInteger(child, "x", -1);
			portal.y = WzDataTool.getInteger(child, "y", -1);

			map.add(portal);
		}
	}

	private void addLife(WzObject<?, ?> parent, Map map) {
		WzObject<?, ?> life = parent.getChild("life");

		if (life == null) {
			return;
		}

		for (WzObject<?, ?> child : life) {
			int id = Integer.parseInt(WzDataTool.getString(child, "id", "0"));
			int x = WzDataTool.getInteger(child, "x", -1);
			int y = WzDataTool.getInteger(child, "y", -1);
			String type = WzDataTool.getString(child, "type", "");

			if (type.equals("n")) {
				MapNPC npc = new MapNPC(id, x, y);
				map.add(npc);
			} else if (type.equals("m")) {
				MapMonster monster = new MapMonster(id, x, y);
				map.add(monster);
			}
		}
	}
}
