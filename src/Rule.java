import java.util.ArrayList;

class Rule {
final ArrayList<Person> source;
private final ArrayList<Person> whitelist;
private final ArrayList<Person> blacklist;

public Rule(ArrayList<Person> source, ArrayList<Person> whitelist, ArrayList<Person> blacklist) {
    this.source = source;
    this.whitelist = whitelist;
    this.blacklist = blacklist;
}

public boolean checkRule(Person check) {
    return whitelist.contains(check) && !blacklist.contains(check);
}
}
