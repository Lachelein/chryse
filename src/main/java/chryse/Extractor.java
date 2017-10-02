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

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import wz.WzFile;
import wz.WzImage;
import wz.WzObject;
import wz.WzProperty;
import wz.io.WzMappedInputStream;

public abstract class Extractor {

	protected WzFile wzFile;
	protected String wz;
	protected boolean fullDump;

	public Extractor(Target target, boolean fullDump, String wz) {
		this.wz = wz;
		this.fullDump = fullDump;

		WzMappedInputStream stream = new WzMappedInputStream(Paths.get("wz/" + wz + ".wz"));
		stream.setKey(target.KEY);

		this.wzFile = new WzFile("wz/" + wz + ".wz", target.VERSION);
		this.wzFile.parse(stream);
	}

	protected void exportImage(String out, Image img) {

		try {

			File file = new File("dump/" + wz + "/" + out + ".png");

			if (file.exists()) {
				file.delete();
			} else {
				file.mkdirs();
				file.createNewFile();
			}

			ImageIO.write((BufferedImage) img, "png", file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		img.flush();
	}

	protected void extract() {
		System.out.println("Started extracting " + wz + "...");
		WzObject<?, ?> root = wzFile.getRoot();
		internalExtract(root);
		System.out.println("Finished extracting " + wz + "...");
	}

	private void internalExtract(WzObject<?, ?> root) {
		if (root.getChildren() == null) {
			return;
		}

		for (WzObject<?, ?> child : root) {
			if (child instanceof WzProperty<?>) {
				WzProperty<?> obj = (WzProperty<?>) child;
				String in = obj.getFullPath();

				subExtract(obj, in);
			}

			internalExtract(child);

			if (child instanceof WzImage) {
				((WzImage) child).unparse();
			}
		}
	}

	protected String getOutPath(String in) {
		if (fullDump) {
			return in;
		} else {
			return getSubOutPath(in);
		}
	}

	public abstract void subExtract(WzProperty<?> obj, String in);

	public abstract int getId(String path);

	public abstract String getSubOutPath(String in);

}
