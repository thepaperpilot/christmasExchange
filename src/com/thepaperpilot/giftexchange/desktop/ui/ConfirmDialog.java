package com.thepaperpilot.giftexchange.desktop.ui;

import javax.swing.*;
import java.awt.event.*;

abstract class ConfirmDialog extends JDialog {
private JPanel contentPane;
private JButton buttonOK;
private JButton buttonCancel;

public ConfirmDialog() {
	setContentPane(contentPane);
	setModal(true);
	getRootPane().setDefaultButton(buttonCancel);

	buttonOK.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			onOK();
			dispose();
		}
	});

	buttonCancel.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			onCancel();
		}
	});

// call onCancel() when cross is clicked
	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			onCancel();
		}
	});

// call onCancel() on ESCAPE
	contentPane.registerKeyboardAction(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			onCancel();
		}
	}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
}

public abstract void onOK();

private void onCancel() {
// add your code here if necessary
	dispose();
}

public void create() {
	pack();
	setVisible(true);
}

}
