package com.thepaperpilot.giftexchange.desktop;

import com.thepaperpilot.giftexchange.core.Family;
import com.thepaperpilot.giftexchange.core.Group;
import com.thepaperpilot.giftexchange.core.Person;
import com.thepaperpilot.giftexchange.core.Rule;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class JGroup extends Group {
private JPanel ruleCards;

public JGroup(JSONObject group) {
	name = group.get("name") == null ? "" : (String) group.get("name");
	if(group.get("families") != null && !((JSONArray) group.get("families")).isEmpty())
		for(Object family : (JSONArray) group.get("families")) {
			JFamily fam = new JFamily((JSONObject) family);
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
	Box group = new Box(BoxLayout.PAGE_AXIS);
	for(Family family : families) {
		group.add(((JFamily) family).toCards());
	}
	return group;
}

public JPanel ruleCards() {
	ruleCards = new JPanel(new WrapLayout(FlowLayout.LEFT));
	ruleCards.setBackground(Color.white);
	for(Rule rule : rules) {
		ruleCards.add(((JRule) rule).getCard().card);
	}
	return ruleCards;
}

public void randomize() {
	super.randomize();
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
}

public void removeRule(Rule rule) {
	super.removeRule(rule);
	ruleCards.remove(((JRule) rule).getCard().card);
}
}
