package chryse.entities.item;

import chryse.Querifiable;

public class Item implements Querifiable {

	public int id;
	public int price;
	public int hungry;
	public int life;
	public int slotMax;
	public int time;
	public int lv;

	@Override
	public String getInsertQuery(int relationshipKey) {
		return "INSERT INTO map (id, mark, bgm, return_map) VALUES ";
	}

	@Override
	public void querify(StringBuilder builder, int relationshipKey) {
		builder.append(getInsertQuery(relationshipKey));
	}
}
