import org.json.simple.JSONObject;

import javax.swing.*;
import java.util.ArrayList;

class Person {
final String name;
private final int family;
final int group;
String givingTo;
String receivingFrom;

public Person(JSONObject person) {
    name = (String) person.get("name");
    family = (int) person.get("family");
    group = (int) person.get("group");
    givingTo = (String) person.get("giving");
    receivingFrom = (String) person.get("receiving");
}

public ArrayList<Person> getValidPartners(ArrayList<Person> people, ArrayList<Rule> rules) {
    ArrayList<Person> out = new ArrayList<>();
    outer:
    for (Person person : people) {
        if (person.family == family)
            continue;
        for (Rule rule : rules)
            if (rule.source.contains(this) && !rule.checkRule(person))
                continue outer;
        out.add(person);
    }
    return out;
}

public JSONObject toJSON() {
    JSONObject person = new JSONObject();
    person.put("name", name);
    person.put("family", family);
    person.put("group", group);
    person.put("giving", givingTo);
    person.put("receiving", receivingFrom);
    return person;
}

public JPanel toCard() {
    return new PersonCard(this).card;
}

@Override
public String toString() {
    return name + "," + givingTo + "," + receivingFrom;
}
}
