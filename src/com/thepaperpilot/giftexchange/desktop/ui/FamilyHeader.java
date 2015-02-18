package com.thepaperpilot.giftexchange.desktop.ui;

import javax.swing.*;
import java.awt.*;

public class FamilyHeader {
private final String familyName;
public JPanel panel;
private JTextArea name;

public FamilyHeader(String name) {
	familyName = name;
}

private void createUIComponents() {
	name = new JTextArea();
	name.setText(familyName);
}

}
