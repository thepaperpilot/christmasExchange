package com.thepaperpilot.giftexchange.desktop.ui;

import com.thepaperpilot.giftexchange.desktop.JFamily;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FamilyHeader {
public JPanel panel;
private JTextArea name;
private JButton deleteButton;
private JButton addButton;

public FamilyHeader(final JFamily parent) {
    name.setText(parent.name);
    deleteButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		    new ConfirmDialog() {
			    @Override
			    public void onOK() {
				    parent.remove();
			    }
		    }.create();
	    }
    });
    addButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		    parent.addPerson();
	    }
    });
    }
}
