import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Rule {
private final ArrayList<Token> sources;
private final ArrayList<Token> whitelist;
private final ArrayList<Token> blacklist;
private final JSONObject jsonObject;
private final RuleCard card;

public Rule(JSONObject rule) {
	jsonObject = rule;
	sources = find((JSONArray) rule.get("source"));
	whitelist = find((JSONArray) rule.get("whitelist"));
	blacklist = find((JSONArray) rule.get("blacklist"));

	card = new RuleCard(this);
}

private ArrayList<Token> find(JSONArray JSONtokens) {
	ArrayList<Token> tokens = new ArrayList<>();
	if(JSONtokens == null)
		return tokens;
	for(Object object : JSONtokens) {
		tokens.add(new Token((JSONObject) object));
	}
	return tokens;
}

public boolean checkRule(Person check) {
	for(Token token : whitelist) {
		if(!token.check(check))
			return false;
	}
	for(Token token : blacklist) {
		if(token.check(check))
			return false;
	}
	return true;
}

public JSONObject toJSON() {
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

public RuleCard getCard() {
	return card;
}

public boolean checkSource(Person person) {
	for(Token token : sources) {
		if(token.check(person))
			return true;
	}
	return false;
}

public JPanel getSourcesCard() {
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
	for(Token token : sources) {
		panel.add(token.toCard());
	}
	return panel;
}

public JPanel getBlacksCard() {
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
	for(Token token : blacklist) {
		panel.add(token.toCard());
	}
	return panel;
}

public JPanel getWhitesCard() {
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
	for(Token token : whitelist) {
		panel.add(token.toCard());
	}
	return panel;
}

public void addSource(Token token) {
	sources.add(token);
	Parser.write();
}

public void addWhite(Token token) {
	whitelist.add(token);
	Parser.write();
}

public void addBlack(Token token) {
	blacklist.add(token);
	Parser.write();
}
}
