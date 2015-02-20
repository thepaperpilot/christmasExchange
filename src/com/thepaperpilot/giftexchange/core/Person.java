package com.thepaperpilot.giftexchange.core;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Person {
private final Family parent;
public boolean participating;
public String name;
public String group;
public String givingTo;
public String receivingFrom;
public boolean lockGive;
public boolean lockReceive;

protected Person(JSONObject person, Family parent) {
    this.parent = parent;
    name = person.get("name") == null ? "" : String.valueOf(person.get("name"));
	group = person.get("group") == null ? "" : String.valueOf(person.get("group"));
	givingTo = (String) person.get("giving");
	receivingFrom = (String) person.get("receiving");
	lockGive = person.get("lockGive") != null && (boolean) person.get("lockGive");
	lockReceive = person.get("lockReceive") != null && (boolean) person.get("lockReceive");
	participating = person.get("participating") == null || (boolean) person.get("participating");
}

JSONObject toJSON() {
	JSONObject person = new JSONObject();
	person.put("name", name);
	person.put("group", group);
	person.put("giving", givingTo);
	person.put("receiving", receivingFrom);
	person.put("lockGive", lockGive);
	person.put("lockReceive", lockReceive);
	person.put("participating", participating);
	return person;
}

void applyRules(ArrayList<Person> people, ArrayList<Rule> rules) {
	int i = 0;
	outer:
	while(i < people.size()) {
		Person person = people.get(i);
		for(Rule rule : rules)
			if(rule.checkSource(this) && !rule.checkRule(person)) {
				people.remove(person);
				continue outer;
			}
		i++;
	}
}

public void remove() {
    parent.remove(this);
}
}
