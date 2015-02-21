package com.thepaperpilot.giftexchange.core;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Group {

public static List<Group> groups;

protected ArrayList<Family> families = new ArrayList<>();
protected ArrayList<Person> people = new ArrayList<>();
protected ArrayList<Rule> rules = new ArrayList<>();
protected String name = "";

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public ArrayList<Person> getPeople() {
	return people;
}

public int randomize() {
	Random r = new Random();
	int nulls = 0;
	ArrayList<Person> available = new ArrayList<>(people);
	for(Person person : people) {
		if(!person.participating || person.lockReceive)
			available.remove(person);
		if(!person.lockGive) {
			person.givingTo = null;
		}
		if(!person.lockReceive) {
			person.receivingFrom = null;
		}
	}
	for(Rule rule : rules) {
		for(Family family : families) {
			for(Person person : family.people) {
				if(!rule.checkSource(person))
					continue;
				if(person.givingTo != null)
					continue;
				if(!person.participating)
					continue;
				ArrayList<Person> specificAvailable = new ArrayList<>(available);
				specificAvailable.removeAll(family.people);
				person.applyRules(specificAvailable, rules);
				if(specificAvailable.size() != 1)
					specificAvailable.remove(find(person.receivingFrom));
				if(specificAvailable.size() <= 0)
					continue;
				Person partner = specificAvailable.get(specificAvailable.size() == 1 ? 0 : r.nextInt(specificAvailable.size()));
				person.givingTo = partner.name;
				partner.receivingFrom = person.name;
				available.remove(partner);
			}
		}
	}
	for(Person person : people) {
		if(person.givingTo != null)
			continue;
		if(!person.participating)
			continue;
		if(available.size() <= 0 || (available.size() == 1 && available.contains(person))) {
			nulls++;
			continue;
		}
		// Temporarily remove the person, so we don't choose them to give to (only rule at this point)
		boolean removed = available.remove(person);
		Person partner = available.get(available.size() == 1 ? 0 : r.nextInt(available.size()));
		if(removed) available.add(person);
		person.givingTo = partner.name;
		partner.receivingFrom = person.name;
		available.remove(partner);
	}
	write();
	return nulls;
}

public Person find(String name) {
	// TODO switch to UIDs
	for(Person person : people) {
		if(person.name.equals(name)) {
			return person;
		}
	}
	return null;
}

public static void write() {
	new Runnable() {
		@Override
		public void run() {
			try {
				FileWriter writer = new FileWriter(new File("data.json"));
				JSONArray groups = new JSONArray();
				for(Group group : Group.groups) {
					groups.add(group.toJSON());
				}
				JSONObject object = new JSONObject();
				object.put("groups", groups);
				writer.write(object.toJSONString());
				writer.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}.run();
}

JSONObject toJSON() {
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

public void removeRule(Rule rule) {
	rules.remove(rule);
	write();
}

public void addFamily(String name) {
	Family family = new Family(this);
	family.name = name;
	families.add(family);
	write();
}

public void removeFamily(Family family) {
    families.remove(family);
    write();
}

public void addRule(Rule rule) {
	rules.add(rule);
	write();
}
}
