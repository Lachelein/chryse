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

package chryse.entities.character;

import java.util.LinkedHashMap;

import chryse.Querifiable;

public class Equip extends Querifiable {

	public int id;
	public int incMDD;
	public int incPDD;
	public int incMAD;
	public int incPAD;
	public int incDEX;
	public int incACC;
	public int incINT;
	public int incLUK;
	public int incSTR;
	public int attackSpeed;
	public int price;
	public int reqDEX;
	public int reqINT;
	public int reqJob;
	public int reqLevel;
	public int reqLUK;
	public int reqSTR;
	public int tuc;

	@Override
	public String getTableName() {
		return "character";
	}

	@Override
	public LinkedHashMap<String, Object> getQueryParameters(int relationshipKey) {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<String, Object>();
		parameters.put("id", id);
		parameters.put("incMDD", incMDD);
		parameters.put("incPDD", incPDD);
		parameters.put("incMAD", incMAD);
		parameters.put("incPAD", incPAD);
		parameters.put("incDEX", incDEX);
		parameters.put("incACC", incACC);
		parameters.put("incINT", incINT);
		parameters.put("incLUK", incLUK);
		parameters.put("incSTR", incSTR);
		parameters.put("attackSpeed", attackSpeed);
		parameters.put("price", price);
		parameters.put("reqDEX", reqDEX);
		parameters.put("reqINT", reqINT);
		parameters.put("reqJob", reqJob);
		parameters.put("reqLevel", reqLevel);
		parameters.put("reqLUK", reqLUK);
		parameters.put("reqSTR", reqSTR);
		parameters.put("tuc", tuc);
		return parameters;
	}
}
