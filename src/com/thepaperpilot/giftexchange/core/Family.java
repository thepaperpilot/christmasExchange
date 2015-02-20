package com.thepaperpilot.giftexchange.core;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Family {

protected String name;
protected ArrayList<Person> people;

JSONObject toJSON() {
	JSONObject family = new JSONObject();
	family.put("name", name);
	JSONArray people = new JSONArray();
	for(Person person : this.people) {
		people.add(person.toJSON());
	}
	family.put("people", people);
	return family;
}

public void remove(Person person) {
    people.remove(person);
    Group.write();
}
}
