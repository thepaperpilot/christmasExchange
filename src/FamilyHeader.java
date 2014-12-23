import javax.swing.*;

class FamilyHeader {
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
