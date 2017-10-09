package chryse.entities.npc;

import java.util.LinkedHashMap;

import chryse.Querifiable;

public class NPC extends Querifiable {

	public int id;
	public String name;
	public String func;

	public NPC(int id) {
		this.id = id;
	}

	@Override
	public String getTableName() {
		return "npcs";
	}

	@Override
	public LinkedHashMap<String, Object> getQueryParameters(int relationshipKey) {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<String, Object>();
		parameters.put("npcId", id);
		parameters.put("name", name);
		parameters.put("func", func);
		return parameters;
	}
}
