import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

class PersonCard {
private JTextArea name;
private JTextArea giving;
private JTextArea receiving;
private JButton lockGive;
private JButton lockReceive;
private JTextArea group;
private JTextField personName;
private JButton delete;
private JButton rename;
public JPanel cards;
private JButton toPerson;
private JButton toSettings;
private JSpinner numGroup;
private JPanel people;
private JPanel settings;
private JCheckBox participating;

// TODO add button to add a person (or family or w/e)

public PersonCard(final Person person) {
	name.setText(person.name);
	personName.setText(person.name);
	giving.setText(person.givingTo);
	receiving.setText(person.receivingFrom);
	group.setText(person.group == 0 ? "No Group Selected" : "(Group " + String.valueOf(person.group) + ")");
	people.setBackground(person.participating ? Color.black : Color.red);
	settings.setBackground(person.participating ? Color.black : Color.red);
	participating.setSelected(person.participating);
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
	toPerson.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			((CardLayout) cards.getLayout()).show(cards, "person");
		}
	});
	toSettings.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			((CardLayout) cards.getLayout()).show(cards, "settings");
		}
	});
	numGroup.addChangeListener(new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent changeEvent) {
			person.group = (int) numGroup.getValue();
			Parser.write();
			group.setText(person.group == 0 ? "No Group Selected" : "(Group " + String.valueOf(person.group) + ")");
		}
	});
	rename.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			person.name = personName.getText();
			Parser.write();
			name.setText(person.name);
		}
	});
	delete.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			// TODO implement this
			Parser.write();
		}
	});
	participating.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			person.participating = participating.isSelected();
			Parser.write();
			people.setBackground(person.participating ? Color.black : Color.red);
			settings.setBackground(person.participating ? Color.black : Color.red);

		}
	});
}


private void createUIComponents() {
	people = new Shadow();
	settings = new Shadow();
	personName = new JTextField();
	personName.setBorder(BorderFactory.createEmptyBorder());
}

}
