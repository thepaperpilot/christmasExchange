import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class FamilyGroup implements Group {

private String name;
private final ArrayList<Family> families;
private final ArrayList<Person> people;
private final ArrayList<Rule> rules;

public FamilyGroup(JSONObject group) {
	name = group.get("name") == null ? "" : (String) group.get("name");
	families = new ArrayList<>();
	people = new ArrayList<>();
	if (group.get("families") != null && !((JSONArray) group.get("families")).isEmpty())
		for (Object family : (JSONArray) group.get("families")) {
			Family fam = new Family((JSONObject) family);
			families.add(fam);
			people.addAll(fam.people);
		}
	rules = new ArrayList<>();
	if (group.get("rules") != null && !((JSONArray) group.get("rules")).isEmpty())
		for (Object rule : (JSONArray) group.get("rules")) {
			rules.add(new Rule((JSONObject) rule, people));
		}
}

public FamilyGroup(String text) {
	name = text;
	families = new ArrayList<>();
	people = new ArrayList<>();
	rules = new ArrayList<>();
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
public ArrayList<Person> getPeople() {
	return people;
}

@Override
public Box toCards() {
	Box group = new Box(BoxLayout.PAGE_AXIS);
	for (Family family : families) {
		for (JPanel panel : family.toCards())
			group.add(panel);
	}
	return group;
}

@Override
public JSONObject toJSON() {
	JSONObject group = new JSONObject();
	group.put("type", "family");
	group.put("name", name);
	JSONArray families = new JSONArray();
	for (Family family : this.families) {
		families.add(family.toJSON());
	}
	group.put("families", families);
	JSONArray rules = new JSONArray();
	for (Rule rule : this.rules) {
		rules.add(rule.toJSON());
	}
	group.put("rules", rules);
	return group;
}

@Override
public String toCSV() {
	String csv = "";
	csv += name + "\n";
	for (Family family : families) {
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
	for(Person person : people) {
		if(!person.participating)
			available.remove(person);
		if(!person.lockGive) {
			person.givingTo = null;
		}
		if(person.lockReceive) {
			available.remove(person);
		} else {
			person.receivingFrom = null;
		}
	}
	for (Rule rule : rules) {
		for (Family family : families) {
			for (Person person : family.people) {
				if (!rule.source.contains(person))
					continue;
				if (person.givingTo != null)
					continue;
				if (!person.participating)
					continue;
				ArrayList<Person> specificAvailable = new ArrayList<>(available);
				specificAvailable.removeAll(family.people);
				person.applyRules(specificAvailable, rules);
				if(specificAvailable.size() != 1)
					specificAvailable.remove(find(person.receivingFrom));
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
		if (!person.participating)
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

	Parser.write();
}

@Override
public Person find(String name) {
	// TODO switch to UIDs
	for(Person person : people) {
		if(person.name.equals(name)) {
			return person;
		}
	}
	return null;
}

private class Family {

	final String name;
	final ArrayList<Person> people;

	public Family(JSONObject family) {
		name = family.get("name") == null ? "" : (String) family.get("name");
		people = new ArrayList<>();
		if (family.get("people") != null && !((JSONArray) family.get("people")).isEmpty())
			for (Object person : (JSONArray) family.get("people")) {
				people.add(new Person((JSONObject) person));
			}
	}

	public JSONObject toJSON() {
		JSONObject family = new JSONObject();
		family.put("name", name);
		JSONArray people = new JSONArray();
		for (Person person : this.people) {
			people.add(person.toJSON());
		}
		family.put("people", people);
		return family;
	}

	public ArrayList<JPanel> toCards() {
		ArrayList<JPanel> cards = new ArrayList<>();
		cards.add(new FamilyHeader(name).panel);
		for (Person person : people) {
			cards.add(person.getCard().cards);
		}
		return cards;
	}

	public String toCSV() {
		String csv = "";
		csv += name + "\n";
		for (Person person : people) {
			csv += "," + person.toCSV();
		}
		csv += "\n";
		return csv;
	}
}
}
