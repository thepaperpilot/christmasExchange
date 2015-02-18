package com.thepaperpilot.giftexchange.desktop;

import com.thepaperpilot.giftexchange.core.Family;
import com.thepaperpilot.giftexchange.core.Person;
import com.thepaperpilot.giftexchange.desktop.ui.FamilyHeader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

class JFamily extends Family {

public JFamily(JSONObject family) {
	name = family.get("name") == null ? "" : (String) family.get("name");
	people = new ArrayList<>();
	if(family.get("people") != null && !((JSONArray) family.get("people")).isEmpty())
		for(Object person : (JSONArray) family.get("people")) {
			people.add(new JPerson((JSONObject) person));
		}
}

public JPanel toCards() {
	final JPanel card = new JPanel();
	card.setLayout(new BoxLayout(card, BoxLayout.PAGE_AXIS));
	card.add(new FamilyHeader(name).panel);
	JPanel peopleCards = new JPanel(new WrapLayout(FlowLayout.LEFT));
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
}
