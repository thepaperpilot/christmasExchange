package com.thepaperpilot.giftexchange.desktop.ui;

import com.thepaperpilot.giftexchange.desktop.JGroup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AddGroup {
public JPanel panel;
private JTextField groupName;
private JButton submit;

public AddGroup() {
	submit.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			GiftExchange.addGroup(new JGroup(groupName.getText()));
		}
	});
}

private void createUIComponents() {
	groupName = new JTextField();
	groupName.setBorder(BorderFactory.createEmptyBorder());
}

}
