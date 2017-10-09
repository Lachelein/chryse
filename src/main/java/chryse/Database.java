package chryse;

import java.util.LinkedHashMap;

import chryse.entities.character.Equip;
import chryse.entities.item.Item;
import chryse.entities.map.Map;
import chryse.entities.map.MapMonster;
import chryse.entities.map.MapNPC;
import chryse.entities.map.MapPortal;
import chryse.entities.monster.Monster;
import chryse.entities.npc.NPC;
import chryse.entities.skill.Skill;
import chryse.entities.skill.SkillLevel;

public class Database {

	private static LinkedHashMap<Integer, Item> items = new LinkedHashMap<Integer, Item>();
	private static LinkedHashMap<Integer, Equip> equips = new LinkedHashMap<Integer, Equip>();
	private static LinkedHashMap<Integer, Map> maps = new LinkedHashMap<Integer, Map>();
	private static LinkedHashMap<Integer, Monster> monsters = new LinkedHashMap<Integer, Monster>();
	private static LinkedHashMap<Integer, NPC> npcs = new LinkedHashMap<Integer, NPC>();
	private static LinkedHashMap<Integer, Skill> skills = new LinkedHashMap<Integer, Skill>();

	public static void buildQueries() {
		buildItemsQuery();
		buildEquipsQuery();
		buildMapsQuery();
		buildMonstersQuery();
		buildNPCsQuery();
		buildSkillsQuery();
	}

	private static void buildItemsQuery() {
		StringBuilder query = new StringBuilder();

		Item fakeItem = new Item(-1);
		fakeItem.addTableStructure(query, -1);

		items.forEach((id, item) -> item.addTableData(query, item.id));
		Utility.printQueryToFile(fakeItem.getTableName(), query);
	}

	private static void buildEquipsQuery() {
		StringBuilder query = new StringBuilder();
		Equip fakeEquip = new Equip(-1);
		fakeEquip.addTableStructure(query, -1);

		equips.forEach((id, equip) -> equip.addTableData(query, equip.id));
		Utility.printQueryToFile(fakeEquip.getTableName(), query);
	}

	private static void buildMapsQuery() {
		StringBuilder mapStringBuilder = new StringBuilder();
		StringBuilder npcsStringBuilder = new StringBuilder();
		StringBuilder monstersStringBuilder = new StringBuilder();
		StringBuilder portalsStringBuilder = new StringBuilder();

		Map fakeMap = new Map(-1);
		fakeMap.addTableStructure(mapStringBuilder, fakeMap.id);

		MapNPC fakeMapNPC = new MapNPC(-1, -1, -1);
		fakeMapNPC.addTableStructure(npcsStringBuilder, -1);

		MapMonster fakeMapMonster = new MapMonster(-1, -1, -1);
		fakeMapMonster.addTableStructure(monstersStringBuilder, -1);

		MapPortal fakeMapPortal = new MapPortal(-1);
		fakeMapPortal.addTableStructure(portalsStringBuilder, -1);

		for (Map map : maps.values()) {
			map.addTableData(mapStringBuilder, map.id);
			map.npcs.forEach(npc -> npc.addTableData(npcsStringBuilder, map.id));
			map.monsters.forEach(monster -> monster.addTableData(monstersStringBuilder, map.id));
			map.portals.forEach(portal -> portal.addTableData(portalsStringBuilder, map.id));
		}

		Utility.printQueryToFile(fakeMap.getTableName(), mapStringBuilder);
		Utility.printQueryToFile(fakeMapNPC.getTableName(), npcsStringBuilder);
		Utility.printQueryToFile(fakeMapMonster.getTableName(), monstersStringBuilder);
		Utility.printQueryToFile(fakeMapPortal.getTableName(), portalsStringBuilder);
	}

	private static void buildMonstersQuery() {
		StringBuilder query = new StringBuilder();

		Monster fakeMonster = new Monster(-1);
		fakeMonster.addTableStructure(query, -1);

		monsters.forEach((id, monster) -> monster.addTableData(query, monster.id));
		Utility.printQueryToFile(fakeMonster.getTableName(), query);
	}

	private static void buildNPCsQuery() {
		StringBuilder query = new StringBuilder();

		NPC fakeNPC = new NPC(-1);
		fakeNPC.addTableStructure(query, -1);

		npcs.forEach((id, npc) -> npc.addTableData(query, npc.id));
		Utility.printQueryToFile(fakeNPC.getTableName(), query);
	}

	private static void buildSkillsQuery() {
		StringBuilder query = new StringBuilder();

		Skill fakeSkill = new Skill(-1);
		fakeSkill.addTableStructure(query, -1);

		StringBuilder skillLevelsStringBuilder = new StringBuilder();
		SkillLevel fakeSkillLevel = new SkillLevel();
		fakeSkillLevel.addTableStructure(skillLevelsStringBuilder, -1);

		for (Skill skill : skills.values()) {
			skill.addTableData(query, skill.id);
			skill.levels.forEach(level -> level.addTableData(skillLevelsStringBuilder, skill.id));
		}

		Utility.printQueryToFile(fakeSkill.getTableName(), query);
		Utility.printQueryToFile(fakeSkillLevel.getTableName(), skillLevelsStringBuilder);

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
