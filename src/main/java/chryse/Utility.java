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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

import wz.WzProperty;
import wz.common.MP3;
import wz.common.PNG;

public class Utility {

	public static boolean isNumeric(String string) {
		return string != null && string.matches("[-+]?\\d*\\.?\\d+");
	}

	public static void printQueryToFile(String tableName, StringBuilder query) {
		query.setLength(query.length() - 3);
		query.append(";");
		printTextToFile("dump/SQL/" + tableName + ".sql", query.toString());
	}

	public static void printTextToFile(String destination, String text) {
		File file = new File(destination);

		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		try (PrintWriter printWriter = new PrintWriter(file)) {
			printWriter.println(text);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void extractImage(WzProperty<?> property, String wz, String out) {
		if (property.getValue() instanceof PNG) {
			PNG value = (PNG) property.getValue();
			Image image = value.getImage(false);

			if (image.getWidth(null) <= 4 || image.getHeight(null) <= 4) {
				// System.out.println("Skipping " + out);
				return;
			}

			Utility.exportImage(wz, out, image);
		}
	}

	public static void extractMP3(WzProperty<?> property, String name) {
		if (property.getValue() instanceof MP3) {
			MP3 value = (MP3) property.getValue();

			Utility.extractSound(value, name);
		}
	}

	public static void exportImage(String wz, String out, Image image) {
		try {
			File file = new File("dump/" + wz + "/" + out + ".png");

			if (file.exists()) {
				file.delete();
			} else {
				file.mkdirs();
				file.createNewFile();
			}

			ImageIO.write((BufferedImage) image, "png", file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		image.flush();
	}

	public static boolean contains(String text, String[] block) {
		for (String b : block) {
			if (text.contains(b)) {
				return true;
			}
		}
		return false;
	}

	public static void extractSound(MP3 song, String name) {

		try {
			File file = new File("dump/Sound/" + name + ".mp3");

			if (file.exists()) {
				file.delete();
			} else {
				file.getParentFile().mkdirs();
			}

			try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
				fileOutputStream.write(song.getHeader());
				fileOutputStream.write(song.getData());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
