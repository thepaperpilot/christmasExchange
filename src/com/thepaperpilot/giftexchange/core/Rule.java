package com.thepaperpilot.giftexchange.core;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Rule {
protected final ArrayList<Token> sources;
protected final ArrayList<Token> whitelist;
protected final ArrayList<Token> blacklist;

protected Rule(JSONObject rule) {
	sources = find((JSONArray) rule.get("source"), Tokens.SOURCE);
	whitelist = find((JSONArray) rule.get("whitelist"), Tokens.WHITE);
	blacklist = find((JSONArray) rule.get("blacklist"), Tokens.BLACK);
}

protected ArrayList<Token> find(JSONArray JSONtokens, Tokens type) {
	ArrayList<Token> tokens = new ArrayList<>();
	if(JSONtokens == null)
		return tokens;
	for(Object object : JSONtokens) {
		tokens.add(new Token((JSONObject) object, this));
	}
	return tokens;
}

boolean checkRule(Person check) {
	for(Token token : whitelist)
		if(!token.check(check))
			return false;
	for(Token token : blacklist)
		if(token.check(check))
			return false;
	return true;
}

JSONObject toJSON() {
	JSONArray sources = new JSONArray();
	for(Token token : this.sources)
		sources.add(token.toJSON());
	JSONArray whites = new JSONArray();
	for(Token token : this.whitelist)
		whites.add(token.toJSON());
	JSONArray blacks = new JSONArray();
	for(Token token : this.blacklist)
		blacks.add(token.toJSON());
	JSONObject object = new JSONObject();
	object.put("source", sources);
	object.put("whitelist", whites);
	object.put("blacklist", blacks);
	return object;
}

boolean checkSource(Person person) {
	for(Token token : sources)
		if(token.check(person))
			return true;
	return false;
}

boolean checkWhite(Person person) {
	for(Token token : whitelist)
		if(token.check(person))
			return true;
	return false;
}

public boolean checkBlack(Person person) {
	for(Token token : blacklist)
		if(token.check(person))
			return true;
	return false;
}

protected void add(Tokens type) {
	switch(type) {
		default:
		case SOURCE:
			sources.add(new Token(this));
			break;
		case WHITE:
			whitelist.add(new Token(this));
			break;
		case BLACK:
			blacklist.add(new Token(this));
			break;
	}
	Group.write();
}

protected void remove(Token token, Tokens type) {
	switch(type) {
		default:
		case SOURCE:
			sources.remove(token);
			break;
		case WHITE:
			whitelist.remove(token);
			break;
		case BLACK:
			blacklist.remove(token);
			break;
	}
	Group.write();
}

public enum Tokens {SOURCE, WHITE, BLACK}
}
