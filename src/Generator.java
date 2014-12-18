import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

class Generator {
private static final JSONParser parser = new JSONParser();

public static ArrayList<ArrayList<Person>> generate() {
    Random r = new Random();
    ArrayList<String> optout = getOptout("current.json");
    ArrayList<ArrayList<Person>> groups = getGroups("current.json", optout);
    int nulls = 0;
    for (ArrayList<Person> people : groups) {
        ArrayList<Rule> rules = getRules("current.json", people);
        ArrayList<Person> available = new ArrayList<>(people);
        for (Rule rule : rules) {
            for (Person person : people) {
                if (!rule.source.contains(person))
                    continue;
                if (person.givingTo != null)
                    continue;
                ArrayList<Person> specificAvailable = person.getValidPartners(available, rules);
                if (specificAvailable.size() <= 0)
                    continue;
                Person partner = specificAvailable.get(specificAvailable.size() == 1 ? 0 : r.nextInt(specificAvailable.size()));
                person.givingTo = partner.name;
                partner.receivingFrom = person.name;
                available.remove(partner);
            }
        }
        for (Person person : people) {
            if (person.givingTo != null)
                continue;
            if (available.size() <= 0) {
                nulls++;
                continue;
            }
            boolean removed = available.remove(person);
            Person partner = available.get(available.size() == 1 ? 0 : r.nextInt(available.size()));
            if (removed) available.add(person);
            person.givingTo = partner.name;
            partner.receivingFrom = person.name;
            available.remove(partner);
        }
    }
    if (nulls > 0) {
        error(nulls + (nulls == 1 ? " person wasn't" : " people weren't") + " able to be sorted.");
    }
    createCSV(groups);
    return groups;
}

private static void error(String error) {
    ChristmasExchange.instance.error.append("\n" + error);
}

private static ArrayList<String> getOptout(String file) {
    try {
        JSONArray names = (JSONArray) ((JSONObject) parser.parse(new FileReader(file))).get("Opt-out");
        ArrayList<String> optout = new ArrayList<>();
        for (Object name : names) {
            optout.add((String) name);
        }
        return optout;
    } catch (ParseException | IOException e) {
        error("Can't read the file. Please fix and try again.");
        e.printStackTrace();
    }
    return new ArrayList<>();
}

private static ArrayList<Rule> getRules(String file, ArrayList<Person> people) {
    try {
        JSONArray JSONrules = (JSONArray) ((JSONObject) parser.parse(new FileReader(file))).get("Rules");
        ArrayList<Rule> rules = new ArrayList<>();
        for (Object JSONrule : JSONrules) {
            JSONObject rule = (JSONObject) JSONrule;
            ArrayList<Person> source = getPeople((JSONArray) rule.get("Source"), people);
            ArrayList<Person> whitelist = getPeople((JSONArray) rule.get("Whitelist"), people);
            ArrayList<Person> blacklist = getPeople((JSONArray) rule.get("Blacklist"), people);
            rules.add(new Rule(source, whitelist, blacklist));
        }
        return rules;
    } catch (ParseException | IOException e) {
        error("Can't read the file. Please fix and try again.");
        e.printStackTrace();
    }
    return new ArrayList<>();
}

private static ArrayList<Person> getPeople(JSONArray source, ArrayList<Person> people) {
    if (source == null || source.isEmpty())
        return new ArrayList<>();
    ArrayList<Person> out = new ArrayList<>();
    for (Object object : source) {
        if (object instanceof Number) {
            for (Person person : people) {
                if (person.group == ((Number) object).intValue()) {
                    out.add(person);
                }
            }
        } else if (object instanceof String) {
            for (Person person : people) {
                if (person.name.equals(object)) {
                    out.add(person);
                    break;
                }
            }
        }
    }
    return out;
}

private static ArrayList<ArrayList<Person>> getGroups(String file, ArrayList<String> optout) {
    try {
        JSONArray groups = (JSONArray) ((JSONObject) parser.parse(new FileReader(file))).get("Groups");
        ArrayList<ArrayList<Person>> people = new ArrayList<>();
        for (Object groupObject : groups) {
            JSONArray jsonFamilies = (JSONArray) groupObject;
            ArrayList<Person> group = new ArrayList<>();
            for (int i1 = 0; i1 < jsonFamilies.size(); i1++) {
                Object family = jsonFamilies.get(i1);
                JSONArray jsonFamily = (JSONArray) family;
                for (Object person : jsonFamily) {
                    JSONObject jsonPerson = (JSONObject) person;
                    if (optout.contains(jsonPerson.get("Name")))
                        continue;
                    group.add(new Person((String) jsonPerson.get("Name"), i1, ((Number) jsonPerson.get("Group")).intValue()));
                }
            }
            people.add(group);
        }
        return people;
    } catch (ParseException | IOException e) {
        error("Can't read the file. Please fix and try again.");
        e.printStackTrace();
    }
    return new ArrayList<>();
}

private static void createCSV(ArrayList<ArrayList<Person>> input) {
    StringBuilder csv = new StringBuilder();
    csv.append("Name,Giving To,Recieving From\n");
    for (ArrayList<Person> group : input) {
        for (Person person : group) {
            csv.append(person).append("\n");
        }
        csv.append("\n");
    }
    try {
        FileWriter writer = new FileWriter("output.csv");
        writer.write(csv.toString());
        writer.close();
    } catch (IOException e) {
        error("File is currently in use. Please close excel and try again.");
        e.printStackTrace();
    }
}
}
