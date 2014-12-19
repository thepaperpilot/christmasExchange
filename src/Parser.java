import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {

public static ArrayList<Group> parse() {
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
	} catch (IOException | ParseException e) {
		return new ArrayList<>();
	}
	return groups;
}
}
