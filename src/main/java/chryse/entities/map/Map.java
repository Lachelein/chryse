package chryse.entities.map;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import chryse.Querifiable;

public class Map extends Querifiable {

	public int id;
	public String mapMark;
	public String bgm;
	public int returnMap;

	public ArrayList<MapNPC> npcs = new ArrayList<MapNPC>();
	public ArrayList<MapMonster> monsters = new ArrayList<MapMonster>();
	public ArrayList<MapPortal> portals = new ArrayList<MapPortal>();

	public Map(int id, String mapMark, String bgm, int returnMap) {
		this.id = id;
		this.mapMark = mapMark;
		this.bgm = bgm;
		this.returnMap = returnMap;
	}

	public void add(MapNPC npc) {
		npcs.add(npc);
	}

	public void add(MapMonster monster) {
		monsters.add(monster);
	}

	public void add(MapPortal portal) {
		portals.add(portal);
	}

	@Override
	public String getTableName() {
		return "map";
	}

	@Override
	public LinkedHashMap<String, Object> getQueryParameters(int relationshipKey) {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<String, Object>();
		parameters.put("id", id);
		parameters.put("mark", mapMark);
		parameters.put("bgm", bgm);
		parameters.put("return_map", returnMap);
		return parameters;
	}

}
