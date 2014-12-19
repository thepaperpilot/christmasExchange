import javax.swing.*;

public class PersonCard {
public JPanel card;
private JTextArea name;
private JTextArea giving;
private JTextArea receiving;
private JButton lockGive;
private JButton lockReceive;

// TODO add button to "disable" entire person (opt-out)
// TODO add button to add a person (or family or w/e)
// TODO add menu to edit or delete person

public PersonCard(Person person) {
	name.setText(person.name);
	giving.setText(person.givingTo);
	receiving.setText(person.receivingFrom);
}

private void createUIComponents() {
	card = new Shadow(5);
}

}
