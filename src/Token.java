import org.json.simple.JSONObject;

import javax.swing.*;
import java.util.regex.PatternSyntaxException;

public class Token {

private String token;
private boolean checkNames;
private boolean checkGroups;
private boolean matchCase;
private boolean useRegex;
private boolean invert;
private JPanel panel;
private JTextArea expression;

public Token(JSONObject object) {
	this.token = object.get("token") == null ? "" : String.valueOf(object.get("token"));
	this.checkNames = object.get("names") != null && (boolean) object.get("names");
	this.checkGroups = object.get("groups") != null && (boolean) object.get("groups");
	this.matchCase = object.get("case") != null && (boolean) object.get("case");
	this.useRegex = object.get("regex") != null && (boolean) object.get("regex");
	this.invert = object.get("invert") != null && (boolean) object.get("invert");

	expression.setText(token);
}

public Token(String token, boolean checkNames, boolean checkGroups, boolean matchCase, boolean useRegex, boolean invert) {
	this.token = token;
	this.checkNames = checkNames;
	this.checkGroups = checkGroups;
	this.matchCase = matchCase;
	this.useRegex = useRegex;
	this.invert = invert;

	expression.setText(token);
}

public JSONObject toJSON() {
	JSONObject object = new JSONObject();
	object.put("token", token);
	object.put("names", checkNames);
	object.put("groups", checkGroups);
	object.put("case", matchCase);
	object.put("regex", useRegex);
	object.put("invert", invert);
	return object;
}

public boolean check(Person person) {
	if(checkNames) {
		if(matchCase) {
			if(useRegex) {
				try {
					if(person.name.matches(token) == !invert)
						return true;
				} catch (PatternSyntaxException ignored) {
					return false;
				}
			} else {
				if(person.name.contains(token) == !invert)
					return true;
			}
		} else {
			if(useRegex) {
				try {
					if(person.name.toLowerCase().matches(token.toLowerCase()) == !invert)
						return true;
				} catch (PatternSyntaxException ignored) {
					return false;
				}
			} else {
				if(person.name.toLowerCase().contains(token.toLowerCase()) == !invert)
					return true;
			}
		}
	}
	if(checkGroups) {
		if(matchCase) {
			if(useRegex) {
				try {
					if(person.group.matches(token) == !invert)
						return true;
				} catch (PatternSyntaxException ignored) {
					return false;
				}
			} else {
				if(person.group.contains(token) == !invert)
					return true;
			}
		} else {
			if(useRegex) {
				try {
					if(person.group.toLowerCase().matches(token.toLowerCase()) == !invert)
						return true;
				} catch (PatternSyntaxException ignored) {
					return false;
				}
			} else {
				if(person.group.toLowerCase().contains(token.toLowerCase()) == !invert)
					return true;
			}
		}
	}
	return false;
}

public JPanel toCard() {
	return panel;
}

private void createUIComponents() {
	panel = new Shadow();
}
}
