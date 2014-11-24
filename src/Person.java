import java.util.ArrayList;

class Person {
final String name;
private final int family;
final int group;
String givingTo;
String receivingFrom;

public Person(String name, int family, int group) {
    this.name = name;
    this.family = family;
    this.group = group;
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

public String toString() {
    return name + "," + givingTo + "," + receivingFrom;
}
}
