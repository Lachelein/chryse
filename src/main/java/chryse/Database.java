package chryse;

import java.util.LinkedHashMap;

import chryse.entities.character.Equip;
import chryse.entities.item.Item;
import chryse.entities.map.Map;
import chryse.entities.monster.Monster;

public class Database {

	private static LinkedHashMap<Integer, Item> items = new LinkedHashMap<Integer, Item>();
	private static LinkedHashMap<Integer, Equip> equips = new LinkedHashMap<Integer, Equip>();
	private static LinkedHashMap<Integer, Map> maps = new LinkedHashMap<Integer, Map>();
	private static LinkedHashMap<Integer, Monster> monsters = new LinkedHashMap<Integer, Monster>();

	public static LinkedHashMap<Integer, Item> getItems() {
		return items;
	}

	public static LinkedHashMap<Integer, Equip> getEquips() {
		return equips;
	}

	public static LinkedHashMap<Integer, Map> getMaps() {
		return maps;
	}

	public static LinkedHashMap<Integer, Monster> getMonsters() {
		return monsters;
	}

	public static Item getItem(int id) {
		Item item = items.get(id);
		if (item != null) {
			return item;
		} else {
			item = new Item(id);
			items.put(id, item);
			return item;
		}
	}

	public static Equip getEquip(int id) {
		Equip equip = equips.get(id);
		if (equip != null) {
			return equip;
		} else {
			equip = new Equip(id);
			equips.put(id, equip);
			return equip;
		}
	}

	public static Map getMap(int id) {
		Map map = maps.get(id);
		if (map != null) {
			return map;
		} else {
			map = new Map(id);
			maps.put(id, map);
			return map;
		}
	}

	public static Monster getMonster(int id) {
		Monster monster = monsters.get(id);
		if (monster != null) {
			return monster;
		} else {
			monster = new Monster(id);
			monsters.put(id, monster);
			return monster;
		}
	}
}
