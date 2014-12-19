import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PersonCard {
public JPanel card;
private JTextArea name;
private JTextArea giving;
private JTextArea receiving;
private JButton lockGive;
private JButton lockReceive;

// TODO add button to "disable" entire person (opt-out)
// TODO add button to add a person (or family or w/e)
// TODO add menu to edit or delete person

public PersonCard(final Person person) {
	name.setText(person.name);
	giving.setText(person.givingTo);
	receiving.setText(person.receivingFrom);
	ImageIcon lock = new ImageIcon("lock.png");
	ImageIcon unlock = new ImageIcon("unlock.png");
	lockGive.setIcon(person.lockGive ? lock : unlock);
	lockReceive.setIcon(person.lockReceive ? lock : unlock);

	lockGive.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			person.lockGive = !person.lockGive;
			Person partner = ChristmasExchange.getGroup().find(person.givingTo);
			partner.lockReceive = person.lockGive;
			Parser.write();
			ChristmasExchange.updateCards();
		}
	});

	lockReceive.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			person.lockReceive = !person.lockReceive;
			Person partner = ChristmasExchange.getGroup().find(person.receivingFrom);
			partner.lockGive = person.lockReceive;
			Parser.write();
			ChristmasExchange.updateCards();
		}
	});
}

private void createUIComponents() {
	card = new Shadow();
}

}
