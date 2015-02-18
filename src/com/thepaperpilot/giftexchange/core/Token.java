package com.thepaperpilot.giftexchange.core;

import org.json.simple.JSONObject;

import java.util.regex.PatternSyntaxException;

public class Token {

protected final Rule parent;
protected String token;
protected boolean checkNames;
protected boolean checkGroups;
protected boolean matchCase;
protected boolean useRegex;
protected boolean invert;

protected Token(JSONObject object, Rule parent) {
	this(
			    object.get("token") == null ? "" : String.valueOf(object.get("token")),
			    object.get("names") != null && (boolean) object.get("names"),
			    object.get("groups") != null && (boolean) object.get("groups"),
			    object.get("case") != null && (boolean) object.get("case"),
			    object.get("regex") != null && (boolean) object.get("regex"),
			    object.get("invert") != null && (boolean) object.get("invert"),
			    parent);
}

protected Token(String token, boolean checkNames, boolean checkGroups, boolean matchCase, boolean useRegex, boolean invert, Rule parent) {
	this.token = token;
	this.checkNames = checkNames;
	this.checkGroups = checkGroups;
	this.matchCase = matchCase;
	this.useRegex = useRegex;
	this.invert = invert;
	this.parent = parent;
}

protected Token(Rule parent) {
	this("", true, false, false, false, false, parent);
}

JSONObject toJSON() {
	JSONObject object = new JSONObject();
	object.put("token", token);
	object.put("names", checkNames);
	object.put("groups", checkGroups);
	object.put("case", matchCase);
	object.put("regex", useRegex);
	object.put("invert", invert);
	return object;
}

boolean check(Person person) {
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
}
