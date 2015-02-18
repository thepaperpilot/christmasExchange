package com.thepaperpilot.giftexchange.desktop.ui;

import com.thepaperpilot.giftexchange.core.Rule;
import com.thepaperpilot.giftexchange.desktop.JRule;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RuleCard {
private final JRule parent;
public JPanel card;
private JButton addBlack;
private JButton addWhite;
private JButton addSource;
private JPanel sources;
private JPanel whites;
private JPanel blacks;
private JButton deleteButton;

public RuleCard(final JRule rule) {
	parent = rule;
	sources.add(rule.getCard(Rule.Tokens.SOURCE));
	blacks.add(rule.getCard(Rule.Tokens.BLACK));
	whites.add(rule.getCard(Rule.Tokens.WHITE));
	addSource.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			rule.add(Rule.Tokens.SOURCE);
			sources.remove(1);
			sources.add(rule.getCard(Rule.Tokens.SOURCE));
			sources.revalidate();
		}
	});
	addBlack.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			rule.add(Rule.Tokens.BLACK);
			blacks.remove(1);
			blacks.add(rule.getCard(Rule.Tokens.BLACK));
			blacks.revalidate();
		}
	});
	addWhite.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			rule.add(Rule.Tokens.WHITE);
			whites.remove(1);
			whites.add(rule.getCard(Rule.Tokens.WHITE));
			whites.revalidate();
		}
	});
	deleteButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			new ConfirmDialog() {
				@Override
				public void onOK() {
					GiftExchange.getGroup().removeRule(rule);
				}
			}.create();
		}
	});
}

public void update() {
	sources.remove(1);
	sources.add(parent.getCard(Rule.Tokens.SOURCE));
	sources.revalidate();
	blacks.remove(1);
	blacks.add(parent.getCard(Rule.Tokens.BLACK));
	blacks.revalidate();
	whites.remove(1);
	whites.add(parent.getCard(Rule.Tokens.WHITE));
	whites.revalidate();
}
}
