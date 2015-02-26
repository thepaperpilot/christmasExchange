package com.thepaperpilot.giftexchange.core;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Person {
public int parent;
public boolean participating;
public String name;
public String group;
public boolean lock;

protected Person(JSONObject person, int parent) {
  this.parent = parent;
  name = person.get("name") == null ? "" : String.valueOf(person.get("name"));
	group = person.get("group") == null ? "" : String.valueOf(person.get("group"));
	participating = person.get("participating") == null || (boolean) person.get("participating");
}

public Person(int parent) {
	this.parent = parent;
	name = "";
	group = "";
	participating = true;
}

JSONObject toJSON() {
	JSONObject person = new JSONObject();
	person.put("name", name);
	person.put("group", group);
	person.put("participating", participating);
	return person;
}

public void remove() {
    Group.get(parent).removePerson(this);
}

public boolean compat(ArrayList<Person> people, ArrayList<Rule> rules) {
	for(Person person : people)
		for(Rule rule : rules)
			if(rule.checkSource(this) && !rule.checkRule(person))
				return false;
	return true;
}
}
