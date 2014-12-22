import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class Parser {

public static ArrayList<Group> read() {
	ArrayList<Group> groups = new ArrayList<>();
	try {
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(new FileReader("data.json"));
		for (Object group : (JSONArray) object.get("groups")) {
			switch ((String) ((JSONObject) group).get("type")) {
				default:
					break;
				case "family":
					groups.add(new FamilyGroup((JSONObject) group));
					break;
				// TODO add other group types
			}
		}
	} catch (IOException | NullPointerException | ParseException ignored) {
	}
	return groups;
}

public static void write() {
	try {
		FileWriter writer = new FileWriter(new File("data.json"));
		JSONArray groups = new JSONArray();
		for (Group group : ChristmasExchange.groups) {
			groups.add(group.toJSON());
		}
		JSONObject object = new JSONObject();
		object.put("groups", groups);
		writer.write(object.toJSONString());
		writer.close();
	} catch (IOException e) {
		ChristmasExchange.error("JSON file is currently in use. Please close any applications accessing the file and try again.");
		e.printStackTrace();
	}
}

}
