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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import chryse.extractors.CharacterExtractor;
import chryse.extractors.ItemExtractor;
import chryse.extractors.MapExtractor;
import chryse.extractors.MonsterExtractor;
import chryse.extractors.NpcExtractor;
import chryse.extractors.ReactorExtractor;
import chryse.extractors.SkillExtractor;
import chryse.extractors.SoundExtractor;
import chryse.extractors.StringExtractor;

public class App {

	public static void main(String[] args) {
		long startTime = System.nanoTime();

		Properties properties = loadProperties();
		Target target = new Target(properties);

		Extractor[] extractors = {
				new ItemExtractor(target),
				new NpcExtractor(target),
				new MapExtractor(target),
				new MonsterExtractor(target),
				new CharacterExtractor(target),
				new ReactorExtractor(target),
				new SkillExtractor(target),
				new SoundExtractor(target),
				new StringExtractor(target)

		};

		// Quest.wz
		// String.wz

		for (Extractor extractor : extractors) {
			extractor.extract();
		}

		Database.buildQuery();
		Database.printQuery();

		long seconds = (System.nanoTime() - startTime) / 1000000000;
		System.out.println("All files extracted in " + seconds + " seconds.");
	}

	public static Properties loadProperties() {
		Properties properties = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("config.properties");
			properties.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return properties;
	}
}
