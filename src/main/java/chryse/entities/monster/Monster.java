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

package chryse.entities.monster;

import java.util.LinkedHashMap;

import chryse.Querifiable;

public class Monster extends Querifiable {

	public int id;
	public String name;
	public int acc;
	public int boss;
	public int bodyAttack;
	public int eva;
	public int exp;
	public int level;
	public int MADamage;
	public int maxHP;
	public int maxMP;
	public int MDDamage;
	public int mobType;
	public int PADamage;
	public int PDDamage;
	public int pushed;
	public int speed;
	public int undead;

	public Monster(int id) {
		this.id = id;
	}

	@Override
	public String getTableName() {
		return "monsters";
	}

	@Override
	public LinkedHashMap<String, Object> getQueryParameters(int relationshipKey) {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<String, Object>();
		parameters.put("id", id);
		parameters.put("name", name);
		parameters.put("acc", acc);
		parameters.put("boss", boss);
		parameters.put("bodyAttack", bodyAttack);
		parameters.put("eva", eva);
		parameters.put("exp", exp);
		parameters.put("level", level);
		parameters.put("MADamage", MADamage);
		parameters.put("maxHP", maxHP);
		parameters.put("maxMP", maxMP);
		parameters.put("MDDamage", MDDamage);
		parameters.put("mobType", mobType);
		parameters.put("PADamage", PADamage);
		parameters.put("PDDamage", PDDamage);
		parameters.put("pushed", pushed);
		parameters.put("speed", speed);
		parameters.put("undead", undead);
		return parameters;
	}

}
