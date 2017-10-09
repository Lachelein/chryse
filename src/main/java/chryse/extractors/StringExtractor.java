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
import chryse.entities.item.Item;
import chryse.entities.map.Map;
import chryse.entities.monster.Monster;
import chryse.entities.npc.NPC;
import chryse.entities.skill.Skill;
import chryse.entities.skill.SkillLevel;
import wz.WzObject;
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

		String[] nonEquips = {
				"Cash",
				"Consume",
				"Etc",
				"Ins",
				"Pet"
		};

		if (Utility.contains(path, nonEquips)) {
			if (path.contains("Etc")) {
				parent = parent.getChild("Etc");
			}
			for (WzObject<?, ?> itemObj : parent) {
				int id = getId(itemObj.getFullPath());
				Item item = Database.getItem(id);

				item.name = WzDataTool.getString(itemObj, "name", "");
				item.desc = WzDataTool.getString(itemObj, "desc", "");
			}
		} else if (path.contains("Eqp")) {
			WzObject<?, ?> equipCategories = parent.getChild("Eqp");
			for (WzObject<?, ?> category : equipCategories) {
				for (WzObject<?, ?> itemObj : category) {
					int id = getId(itemObj.getFullPath());
					Equip equip = Database.getEquip(id);

					equip.name = WzDataTool.getString(itemObj, "name", "");
				}
			}
		} else if (path.contains("Map")) {
			for (WzObject<?, ?> mapCategory : parent) {
				for (WzObject<?, ?> mapObj : mapCategory) {
					int id = getId(mapObj.getFullPath());
					Map map = Database.getMap(id);

					map.mapName = WzDataTool.getString(mapObj, "mapName", "");
					map.streetName = WzDataTool.getString(mapObj, "streetName", "");
				}
			}
		} else if (path.contains("Mob")) {
			for (WzObject<?, ?> mob : parent) {
				int id = getId(mob.getFullPath());
				Monster monster = Database.getMonster(id);

				monster.name = WzDataTool.getString(mob, "name", "");
			}
		} else if (path.contains("Npc")) {
			for (WzObject<?, ?> npcObj : parent) {
				int id = getId(npcObj.getFullPath());
				NPC npc = Database.getNPC(id);

				npc.name = WzDataTool.getString(npcObj, "name", "");
				npc.func = WzDataTool.getString(npcObj, "func", "");
			}
		} else if (path.contains("Skill")) {
			for (WzObject<?, ?> skillObj : parent) {
				WzObject<?, ?> bookName = skillObj.getChild("bookName");

				if (bookName != null) {
					// books
					continue;
				}

				int id = getId(skillObj.getFullPath());
				Skill skill = Database.getSkill(id);

				skill.name = WzDataTool.getString(skillObj, "name", "");
				skill.desc = WzDataTool.getString(skillObj, "desc", "");

				int level = 1;

				while (true) {
					WzObject<?, ?> levelObj = skillObj.getChild("h" + level);

					if (levelObj == null) {
						break;
					}

					SkillLevel skillLevel = new SkillLevel();
					skillLevel.level = level;
					skillLevel.desc = WzDataTool.getString(skillObj, "h" + level, "");

					skill.add(skillLevel);

					level++;
				}
			}
		}
	}
}
