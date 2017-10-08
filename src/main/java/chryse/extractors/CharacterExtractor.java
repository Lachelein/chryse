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
import chryse.entities.character.Equip;
import wz.WzObject;
import wz.WzProperty;
import wz.common.WzDataTool;

public class CharacterExtractor extends Extractor {

	public CharacterExtractor(Target target) {
		super(target, "Character");
	}

	@Override
	public void parse(WzObject<?, ?> parent, String path) {

		if (path.length() <= 28) {
			return;
		}

		String[] block = {
				"Face",
				"Hair"
		};

		if (Utility.contains(path, block)) {
			return;
		}

		WzObject<?, ?> info = parent.getChild("info");

		if (info == null) {
			return;
		}

		int id = getId(path);
		Equip equip = Database.getEquip(id);

		equip.incMDD = WzDataTool.getInteger(info, "incMDD", -1);
		equip.incPDD = WzDataTool.getInteger(info, "incPDD", -1);
		equip.incMAD = WzDataTool.getInteger(info, "incMAD", -1);
		equip.incPAD = WzDataTool.getInteger(info, "incPAD", -1);
		equip.incDEX = WzDataTool.getInteger(info, "incDEX", -1);
		equip.incACC = WzDataTool.getInteger(info, "incACC", -1);
		equip.incINT = WzDataTool.getInteger(info, "incINT", -1);
		equip.incLUK = WzDataTool.getInteger(info, "incLUK", -1);
		equip.incSTR = WzDataTool.getInteger(info, "incSTR", -1);
		equip.attackSpeed = WzDataTool.getInteger(info, "attackSpeed", -1);
		equip.price = WzDataTool.getInteger(info, "price", -1);
		equip.reqDEX = WzDataTool.getInteger(info, "reqDEX", -1);
		equip.reqINT = WzDataTool.getInteger(info, "reqINT", -1);
		equip.reqJob = WzDataTool.getInteger(info, "reqJob", -1);
		equip.reqLevel = WzDataTool.getInteger(info, "reqLevel", -1);
		equip.reqLUK = WzDataTool.getInteger(info, "reqLUK", -1);
		equip.reqSTR = WzDataTool.getInteger(info, "reqSTR", -1);
		equip.tuc = WzDataTool.getInteger(info, "tuc", -1);

		WzProperty<?> image = (WzProperty<?>) info.getChild("iconRaw");
		extractImage(image, path);
	}

	@Override
	protected void finishExtraction() {
		StringBuilder characterBuilder = new StringBuilder();

		Database.getEquips().forEach((id, equip) -> {
			characterBuilder.append(equip.querify(equip.id));
		});

		Database.addQuery(characterBuilder);
	}

}
