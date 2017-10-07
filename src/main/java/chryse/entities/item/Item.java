package chryse.entities.item;

import java.util.LinkedHashMap;

import chryse.Querifiable;

public class Item extends Querifiable {

	public int id;
	public int price;
	public int hungry;
	public int life;
	public int slotMax;
	public int time;
	public int lv;
	public int reqLevel;

	@Override
	public String getTableName() {
		return "items";
	}

	@Override
	public LinkedHashMap<String, Object> getQueryParameters(int relationshipKey) {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<String, Object>();
		parameters.put("id", id);
		parameters.put("price", price);
		parameters.put("hungry", hungry);
		parameters.put("return_map", life);
		parameters.put("slotMax", slotMax);
		parameters.put("time", time);
		parameters.put("lv", lv);
		parameters.put("reqLevel", reqLevel);
		return parameters;
	}
}
