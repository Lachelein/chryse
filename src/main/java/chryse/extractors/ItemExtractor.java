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
import chryse.entities.item.Item;
import wz.WzObject;
import wz.WzProperty;
import wz.common.WzDataTool;

public class ItemExtractor extends Extractor {

	public ItemExtractor(Target target) {
		super(target, "Item");
	}

	@Override
	public void parse(WzObject<?, ?> parent, String path) {

		if (path.contains("Special")) {
			return;
		}

		if (path.contains("Pet")) {
			int id = getId(path);
			Item item = Database.getItem(id);

			parsePet(item, parent);
			dumpIcon(parent, "stand0/0");
			return;
		}

		for (WzObject<?, ?> child : parent) {
			WzObject<?, ?> info = child.getChild("info");

			int id = getId(child.getFullPath());
			Item item = Database.getItem(id);

			if (item.id >= 2000000 && item.id < 3000000) {
				parseConsumable(item, info);

				WzObject<?, ?> spec = child.getChild("spec");
				if (spec != null) {
					parseConsumable2(item, spec);
				}
			} else if (item.id >= 3000000 && item.id < 4000000) {
				parseInstall(item, info);
			} else if (item.id >= 4000000 && item.id < 5000000) {
				parseEtc(item, info);
			} else if (item.id >= 5000000 && item.id < 6000000) {
				// cash items
			}

			dumpIcon(info, "icon");
		}
	}

	private void parsePet(Item item, WzObject<?, ?> info) {
		item.life = WzDataTool.getInteger(info, "life", -1);
		item.hungry = WzDataTool.getInteger(info, "hungry", -1);
	}

	private void parseConsumable(Item item, WzObject<?, ?> info) {
		item.price = WzDataTool.getInteger(info, "price", -1);
		item.slotMax = WzDataTool.getInteger(info, "slotMax", -1);
	}

	private void parseConsumable2(Item item, WzObject<?, ?> spec) {
		item.time = WzDataTool.getInteger(spec, "time", -1);
	}

	private void parseEtc(Item item, WzObject<?, ?> info) {
		item.price = WzDataTool.getInteger(info, "price", -1);
		item.slotMax = WzDataTool.getInteger(info, "slotMax", -1);
		item.lv = WzDataTool.getInteger(info, "lv", -1);
	}

	private void parseInstall(Item item, WzObject<?, ?> info) {
		item.price = WzDataTool.getInteger(info, "price", -1);
		item.slotMax = WzDataTool.getInteger(info, "slotMax", -1);
		item.reqLevel = WzDataTool.getInteger(info, "reqLevel", -1);
	}

	private void dumpIcon(WzObject<?, ?> item, String imagePath) {
		String path = item.getFullPath();
		WzProperty<?> image = (WzProperty<?>) item.getChildByPath(imagePath);
		extractImage(image, path);
	}

	@Override
	protected void finishExtraction() {
		StringBuilder itemBuilder = new StringBuilder();

		Database.getItems().forEach((id, item) -> {
			itemBuilder.append(item.querify(item.id));
		});

		Database.addQuery(itemBuilder);
	}
}
