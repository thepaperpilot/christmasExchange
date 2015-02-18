package com.thepaperpilot.giftexchange.desktop;

import com.thepaperpilot.giftexchange.core.Person;
import com.thepaperpilot.giftexchange.desktop.ui.PersonCard;
import org.json.simple.JSONObject;

public class JPerson extends Person {

public JPerson(JSONObject person) {
	super(person);
}

public PersonCard getCard() {
	return new PersonCard(this);
}
}
