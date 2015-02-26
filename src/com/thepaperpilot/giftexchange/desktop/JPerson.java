package com.thepaperpilot.giftexchange.desktop;

import com.thepaperpilot.giftexchange.core.Family;
import com.thepaperpilot.giftexchange.core.Person;
import com.thepaperpilot.giftexchange.desktop.ui.PersonCard;
import org.json.simple.JSONObject;

public class JPerson extends Person {

private final PersonCard card;

public JPerson(JSONObject person, int parent) {
	super(person, parent);
	card = new PersonCard(this);
}

public JPerson(int parent) {
	super(parent);
	card = new PersonCard(this);
}

public PersonCard getCard() {
	return card;
}
}
