package com.thepaperpilot.giftexchange.core;

import com.thepaperpilot.giftexchange.desktop.JPerson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Family {

public String name;
protected ArrayList<Person> people;
private final Group parent;

public Family(Group parent) {
    this.parent = parent;
}

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

public void removePerson(Person person) {
    people.remove(person);
    Group.write();
}

public void remove() {
    parent.removeFamily(this);
}

public void addPerson() {
	people.add(new JPerson(this));
}
}
