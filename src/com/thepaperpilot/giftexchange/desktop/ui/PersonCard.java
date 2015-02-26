package com.thepaperpilot.giftexchange.desktop.ui;

import com.thepaperpilot.giftexchange.core.Group;
import com.thepaperpilot.giftexchange.core.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonCard {
public JPanel card;
private JTextArea name;
private JButton lock;
private JTextArea group;
private JTextField personName;
private JButton delete;
private JButton save;
private JButton toPerson;
private JButton toSettings;
private JTextField groupName;

public PersonCard(final Person person) {
	name.setText(person.name);
	personName.setText(person.name);
	group.setText(person.group.equals("") ? "" : "(" + String.valueOf(person.group) + ")");
	groupName.setText(person.group);
	final ImageIcon lock = new ImageIcon("assets/lock.png");
	final ImageIcon unlock = new ImageIcon("assets/unlock.png");
	this.lock.setIcon(person.lock ? lock : unlock);

	this.lock.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			person.lock = !person.lock;
			PersonCard.this.lock.setIcon(person.lock ? lock : unlock);
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
			person.group = groupName.getText();
			name.setText(person.name);
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
