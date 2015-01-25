import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.PatternSyntaxException;

public class Token {

public final Rule parent;
private String token;
private boolean checkNames;
private boolean checkGroups;
private boolean matchCase;
private boolean useRegex;
private boolean invert;
private JPanel panel;
private JButton expression;

public Token(JSONObject object, Rule parent) {
	this(
			object.get("token") == null ? "" : String.valueOf(object.get("token")),
			object.get("names") != null && (boolean) object.get("names"),
			object.get("groups") != null && (boolean) object.get("groups"),
			object.get("case") != null && (boolean) object.get("case"),
			object.get("regex") != null && (boolean) object.get("regex"),
			object.get("invert") != null && (boolean) object.get("invert"),
			parent);
}

public Token(Rule parent) {
	this("", true, false, false, false, false, parent);
}

public Token(String token, boolean checkNames, boolean checkGroups, boolean matchCase, boolean useRegex, boolean invert, Rule parent) {
	this.token = token;
	this.checkNames = checkNames;
	this.checkGroups = checkGroups;
	this.matchCase = matchCase;
	this.useRegex = useRegex;
	this.invert = invert;
	this.parent = parent;

	expression.setText(token);
	expression.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			new EditToken(Token.this).create();
		}
	});
}

public boolean isCheckNames() {
	return checkNames;
}

public boolean isCheckGroups() {
	return checkGroups;
}

public boolean isMatchCase() {
	return matchCase;
}

public String getToken() {
	return token;
}

public void setToken(Token token) {
	this.token = token.token;
	this.checkNames = token.checkNames;
	this.checkGroups = token.checkGroups;
	this.matchCase = token.matchCase;
	this.useRegex = token.useRegex;
	this.invert = token.invert;

	expression.setText(this.token);
	parent.card.update();
	Parser.write();
}

public boolean isUseRegex() {
	return useRegex;
}

public boolean isInvert() {
	return invert;
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
				} catch(PatternSyntaxException ignored) {
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
				} catch(PatternSyntaxException ignored) {
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
				} catch(PatternSyntaxException ignored) {
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
				} catch(PatternSyntaxException ignored) {
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

public void deleteToken() {
	parent.remove(this);
	parent.card.update();
}
}
