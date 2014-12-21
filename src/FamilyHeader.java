import javax.swing.*;

public class FamilyHeader {
private final String familyName;
private JTextArea name;
public JPanel panel;

public FamilyHeader(String name) {
	familyName = name;
}

private void createUIComponents() {
	name = new JTextArea();
	name.setText(familyName);
}
}
