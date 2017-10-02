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

public class ItemExtractor extends Extractor {

	public ItemExtractor(Target target) {
		super(target, "Item");
	}

	@Override
	public void parse(WzObject<?, ?> parent, String path) {

		if (path.contains("Special")) {
			return;
		}

		if (path.contains("Pet")) {
			parseItem(parent, "stand0/0");
			return;
		}

		for (WzObject<?, ?> item : parent) {
			parseItem(item, "info/icon");
		}
	}

	private void parseItem(WzObject<?, ?> item, String imagePath) {
		String path = item.getFullPath();
		int id = getId(path);
		System.out.println(path + " id: " + id);

		WzProperty<?> image = (WzProperty<?>) item.getChildByPath(imagePath);
		extractImage(image, path);
	}
}
