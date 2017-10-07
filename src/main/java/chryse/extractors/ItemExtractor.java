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

import java.util.ArrayList;

import chryse.Extractor;
import chryse.Target;
import chryse.entities.item.Item;
import wz.WzObject;
import wz.WzProperty;
import wz.common.WzDataTool;

public class ItemExtractor extends Extractor {

	public ArrayList<Item> items = new ArrayList<Item>();

	public ItemExtractor(Target target) {
		super(target, "Item");
	}

	@Override
	public void parse(WzObject<?, ?> parent, String path) {

		if (path.contains("Special")) {
			return;
		}

		Item item = new Item();
		item.id = getId(path);

		if (path.contains("Pet")) {
			parsePet(item, parent);
			dumpIcon(parent, "stand0/0");
		} else {
			for (WzObject<?, ?> child : parent) {
				if (item.id >= 5000000 && item.id < 6000000) {
					parseCashItem(item, parent);
				} else if (item.id >= 2000000 && item.id < 3000000) {
					parseConsumable(item, parent);
				} else if (item.id >= 4000000 && item.id < 5000000) {
					parseEtc(item, parent);
				}
				dumpIcon(child, "info/icon");
			}
		}
	}

	private void parsePet(Item item, WzObject<?, ?> parent) {
		item.price = WzDataTool.getInteger(parent, "info/price", -1);
		item.hungry = WzDataTool.getInteger(parent, "info/hungry", -1);
	}

	private void parseCashItem(Item item, WzObject<?, ?> parent) {
		// nothing to do here
	}

	private void parseConsumable(Item item, WzObject<?, ?> parent) {
		item.price = WzDataTool.getInteger(parent, "info/price", -1);
		item.slotMax = WzDataTool.getInteger(parent, "info/slotMax", -1);
		item.time = WzDataTool.getInteger(parent, "spec/time", -1);
	}

	private void parseEtc(Item item, WzObject<?, ?> parent) {
		item.price = WzDataTool.getInteger(parent, "info/price", -1);
		item.slotMax = WzDataTool.getInteger(parent, "info/slotMax", -1);
		item.lv = WzDataTool.getInteger(parent, "info/lv", -1);
	}

	private void dumpIcon(WzObject<?, ?> item, String imagePath) {
		String path = item.getFullPath();
		int id = getId(path);
		System.out.println(path + " id: " + id);

		WzProperty<?> image = (WzProperty<?>) item.getChildByPath(imagePath);
		// extractImage(image, path);
	}

	@Override
	protected void finishExtraction() {
		StringBuilder itemBuilder = new StringBuilder();

		items.forEach(item -> item.querify(itemBuilder, item.id));

		System.out.println(itemBuilder.toString());
	}
}
