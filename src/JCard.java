import javax.swing.*;

public class JCard {
public JPanel card;
private JTextArea name;
private JTextArea giving;
private JTextArea receiving;

public JCard(Person person) {
	name.setText(person.name);
	giving.setText(person.givingTo);
	receiving.setText(person.receivingFrom);
}

private void createUIComponents() {
	card = new Card(5);
}

}
