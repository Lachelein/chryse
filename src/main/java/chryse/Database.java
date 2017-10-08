package chryse;

import java.util.LinkedHashMap;

import chryse.entities.character.Equip;
import chryse.entities.item.Item;
import chryse.entities.map.Map;
import chryse.entities.monster.Monster;
import chryse.entities.npc.NPC;
import chryse.entities.skill.Skill;

public class Database {

	private static LinkedHashMap<Integer, Item> items = new LinkedHashMap<Integer, Item>();
	private static LinkedHashMap<Integer, Equip> equips = new LinkedHashMap<Integer, Equip>();
	private static LinkedHashMap<Integer, Map> maps = new LinkedHashMap<Integer, Map>();
	private static LinkedHashMap<Integer, Monster> monsters = new LinkedHashMap<Integer, Monster>();
	private static LinkedHashMap<Integer, NPC> npcs = new LinkedHashMap<Integer, NPC>();
	private static LinkedHashMap<Integer, Skill> skills = new LinkedHashMap<Integer, Skill>();

	private static StringBuilder query = new StringBuilder();

	public static String buildQuery() {

		getEquips().forEach((id, equip) -> {
			query.append(equip.querify(equip.id));
		});

		getItems().forEach((id, item) -> {
			query.append(item.querify(item.id));
		});

		StringBuilder npcsStringBuilder = new StringBuilder();
		StringBuilder monstersStringBuilder = new StringBuilder();
		StringBuilder portalsStringBuilder = new StringBuilder();

		for (Map map : getMaps().values()) {
			query.append(map.querify(map.id));

			map.npcs.forEach(npc -> npcsStringBuilder.append(npc.querify(map.id)));
			map.monsters.forEach(monster -> monstersStringBuilder.append(monster.querify(map.id)));
			map.portals.forEach(portal -> portalsStringBuilder.append(portal.querify(map.id)));
		}

		query.append(npcsStringBuilder.toString());
		query.append(monstersStringBuilder.toString());
		query.append(portalsStringBuilder.toString());

		getMonsters().forEach((id, monster) -> {
			query.append(monster.querify(monster.id));
		});

		getNPCs().forEach((id, npc) -> {
			query.append(npc.querify(npc.id));
		});

		StringBuilder skillLevelsStringBuilder = new StringBuilder();
		for (Skill skill : getSkills().values()) {
			query.append(skill.querify(skill.id));

			skill.levels.forEach(level -> skillLevelsStringBuilder.append(level.querify(skill.id)));
		}

		query.append(skillLevelsStringBuilder.toString());

		return query.toString();
	}

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

	public static LinkedHashMap<Integer, NPC> getNPCs() {
		return npcs;
	}

	public static LinkedHashMap<Integer, Skill> getSkills() {
		return skills;
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

	public static NPC getNPC(int id) {
		NPC npc = npcs.get(id);
		if (npc != null) {
			return npc;
		} else {
			npc = new NPC(id);
			npcs.put(id, npc);
			return npc;
		}
	}

	public static Skill getSkill(int id) {
		Skill skill = skills.get(id);
		if (skill != null) {
			return skill;
		} else {
			skill = new Skill(id);
			skills.put(id, skill);
			return skill;
		}
	}
}
