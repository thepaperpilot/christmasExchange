package com.thepaperpilot.giftexchange.desktop.ui;

import com.thepaperpilot.giftexchange.core.Group;
import com.thepaperpilot.giftexchange.core.Person;
import com.thepaperpilot.giftexchange.desktop.JPerson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonCard {
public JTextArea giving;
public JTextArea receiving;
public JPanel card;
private JTextArea name;
private JButton lockGive;
private JButton lockReceive;
private JTextArea group;
private JTextField personName;
private JButton delete;
private JButton save;
private JButton toPerson;
private JButton toSettings;
private JTextField groupName;
private JPanel people;
private JPanel settings;
private JCheckBox participating;

// TODO add button to add a person (or family or w/e)

public PersonCard(final Person person) {
	name.setText(person.name);
	personName.setText(person.name);
	giving.setText(person.givingTo);
	receiving.setText(person.receivingFrom);
	group.setText(person.group.equals("") ? "" : "(" + String.valueOf(person.group) + ")");
	groupName.setText(person.group);
	card.setBackground(person.participating ? Color.LIGHT_GRAY : Color.red);
	participating.setSelected(person.participating);
	final ImageIcon lock = new ImageIcon("assets/lock.png");
	final ImageIcon unlock = new ImageIcon("assets/unlock.png");
	lockGive.setIcon(person.lockGive ? lock : unlock);
	lockReceive.setIcon(person.lockReceive ? lock : unlock);

	lockGive.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			person.lockGive = !person.lockGive;
			Person partner = GiftExchange.getGroup().find(person.givingTo);
			lockGive.setIcon(person.lockGive ? lock : unlock);
			if(partner != null) {
				partner.lockReceive = person.lockGive;
				((JPerson) partner).getCard().lockReceive.setIcon(partner.lockReceive ? lock : unlock);
			}
			Group.write();
		}
	});

	lockReceive.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			person.lockReceive = !person.lockReceive;
			Person partner = GiftExchange.getGroup().find(person.receivingFrom);
			lockReceive.setIcon(person.lockReceive ? lock : unlock);
			if(partner != null) {
				partner.lockGive = person.lockReceive;
				((JPerson) partner).getCard().lockGive.setIcon(partner.lockGive ? lock : unlock);
			}
			Group.write();
		}
	});
	toPerson.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			((CardLayout) card.getLayout()).show(card, "person");
		}
	});
	toSettings.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			((CardLayout) card.getLayout()).show(card, "settings");
		}
	});
	save.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			person.name = personName.getText();
			person.participating = participating.isSelected();
			person.group = groupName.getText();
			name.setText(person.name);
			card.setBackground(person.participating ? Color.LIGHT_GRAY : Color.red);
			group.setText(person.group.equals("") ? "" : "(" + String.valueOf(person.group) + ")");
			Group.write();
		}
	});
	delete.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			new ConfirmDialog() {
				@Override
				public void onOK() {
					person.remove();
				}
			}.create();
		}
	});
}
}
