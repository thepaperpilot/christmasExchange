package com.thepaperpilot.giftexchange.desktop;

import com.thepaperpilot.giftexchange.core.Family;
import com.thepaperpilot.giftexchange.core.Group;
import com.thepaperpilot.giftexchange.core.Person;
import com.thepaperpilot.giftexchange.core.Rule;
import com.thepaperpilot.giftexchange.desktop.ui.GiftExchange;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class JGroup extends Group {
private JPanel ruleCards;
private Box familyCards;

public JGroup(JSONObject group) {
	name = group.get("name") == null ? "" : (String) group.get("name");
	if(group.get("families") != null && !((JSONArray) group.get("families")).isEmpty())
		for(Object family : (JSONArray) group.get("families")) {
			JFamily fam = new JFamily((JSONObject) family, this);
			families.add(fam);
			people.addAll(fam.getPeople());
		}
	if(group.get("rules") != null && !((JSONArray) group.get("rules")).isEmpty())
		for(Object rule : (JSONArray) group.get("rules")) {
			rules.add(new JRule((JSONObject) rule));
		}
}

public JGroup(String text) {
	name = text;
	families = new ArrayList<>();
	people = new ArrayList<>();
	rules = new ArrayList<>();
}

public Box peopleCards() {
	familyCards = new Box(BoxLayout.PAGE_AXIS);
	for(Family family : families) {
		familyCards.add(((JFamily) family).toCards());
	}
	return familyCards;
}

public JPanel ruleCards() {
	ruleCards = new JPanel(new WrapLayout(FlowLayout.LEFT));
	for(Rule rule : rules) {
		ruleCards.add(((JRule) rule).getCard().card);
	}
	return ruleCards;
}

public int randomize() {
	int nulls = super.randomize();
	new Thread(new Runnable() {
		@Override
		public void run() {
			for(Person person : people) {
				JPerson jPerson = (JPerson) person;
				jPerson.getCard().giving.setText(person.givingTo);
				jPerson.getCard().receiving.setText(person.receivingFrom);
			}
		}
	}).start();
	if(nulls != 0) {
		GiftExchange.error("Unable to sort " + nulls + (nulls == 1 ? " person" : " people") + "\nPlease try again. If this issue persists, try adding or removing people");
	}
	return nulls;
}

public void removeRule(Rule rule) {
	super.removeRule(rule);
	ruleCards.remove(((JRule) rule).getCard().card);
}

public void removeFamily(Family family) {
    super.removeFamily(family);
    if(familyCards != null) {
        familyCards.remove(((JFamily) family).card);
        familyCards.updateUI();
    }
}
}
