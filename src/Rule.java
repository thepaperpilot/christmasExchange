import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.util.ArrayList;

class Rule {
final ArrayList<Person> source;
private final ArrayList<Person> whitelist;
private final ArrayList<Person> blacklist;
private final JSONObject jsonObject;

public Rule(JSONObject rule, ArrayList<Person> people) {
	jsonObject = rule;
	source = find((JSONArray) rule.get("source"), people);
	whitelist = find((JSONArray) rule.get("whitelist"), people);
	blacklist = find((JSONArray) rule.get("blacklist"), people);
}

private static ArrayList<Person> find(JSONArray rules, ArrayList<Person> source) {
	ArrayList<Person> out = new ArrayList<>();
	if (rules == null || rules.isEmpty())
		return out;
	if (source == null || source.isEmpty())
		return out;
	for (Object rule : rules) {
		if (rule instanceof Number)
			for (Person person : source) {
				if (person.group == ((Number) rule).intValue())
					out.add(person);
			}
		else if (rule instanceof String) {
			for (Person person : source) {
				if (person.name.equals(rule))
					out.add(person);
			}
		}
	}
	return out;
}

public boolean checkRule(Person check) {
	return whitelist.contains(check) && !blacklist.contains(check);
}

public JSONObject toJSON() {
	// TODO add support for dynamically changing(editable) rules
	return jsonObject;
}

public JPanel toCard() {
	return new RuleCard(this).card;
}
}
