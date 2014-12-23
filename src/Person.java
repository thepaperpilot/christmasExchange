import org.json.simple.JSONObject;

import javax.swing.*;
import java.util.ArrayList;

class Person {
String name;
int group;
String givingTo;
String receivingFrom;
boolean lockGive;
boolean lockReceive;
public boolean participating;
private PersonCard card;

public Person(JSONObject person) {
	name = person.get("name") == null ? "" : (String) person.get("name");
	group = person.get("group") == null ? 0 : ((Long) person.get("group")).intValue();
	givingTo = (String) person.get("giving");
	receivingFrom = (String) person.get("receiving");
	lockGive = person.get("lockGive") != null && (boolean) person.get("lockGive");
	lockReceive = person.get("lockReceive") != null && (boolean) person.get("lockReceive");
	participating = person.get("participating") == null || (boolean) person.get("participating");

	card = new PersonCard(this);
}

public JSONObject toJSON() {
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

public String toCSV() {
	return name + "," + givingTo + "," + receivingFrom + "\n";
}

public void applyRules(ArrayList<Person> people, ArrayList<Rule> rules) {
	int i = 0;
	outer:
	while (i < people.size()) {
		Person person = people.get(i);
		for (Rule rule : rules)
			if (rule.source.contains(this) && !rule.checkRule(person)) {
				people.remove(person);
				continue outer;
			}
		i++;
	}
}

public PersonCard getCard() {
	return card;
}
}
