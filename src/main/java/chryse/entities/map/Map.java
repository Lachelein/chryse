package chryse.entities.map;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import chryse.Querifiable;

public class Map extends Querifiable {

	public int id;
	public String mapName;
	public String streetName;
	public String mapMark;
	public String bgm;
	public int returnMap;

	public ArrayList<MapNPC> npcs = new ArrayList<MapNPC>();
	public ArrayList<MapMonster> monsters = new ArrayList<MapMonster>();
	public ArrayList<MapPortal> portals = new ArrayList<MapPortal>();

	public Map(int id) {
		this.id = id;
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
		return "maps";
	}

	@Override
	public LinkedHashMap<String, Object> getQueryParameters(int relationshipKey) {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<String, Object>();
		parameters.put("mapId", id);
		parameters.put("mapName", mapName);
		parameters.put("streetName", streetName);
		parameters.put("mark", mapMark);
		parameters.put("bgm", bgm);
		parameters.put("returnMap", returnMap);
		return parameters;
	}

}
