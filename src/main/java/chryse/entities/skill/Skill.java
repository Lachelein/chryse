package chryse.entities.skill;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import chryse.Querifiable;

public class Skill extends Querifiable {

	public int id;
	public String name;
	public String desc;
	public ArrayList<SkillLevel> levels;

	public Skill(int id) {
		this.id = id;
		this.levels = new ArrayList<SkillLevel>();
	}

	public void add(SkillLevel level) {
		levels.add(level);
	}

	@Override
	public String getTableName() {
		return "skills";
	}

	@Override
	public LinkedHashMap<String, Object> getQueryParameters(int relationshipKey) {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<String, Object>();
		parameters.put("id", id);
		parameters.put("name", name);
		parameters.put("desc", desc);
		return parameters;
	}
}
