package chryse.entities.character;

import java.util.LinkedHashMap;

import chryse.Querifiable;

public class Equip extends Querifiable {

	public int id;
	public int incMDD;
	public int incPDD;
	public int incMAD;
	public int incPAD;
	public int incDEX;
	public int incACC;
	public int incINT;
	public int incLUK;
	public int incSTR;
	public int attackSpeed;
	public int price;
	public int reqDEX;
	public int reqINT;
	public int reqJob;
	public int reqLevel;
	public int reqLUK;
	public int reqSTR;
	public int tuc;

	@Override
	public String getTableName() {
		return "character";
	}

	@Override
	public LinkedHashMap<String, Object> getQueryParameters(int relationshipKey) {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<String, Object>();
		parameters.put("id", id);
		parameters.put("incMDD", incMDD);
		parameters.put("incPDD", incPDD);
		parameters.put("incMAD", incMAD);
		parameters.put("incPAD", incPAD);
		parameters.put("incDEX", incDEX);
		parameters.put("incACC", incACC);
		parameters.put("incINT", incINT);
		parameters.put("incLUK", incLUK);
		parameters.put("incSTR", incSTR);
		parameters.put("attackSpeed", attackSpeed);
		parameters.put("price", price);
		parameters.put("reqDEX", reqDEX);
		parameters.put("reqINT", reqINT);
		parameters.put("reqJob", reqJob);
		parameters.put("reqLevel", reqLevel);
		parameters.put("reqLUK", reqLUK);
		parameters.put("reqSTR", reqSTR);
		parameters.put("tuc", tuc);
		return parameters;
	}
}
