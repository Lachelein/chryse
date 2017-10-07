package chryse.entities.map;

import java.util.ArrayList;

import chryse.Querifiable;

public class Map implements Querifiable {

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
	public String getInsertQuery(int relationshipKey) {
		return "INSERT INTO map (id, mark, bgm, return_map) VALUES (" + id + ", " + mapMark + ", " + bgm + ", " + returnMap + ");\r\n";
	}

	@Override
	public void querify(StringBuilder builder, int relationshipKey) {
		builder.append(getInsertQuery(relationshipKey));
	}

}
