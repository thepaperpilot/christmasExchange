import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.util.ArrayList;

class Rule {
final ArrayList<Person> source;
private final ArrayList<Person> whitelist;
private final ArrayList<Person> blacklist;

public Rule(JSONObject rule) {
    source = new ArrayList<>();
    for(Object person : (JSONArray) rule.get("source")) {
        source.add(new Person((JSONObject) person));
    }
    whitelist = new ArrayList<>();
    for(Object person : (JSONArray) rule.get("whitelist")) {
        whitelist.add(new Person((JSONObject) person));
    }
    blacklist = new ArrayList<>();
    for(Object person : (JSONArray) rule.get("blacklist")) {
        blacklist.add(new Person((JSONObject) person));
    }
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
