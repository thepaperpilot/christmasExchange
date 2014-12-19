import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.util.ArrayList;

class Rule {
final ArrayList<Person> source;
private final ArrayList<Person> whitelist;
private final ArrayList<Person> blacklist;

public Rule(JSONObject rule, ArrayList<Person> people) {
    source = Parser.find((JSONArray) rule.get("source"), people);
    whitelist = Parser.find((JSONArray) rule.get("whitelist"), people);
    blacklist = Parser.find((JSONArray) rule.get("blacklist"), people);
}

public boolean checkRule(Person check) {
    return whitelist.contains(check) && !blacklist.contains(check);
}

public JSONObject toJSON() {
    JSONObject rule = new JSONObject();
    JSONArray source = new JSONArray();
    for(Person person : this.source) {
        source.add(person.toJSON());
    }
    rule.put("source", source);
    JSONArray whitelist = new JSONArray();
    for(Person person : this.whitelist) {
        whitelist.add(person.toJSON());
    }
    rule.put("whitelist", whitelist);
    JSONArray blacklist = new JSONArray();
    for(Person person : this.blacklist) {
        blacklist.add(person.toJSON());
    }
    rule.put("blacklist", blacklist);
    return rule;
}

public JPanel toCard() {
    return new RuleCard(this).card;
}
}
