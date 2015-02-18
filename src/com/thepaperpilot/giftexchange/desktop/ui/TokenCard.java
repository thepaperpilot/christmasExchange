package com.thepaperpilot.giftexchange.desktop.ui;

import com.thepaperpilot.giftexchange.core.Group;
import com.thepaperpilot.giftexchange.core.Person;
import com.thepaperpilot.giftexchange.core.Rule;
import com.thepaperpilot.giftexchange.core.Token;
import com.thepaperpilot.giftexchange.desktop.JRule;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.PatternSyntaxException;

public class TokenCard extends Token {

public final Rule parent;
public final Rule.Tokens type;
private JPanel panel;
private JButton expression;

public TokenCard(JSONObject object, Rule parent, Rule.Tokens type) {
	super(object, parent);
	this.parent = parent;
	this.type = type;

	expression.setText(token);
	expression.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			new EditToken(TokenCard.this).create();
		}
	});
}

public TokenCard(Rule parent, Rule.Tokens type) {
	super(parent);
	this.parent = parent;
	this.type = type;

	expression.setText(token);
	expression.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			new EditToken(TokenCard.this).create();
		}
	});
}

public TokenCard(String text, boolean selected, boolean selected1, boolean selected2, boolean selected3, boolean selected4, Rule parent, Rule.Tokens type) {
	this(parent, type);
	token = text;
	checkNames = selected;
	checkGroups = selected1;
	matchCase = selected2;
	useRegex = selected3;
	invert = selected4;
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

public void setToken(TokenCard token) {
	this.token = token.token;
	this.checkNames = token.checkNames;
	this.checkGroups = token.checkGroups;
	this.matchCase = token.matchCase;
	this.useRegex = token.useRegex;
	this.invert = token.invert;

	expression.setText(this.token);
	Group.write();
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
	((JRule) parent).remove(this, type);
	((JRule) parent).getCard().update();
}

}
