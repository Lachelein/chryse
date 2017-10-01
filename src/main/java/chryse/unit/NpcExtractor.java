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

package chryse.unit;

import java.awt.Image;
import java.util.HashSet;
import java.util.Set;

import chryse.Extractable;
import chryse.Target;
import wz.WzImage;
import wz.WzObject;
import wz.WzProperty;
import wz.common.PNG;

public class NpcExtractor extends Extractable {

	Set<Integer> dumped = new HashSet<Integer>();

	public NpcExtractor(Target target) {
		super(target, "Npc");
	}

	@Override
	public int getId(String path) {
		String[] split = path.split("/");
		int id = 0;

		for (String s : split) {

			// if (s.contains("info") || s.contains("icon")) {
			// break;
			// }

			if (s.contains(".img")) {
				s = s.substring(0, s.length() - 4);
				id = Integer.parseInt(s);
				break;
			}
		}

		return id;
	}

	@Override
	public void subExtract() {
		WzObject<?, ?> root = wzFile.getRoot();
		iterateInternal(root);
	}

	private void iterateInternal(WzObject<?, ?> root) {
		if (root.getChildren() == null) {
			return;
		}

		for (WzObject<?, ?> child : root) {
			if (child instanceof WzProperty<?>) {
				WzProperty<?> obj = (WzProperty<?>) child;

				if (obj.getValue() instanceof PNG) {
					String in = obj.getFullPath();

					int id = getId(in);

					if (id == 0) {
						continue;
					}

					if (dumped.contains(id)) {
						continue;
					}

					PNG value = (PNG) obj.getValue();
					Image img = value.getImage(false);

					if (img.getWidth(null) <= 4 || img.getHeight(null) <= 4) {
						continue;
					}

					System.out.println(in + " has ID of " + id);

					dumped.add(id);

					String out = getOutPath(in);

					exportImage(out, img);
				}
			}

			iterateInternal(child);

			if (child instanceof WzImage) {
				((WzImage) child).unparse();
			}
		}
	}

	@Override
	public String getSubOutPath(String in) {
		return Integer.toString(getId(in));
	}
}
