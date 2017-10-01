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
import wz.WzObject;
import wz.WzProperty;
import wz.common.PNG;
import wz.io.WzMappedInputStream;

public abstract class Extractable {

	protected WzFile wzFile;
	protected String wz;

	public Extractable(Target target, String wz) {
		this.wz = wz;

		WzMappedInputStream stream = new WzMappedInputStream(Paths.get("wz/" + wz + ".wz"));
		stream.setKey(target.KEY);

		wzFile = new WzFile("wz/" + wz + ".wz", target.VERSION);
		wzFile.parse(stream);
	}

	protected void exportImage(String path, WzObject<?, ?> export) {
		int id = getId(path);

		System.out.println(path + " has ID of " + id);

		if (id == 0) {
			return;
		}

		WzProperty<?> prop = (WzProperty<?>) export;
		PNG value = (PNG) prop.getValue();

		Image img = value.getImage(false);

		try {
			File file = new File("dump/" + wz + "/" + id + ".png");

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
		System.out.println("Extacting " + wz + "...");
		subExtract();
	}

	public abstract void subExtract();

	public abstract int getId(String path);

}
