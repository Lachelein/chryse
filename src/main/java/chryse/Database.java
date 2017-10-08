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

	private static StringBuilder sqlQuery = new StringBuilder();

	public static void buildQuery() {
		StringBuilder characterBuilder = new StringBuilder();

		Database.getEquips().forEach((id, equip) -> {
			characterBuilder.append(equip.querify(equip.id));
		});

		Database.addQuery(characterBuilder);

		StringBuilder itemBuilder = new StringBuilder();

		Database.getItems().forEach((id, item) -> {
			itemBuilder.append(item.querify(item.id));
		});

		Database.addQuery(itemBuilder);

		StringBuilder mapBuilder = new StringBuilder();
		StringBuilder mapNpcsBuilder = new StringBuilder();
		StringBuilder mapMonstersBuilder = new StringBuilder();
		StringBuilder mapPortalBuilder = new StringBuilder();

		for (Map map : Database.getMaps().values()) {
			mapBuilder.append(map.querify(map.id));

			map.npcs.forEach(npc -> mapNpcsBuilder.append(npc.querify(map.id)));
			map.monsters.forEach(monster -> mapMonstersBuilder.append(monster.querify(map.id)));
			map.portals.forEach(portal -> mapPortalBuilder.append(portal.querify(map.id)));
		}

		Database.addQuery(mapBuilder);
		Database.addQuery(mapNpcsBuilder);
		Database.addQuery(mapMonstersBuilder);
		Database.addQuery(mapPortalBuilder);

		StringBuilder monsterBuilder = new StringBuilder();

		Database.getMonsters().forEach((id, monster) -> {
			monsterBuilder.append(monster.querify(monster.id));
		});

		Database.addQuery(monsterBuilder);

		StringBuilder npcsBuilder = new StringBuilder();

		Database.getNPCs().forEach((id, npc) -> {
			npcsBuilder.append(npc.querify(npc.id));
		});

		Database.addQuery(npcsBuilder);

		StringBuilder skillsBuilder = new StringBuilder();
		StringBuilder skillsLevelBuilder = new StringBuilder();

		for (Skill skill : Database.getSkills().values()) {
			skillsBuilder.append(skill.querify(skill.id));

			skill.levels.forEach(level -> skillsLevelBuilder.append(level.querify(skill.id)));
		}

		Database.addQuery(skillsBuilder);
		Database.addQuery(skillsLevelBuilder);
	}

	public static void addQuery(StringBuilder builder) {
		sqlQuery.append(builder.toString());
	}

	public static void printQuery() {
		System.out.println(sqlQuery.toString());
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
