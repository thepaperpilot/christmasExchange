package com.thepaperpilot.giftexchange.desktop;

import com.thepaperpilot.giftexchange.core.Group;
import com.thepaperpilot.giftexchange.core.Rule;
import com.thepaperpilot.giftexchange.core.Token;
import com.thepaperpilot.giftexchange.desktop.ui.RuleCard;
import com.thepaperpilot.giftexchange.desktop.ui.TokenCard;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class JRule extends Rule {
private final RuleCard card;

public JRule(JSONObject rule) {
	super(rule);

	card = new RuleCard(this);
}

public RuleCard getCard() {
	return card;
}

public void remove(TokenCard token, Tokens type) {
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

public JPanel getCard(Tokens source) {
	JPanel panel = new JPanel();
	panel.setBackground(Color.white);
	panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
	switch(source) {
		default:
		case SOURCE:
			for(Token token : sources) {
				panel.add(((TokenCard) token).toCard());
			}
			break;
		case BLACK:
			for(Token token : blacklist) {
				panel.add(((TokenCard) token).toCard());
			}
			break;
		case WHITE:
			for(Token token : whitelist) {
				panel.add(((TokenCard) token).toCard());
			}
			break;
	}
	return panel;
}

protected ArrayList<Token> find(JSONArray JSONtokens, Tokens type) {
	ArrayList<Token> tokens = new ArrayList<>();
	if(JSONtokens == null)
		return tokens;
	for(Object object : JSONtokens) {
		tokens.add(new TokenCard((JSONObject) object, this, type));
	}
	return tokens;
}

public void add(Tokens type) {
	switch(type) {
		default:
		case SOURCE:
			sources.add(new TokenCard(this, type));
			break;
		case WHITE:
			whitelist.add(new TokenCard(this, type));
			break;
		case BLACK:
			blacklist.add(new TokenCard(this, type));
			break;
	}
	Group.write();
}
}
