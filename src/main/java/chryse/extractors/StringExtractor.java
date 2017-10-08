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

import java.util.ArrayList;

import chryse.Extractor;
import chryse.Target;
import chryse.Utility;
import wz.WzObject;
import wz.WzProperty;
import wz.common.WzDataTool;

public class StringExtractor extends Extractor {

	public StringExtractor(Target target) {
		super(target, "String");
	}

	@Override
	public void parse(WzObject<?, ?> parent, String path) {

		String[] block = {
				"EULA",
				"GLcloneC",
				"MonsterBook",
				"PetDialog",
				"ToolTipHelp",
				"TransferWorld"
		};

		if (Utility.contains(path, block)) {
			return;
		}

		System.out.println(path);

		String[] nonEquips = {
				"Cash",
				"Consume",
				"Etc",
				"Ins",
				"Pet"
		};

		if (Utility.contains(path, nonEquips)) {
			for (WzObject<?, ?> item : parent) {
				int id = getId(item.getFullPath());
				String desc = WzDataTool.getString(item, "desc", "None");
				String name = WzDataTool.getString(item, "name", "None");

			}
		} else if (path.contains("Eqp")) {
			WzObject<?, ?> equipCategories = parent.getChild("Eqp");
			for (WzObject<?, ?> category : equipCategories) {
				for (WzObject<?, ?> item : category) {
					int id = getId(item.getFullPath());
					String name = WzDataTool.getString(item, "desc", "None");

				}
			}
		} else if (path.contains("Map")) {
			for (WzObject<?, ?> mapCategory : parent) {
				for (WzObject<?, ?> map : mapCategory) {
					int id = getId(map.getFullPath());
					String mapName = WzDataTool.getString(map, "mapName", "None");
					String streetName = WzDataTool.getString(map, "streetName", "None");

				}
			}
		} else if (path.contains("Mob")) {
			for (WzObject<?, ?> mob : parent) {
				int id = getId(mob.getFullPath());
				String name = WzDataTool.getString(mob, "name", "None");

			}
		} else if (path.contains("Npc")) {
			for (WzObject<?, ?> npc : parent) {
				int id = getId(npc.getFullPath());
				String func = WzDataTool.getString(npc, "func", "None");
				String name = WzDataTool.getString(npc, "name", "None");

			}
		} else if (path.contains("Skill")) {
			if (path.length() < 7) {
				return;
			}
			for (WzObject<?, ?> skill : parent) {
				int id = getId(skill.getFullPath());
				String desc = WzDataTool.getString(skill, "desc", "None");
				String name = WzDataTool.getString(skill, "name", "None");
				ArrayList<String> levels = new ArrayList<String>();

				int level = 0;

				while (true) {
					WzObject<?, ?> levelObj = skill.getChild("h" + level);

					if (levelObj == null) {
						break;
					}

					String levelDescription = WzDataTool.getString(levelObj, "h" + level, "None");
					levels.add(levelDescription);

					level++;
				}
			}
		}

	}

	private void dumpIcon(WzObject<?, ?> skill, String imagePath) {
		String path = skill.getFullPath();

		WzProperty<?> image = (WzProperty<?>) skill.getChildByPath(imagePath);
		extractImage(image, path);
	}

	@Override
	protected void finishExtraction() {

	}
}
