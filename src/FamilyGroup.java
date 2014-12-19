import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FamilyGroup implements Group{

String name;
ArrayList<Family> families;
ArrayList<Rule> rules;

public FamilyGroup(JSONObject group) {
	name = (String) group.get("name");
	families = new ArrayList<>();
	for(Object family : (JSONArray) group.get("families")) {
		families.add(new Family((JSONObject) family));
	}
	for(Object rule : (JSONArray) group.get("rules")) {
		rules.add(new Rule((JSONObject) rule));
	}
}

@Override
public String getName() {
	return name;
}

@Override
public void setName(String name) {
	this.name = name;
}

@Override
public JPanel toCards() {
	JPanel group = new JPanel(new GridLayout(0, 1));
	for(Family family : families) {
		group.add(family.toCards());
	}
	return group;
}

@Override
public JSONObject toJSON() {
	JSONObject group = new JSONObject();
	group.put("type", "family");
	group.put("name", name);
	JSONArray families = new JSONArray();
	for(Family family : this.families) {
		families.add(family.toJSON());
	}
	group.put("families", families);
	JSONArray rules = new JSONArray();
	for(Rule rule : this.rules) {
		rules.add(rule.toJSON());
	}
	group.put("rules", rules);
	return group;
}

@Override
public void randomize() {
	// TODO move generator code here
}

private class Family {

	String name;
	ArrayList<Person> people;

	public Family(JSONObject family) {
		name = (String) family.get("name");
		people = new ArrayList<>();
		for(Object person : (JSONArray) family.get("people")) {
			people.add(new Person((JSONObject) person));
		}
	}

	public JSONObject toJSON() {
		JSONObject family = new JSONObject();
		family.put("name", name);
		JSONArray people = new JSONArray();
		for(Person person : this.people) {
			people.add(person.toJSON());
		}
		family.put("people", people);
		return family;
	}

	public JPanel toCards() {
		JPanel cards = new JPanel(new GridLayout(0, 1));
		// TODO family header
		for(Person person : people) {
			cards.add(person.toCard());
		}
		return cards;
	}
}
}
