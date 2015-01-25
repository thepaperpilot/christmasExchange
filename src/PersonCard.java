import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PersonCard {
public JTextArea giving;
public JTextArea receiving;
public JPanel card;
private JTextArea name;
private JButton lockGive;
private JButton lockReceive;
private JTextArea group;
private JTextField personName;
private JButton delete;
private JButton save;
private JButton toPerson;
private JButton toSettings;
private JTextField groupName;
private JPanel people;
private JPanel settings;
private JCheckBox participating;

// TODO add button to add a person (or family or w/e)

public PersonCard(final Person person) {
	name.setText(person.name);
	personName.setText(person.name);
	giving.setText(person.givingTo);
	receiving.setText(person.receivingFrom);
	group.setText(person.group.equals("") ? "" : "(" + String.valueOf(person.group) + ")");
	groupName.setText(person.group);
	people.setBackground(person.participating ? Color.black : Color.red);
	settings.setBackground(person.participating ? Color.black : Color.red);
	participating.setSelected(person.participating);
	final ImageIcon lock = new ImageIcon("lock.png");
	final ImageIcon unlock = new ImageIcon("unlock.png");
	lockGive.setIcon(person.lockGive ? lock : unlock);
	lockReceive.setIcon(person.lockReceive ? lock : unlock);

	lockGive.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			person.lockGive = !person.lockGive;
			Person partner = ChristmasExchange.getGroup().find(person.givingTo);
			lockGive.setIcon(person.lockGive ? lock : unlock);
			if(partner != null) {
				partner.lockReceive = person.lockGive;
				partner.getCard().lockReceive.setIcon(partner.lockReceive ? lock : unlock);
			}
			Parser.write();
		}
	});

	lockReceive.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			person.lockReceive = !person.lockReceive;
			Person partner = ChristmasExchange.getGroup().find(person.receivingFrom);
			lockReceive.setIcon(person.lockReceive ? lock : unlock);
			if(partner != null) {
				partner.lockGive = person.lockReceive;
				partner.getCard().lockGive.setIcon(partner.lockGive ? lock : unlock);
			}
			Parser.write();
		}
	});
	toPerson.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			((CardLayout) card.getLayout()).show(card, "person");
		}
	});
	toSettings.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			((CardLayout) card.getLayout()).show(card, "settings");
		}
	});
	save.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			person.name = personName.getText();
			person.participating = participating.isSelected();
			person.group = groupName.getText();
			Parser.write();
			name.setText(person.name);
			people.setBackground(person.participating ? Color.black : Color.red);
			settings.setBackground(person.participating ? Color.black : Color.red);
			group.setText(person.group.equals("") ? "" : "(" + String.valueOf(person.group) + ")");
		}
	});
	delete.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			// TODO implement deleting people
			Parser.write();
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
