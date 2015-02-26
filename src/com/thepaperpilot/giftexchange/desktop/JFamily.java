package com.thepaperpilot.giftexchange.desktop;

import com.thepaperpilot.giftexchange.core.Family;
import com.thepaperpilot.giftexchange.core.Group;
import com.thepaperpilot.giftexchange.core.Person;
import com.thepaperpilot.giftexchange.desktop.ui.FamilyHeader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

public class JFamily extends Family {

JPanel card;
private JPanel peopleCards;

public JFamily(JSONObject family, Group parent) {
	super(parent);
	name = family.get("name") == null ? "" : (String) family.get("name");
	if(family.get("people") != null && !((JSONArray) family.get("people")).isEmpty())
		for(Object person : (JSONArray) family.get("people")) {
			people.add(new JPerson((JSONObject) person, parent.families.indexOf(this), parent));
		}
	toCards();
}

public JFamily(Group group) {
	super(group);
	toCards();
}

public JPanel toCards() {
	card = new JPanel();
	card.setLayout(new BoxLayout(card, BoxLayout.PAGE_AXIS));
	card.add(new FamilyHeader(this).panel);
	peopleCards = new JPanel(new WrapLayout(FlowLayout.LEFT));
	peopleCards.setSize(1, 1);
	peopleCards.addComponentListener(new ComponentListener() {
		@Override
		public void componentResized(ComponentEvent componentEvent) {
			card.revalidate();
		}

		@Override
		public void componentMoved(ComponentEvent componentEvent) {

		}

		@Override
		public void componentShown(ComponentEvent componentEvent) {

		}

		@Override
		public void componentHidden(ComponentEvent componentEvent) {

		}
	});
	for(Person person : people) {
		peopleCards.add(((JPerson) person).getCard().card);
	}
	card.add(peopleCards);
	return card;
}

public ArrayList<Person> getPeople() {
	return people;
}

public void removePerson(Person person) {
    super.removePerson(person);
    if(card != null) {
        peopleCards.remove(((JPerson) person).getCard().card);
        peopleCards.updateUI();
    }
}

public void addPerson() {
	super.addPerson();
	if(card != null) {
		peopleCards.add(((JPerson) people.get(people.size() - 1)).getCard().card);
		peopleCards.updateUI();
	}
}
}
