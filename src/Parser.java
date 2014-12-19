import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {

public static ArrayList<Group> read() {
	ArrayList<Group> groups = new ArrayList<>();
	try {
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(new FileReader("data.json"));
		for(Object group : (JSONArray) object.get("groups")) {
			switch ((String) ((JSONObject) group).get("type")) {
				default:
					break;
				case "family":
					groups.add(new FamilyGroup((JSONObject) group));
					break;
				// TODO add other group types
			}
		}
	} catch (IOException e) {
		ChristmasExchange.error("Can't read the file. Please fix and try again.");
		e.printStackTrace();
	} catch (NullPointerException | ParseException e) {
		ChristmasExchange.error("Error parsing the file. Please fix and try again.");
		e.printStackTrace();
	}
	return groups;
}

public static void write() {
	try {
		FileWriter writer = new FileWriter(new File("data_out.json"));
		JSONArray groups = new JSONArray();
		for(Group group : ChristmasExchange.groups) {
			groups.add(group.toJSON());
		}
		writer.write(groups.toJSONString());
	} catch (IOException e) {
		ChristmasExchange.error("JSON file is currently in use. Please close any applications accessing the file and try again.");
		e.printStackTrace();
	}
}

public static void createCSV() {
    StringBuilder csv = new StringBuilder();
    csv.append("Name,Giving To,Recieving From\n");
    for (Group group : ChristmasExchange.groups) {
        csv.append(group.toCSV());
    }
    try {
        FileWriter writer = new FileWriter("output.csv");
        writer.write(csv.toString());
        writer.close();
    } catch (IOException e) {
        ChristmasExchange.error("CSV file is currently in use. Please close any applications accessing the file and try again.");
        e.printStackTrace();
    }
}

public static ArrayList<Person> find(JSONArray rules, ArrayList<Person> source) {
	ArrayList<Person> out = new ArrayList<>();
	if(rules == null || rules.isEmpty())
		return out;
	if(source == null || source.isEmpty())
		return out;
	for(Object rule : rules) {
		if(rule instanceof Number)
			for(Person person : source) {
				if (person.group == ((Number) rule).intValue())
					out.add(person);
			}
		else if(rule instanceof String) {
			for (Person person : source) {
				if (person.name.equals(rule))
					out.add(person);
			}
		}
	}
	return out;
}
}
