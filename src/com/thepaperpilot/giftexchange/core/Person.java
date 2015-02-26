package com.thepaperpilot.giftexchange.core;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Person {
private final Group parentParent;
public int parent;
public String name;
public String group;
public boolean lock;

protected Person(JSONObject person, int parent, Group parentParent) {
  this.parent = parent;
	this.parentParent = parentParent;
	name = person.get("name") == null ? "" : String.valueOf(person.get("name"));
	group = person.get("group") == null ? "" : String.valueOf(person.get("group"));
}

protected Person(int parent, Group parentParent) {
	this.parent = parent;
	this.parentParent = parentParent;
	name = "";
	group = "";
}

JSONObject toJSON() {
	JSONObject person = new JSONObject();
	person.put("name", name);
	person.put("group", group);
	return person;
}

public void remove() {
	parentParent.get(parent).removePerson(this);
}

public boolean compat(ArrayList<Person> people, ArrayList<Rule> rules) {
	for(Person person : people)
		for(Rule rule : rules)
			if(rule.checkSource(person) && !rule.checkRule(this))
				return false;
	return true;
}
}
