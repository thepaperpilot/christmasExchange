package com.thepaperpilot.giftexchange.desktop.ui;

import com.thepaperpilot.giftexchange.core.Group;
import com.thepaperpilot.giftexchange.core.Rule;
import com.thepaperpilot.giftexchange.desktop.JGroup;
import com.thepaperpilot.giftexchange.desktop.JRule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Tab {
public JPanel panel;
private JPanel people;
private JTextField groupName;
private JButton delete;
private JPanel rules;
private JPanel cards;
private JButton rulesButton;
private JButton settingsButton;
private JButton peopleButton;
private JButton rename;
private JButton GENERATEButton;
private JPanel settings;
private JButton ruleButton;
private JScrollPane peoplePane;
private JScrollPane settingsPane;
private JScrollPane rulesPane;

public Tab(final JGroup group) {
	people.add(group.peopleCards());
	rules.add(group.ruleCards());
	panel.validate();
	groupName.setText(group.getName());
	peoplePane.getVerticalScrollBar().setUnitIncrement(16);
	settingsPane.getVerticalScrollBar().setUnitIncrement(16);
	rulesPane.getVerticalScrollBar().setUnitIncrement(16);
	peopleButton.setSelected(true);
	peopleButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			((CardLayout) cards.getLayout()).show(cards, "people");
			peopleButton.setSelected(true);
			rulesButton.setSelected(false);
			settingsButton.setSelected(false);
		}
	});
	rulesButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			((CardLayout) cards.getLayout()).show(cards, "rules");
			peopleButton.setSelected(false);
			rulesButton.setSelected(true);
			settingsButton.setSelected(false);
		}
	});
	ruleButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			JRule rule = new JRule();
			group.addRule(rule);
			Group.write();
		}
	});
	settingsButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			((CardLayout) cards.getLayout()).show(cards, "settings");
			peopleButton.setSelected(false);
			rulesButton.setSelected(false);
			settingsButton.setSelected(true);
		}
	});
	delete.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			new ConfirmDialog() {
				@Override
				public void onOK() {
					GiftExchange.removeGroup();
				}
			}.create();
		}
	});
	rename.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			GiftExchange.renameGroup(groupName.getText());
		}
	});

	GENERATEButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			GiftExchange.generate();
		}
	});
}

private void createUIComponents() {
	people = new JPanel(new GridLayout(0, 1));
	rules = new JPanel(new GridLayout(0, 1));
	settings = new JPanel(new GridLayout(0, 1));
}
}
