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

import chryse.Extractor;
import chryse.Target;
import wz.WzObject;
import wz.WzProperty;
import wz.common.WzDataTool;

public class MapExtractor extends Extractor {

	public MapExtractor(Target target) {
		super(target, "Map");
	}

	@Override
	public void parse(WzObject<?, ?> parent, String path) {
		if (path.length() > 10 && !path.contains("wz/Map.wz/M")) {
			return;
		}

		if (path.contains("MapHelper") || path.contains("AreaCode")) {
			return;
		}

		int id = getId(path);
		System.out.println(path);

		WzObject<?, ?> info = parent.getChild("info");

		WzObject<?, ?> mapMapObj = info.getChild("mapMark");
		String mapMark = WzDataTool.getString((WzProperty<?>) mapMapObj, "None");

		WzObject<?, ?> miniMap = parent.getChild("miniMap");

		if (miniMap != null) {
			WzProperty<?> image = (WzProperty<?>) miniMap.getChild("canvas");
			extractImage(image, path);
		}

		System.out.println("MAP ID: " + id + " mapMark: " + mapMark);
	}

}
