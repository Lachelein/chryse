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

package chryse;

import java.nio.file.Paths;

import wz.WzFile;
import wz.WzImage;
import wz.WzObject;
import wz.WzProperty;
import wz.io.WzMappedInputStream;

public abstract class Extractor {

	private WzFile wzFile;
	private String wz;

	public Extractor(Target target, String wz) {
		this.wz = wz;

		WzMappedInputStream stream = new WzMappedInputStream(Paths.get("wz/" + wz + ".wz"));
		stream.setKey(target.key);

		this.wzFile = new WzFile("wz/" + wz + ".wz", target.version);
		this.wzFile.parse(stream);
	}

	protected void extract() {
		WzObject<?, ?> root = wzFile.getRoot();
		internalExtract(root);
	}

	private void internalExtract(WzObject<?, ?> root) {
		if (root.getChildren() == null) {
			return;
		}

		String path = root.getFullPath();

		if (path.contains(".img")) {
			parse(root, path);
			return;
		}

		System.out.println("Extracting " + path);

		for (WzObject<?, ?> child : root) {

			internalExtract(child);

			if (child instanceof WzImage) {
				((WzImage) child).unparse();
			}
		}
	}

	protected int getId(String path) {
		String[] split = path.split("/");
		int id = 0;

		for (String s : split) {

			if (s.contains(".img")) {
				s = s.substring(0, s.length() - 4);
			}

			if (Utility.isNumeric(s)) {
				id = Math.max(id, Integer.parseInt(s));
			}
		}

		return id;
	}

	protected void extractImage(WzProperty<?> icon, String path) {
		String out = Integer.toString(getId(path));
		Utility.extractImage(icon, wz, out);
	}

	protected void extractSound(WzProperty<?> song, String name) {
		Utility.extractMP3(song, name);
	}

	protected abstract void parse(WzObject<?, ?> parent, String path);

}
