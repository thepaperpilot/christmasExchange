package com.thepaperpilot.giftexchange.core;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Group {

public static List<Group> groups;

public static ArrayList<Family> families = new ArrayList<>();
protected ArrayList<Person> people = new ArrayList<>();
protected ArrayList<Rule> rules = new ArrayList<>();
protected String name = "";
protected int groupSize = 1;

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public ArrayList<Person> getPeople() {
	return people;
}

public void randomize() {
	Random r = new Random();
	int numFamilies = (int) Math.ceil(people.size() / (float) groupSize);
	families = new ArrayList<>(numFamilies);
	for(int i = 0; i < numFamilies; i++) {
		addFamily("Group " + (i + 1));
	}
	for(Person person : people) {
		if(person.participating && !person.lock)
			person.parent = -1;
		else if(person.participating)
			families.get(person.parent).people.add(person);
	}
	// Place people affected by rules, in order of rule priority
	for(Rule rule : rules) {
		for(Person person : people) {
			if(!rule.checkSource(person))
				continue;
			if(!person.participating || person.lock)
				continue;
			ArrayList<Family> familiesAvailable = new ArrayList<>(families);
			for (Iterator<Family> iterator = familiesAvailable.iterator(); iterator.hasNext(); ) {
				Family family = iterator.next();
				if (!person.compat(family.people, rules))
					iterator.remove();
				if (family.people.size() >= groupSize)
					iterator.remove();
			}
			if(familiesAvailable.size() <= 0)
				continue;
			int family = r.nextInt(familiesAvailable.size());
			person.parent = families.indexOf(familiesAvailable.get(family));
		}
	}
	// Sort the rest of the people, up until all groups have their max size
	for(Person person : people) {
		if(!person.participating)
			continue;
		ArrayList<Family> familiesAvailable = new ArrayList<>(families);
		for (Iterator<Family> iterator = familiesAvailable.iterator(); iterator.hasNext(); ) {
			Family family = iterator.next();
			if (family.people.size() >= groupSize)
				iterator.remove();
		}
		if(familiesAvailable.size() <= 0)
			continue;
		int family = r.nextInt(familiesAvailable.size());
		person.parent = families.indexOf(familiesAvailable.get(family));
	}
	for(Person person : people) {
		if(person.parent != -1)
			continue;
		if(!person.participating)
			continue;
		ArrayList<Family> familiesAvailable = new ArrayList<>(families);
		Collections.sort(familiesAvailable);
		int family = r.nextInt(familiesAvailable.size());
		person.parent = families.indexOf(familiesAvailable.get(family));
	}
	write();
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

public static Family get(int parent) {
	return families.get(parent);
}

public void addFamily() {
	addFamily("Group " + (families.size() + 1));
}
}
