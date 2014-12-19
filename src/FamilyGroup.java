import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class FamilyGroup implements Group{

String name;
ArrayList<Family> families;
ArrayList<Person> people;
ArrayList<Rule> rules;

public FamilyGroup(JSONObject group) {
	name = group.get("name") == null ? "" : (String) group.get("name");
	families = new ArrayList<>();
	people = new ArrayList<>();
	if(group.get("families") != null && !((JSONArray) group.get("families")).isEmpty())
		for(Object family : (JSONArray) group.get("families")) {
			Family fam = new Family((JSONObject) family);
			families.add(fam);
			people.addAll(fam.people);
		}
	rules = new ArrayList<>();
	if(group.get("rules") != null && !((JSONArray) group.get("rules")).isEmpty())
		for(Object rule : (JSONArray) group.get("rules")) {
			rules.add(new Rule((JSONObject) rule, people));
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
	JSONArray rules = new JSONArray();
	for(Rule rule : this.rules) {
		rules.add(rule.toJSON());
	}
	group.put("rules", rules);
	return group;
}

@Override
public String toCSV() {
	String csv = "";
	csv += name + "\n";
	for(Family family : families) {
		csv += family.toCSV();
	}
	csv += "\n\n";
	return csv;
}

@Override
public void randomize() {
	Random r = new Random();
	int nulls = 0;
	ArrayList<Person> available = new ArrayList<>(people);
	for (Rule rule : rules) {
		for(Family family : families) {
			for (Person person : family.people) {
				if (!rule.source.contains(person))
					continue;
				if (person.givingTo != null)
					continue;
				ArrayList<Person> specificAvailable = new ArrayList<>(available);
				specificAvailable.removeAll(family.people);
				person.applyRules(specificAvailable, rules);
				if (specificAvailable.size() <= 0)
					continue;
				Person partner = specificAvailable.get(specificAvailable.size() == 1 ? 0 : r.nextInt(specificAvailable.size()));
				person.givingTo = partner.name;
				partner.receivingFrom = person.name;
				available.remove(partner);
			}
		}
	}
	for (Person person : people) {
		if (person.givingTo != null)
			continue;
		if (available.size() <= 0 || (available.size() == 1 && available.contains(person))) {
			nulls++;
			continue;
		}
		// Temporarily remove the person, so we don't choose them to give to (only rule at this point)
		boolean removed = available.remove(person);
		Person partner = available.get(available.size() == 1 ? 0 : r.nextInt(available.size()));
		if (removed) available.add(person);
		person.givingTo = partner.name;
		partner.receivingFrom = person.name;
		available.remove(partner);
	}
	if (nulls > 0) {
		ChristmasExchange.error(nulls + (nulls == 1 ? " person wasn't" : " people weren't") + " able to be sorted.");
	}

	Parser.createCSV();
	Parser.write();
}

public void clear() {
	for(Person person : people) {
		if(!person.lockGive)
			person.givingTo = null;
		if(!person.lockReceive)
			person.receivingFrom = null;
	}
}

private class Family {

	String name;
	ArrayList<Person> people;

	public Family(JSONObject family) {
		name = family.get("name") == null ? "" : (String) family.get("name");
		people = new ArrayList<>();
		if(family.get("people") != null && !((JSONArray) family.get("people")).isEmpty())
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

	public String toCSV() {
		String csv = "";
		csv += name + "\n";
		for(Person person : people) {
			csv += "," + person.toCSV();
		}
		csv += "\n";
		return csv;
	}
}
}
