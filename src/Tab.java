import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tab {
private JScrollPane scrollpanePeople;
private JPanel people;
public JPanel panel;
private JTextField groupName;
private JButton delete;
private JPanel rules;
private JPanel cards;
private JButton rulesButton;
private JButton settingsButton;
private JButton peopleButton;
private JScrollPane scrollpaneRules;
private JButton rename;

final Group group;

public Tab(final Group group) {
	this.group = group;
	people.add(group.toCards());
	panel.validate();
	groupName.setText(group.getName());
	peopleButton.setBackground(Color.lightGray);
	peopleButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			((CardLayout) cards.getLayout()).show(cards, "people");
			peopleButton.setBackground(Color.lightGray);
			rulesButton.setBackground(Color.white);
			settingsButton.setBackground(Color.white);
		}
	});
	rulesButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			((CardLayout) cards.getLayout()).show(cards, "rules");
			peopleButton.setBackground(Color.white);
			rulesButton.setBackground(Color.lightGray);
			settingsButton.setBackground(Color.white);
		}
	});
	settingsButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			((CardLayout) cards.getLayout()).show(cards, "settings");
			peopleButton.setBackground(Color.white);
			rulesButton.setBackground(Color.white);
			settingsButton.setBackground(Color.lightGray);
		}
	});
	delete.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			ChristmasExchange.removeGroup();
		}
	});
	rename.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			ChristmasExchange.renameGroup(groupName.getText());
		}
	});
}

private void createUIComponents() {
	people = new JPanel(new GridLayout(0, 1));
	rules = new JPanel(new GridLayout(0, 1));

	groupName = new JTextField();
	groupName.setBorder(BorderFactory.createEmptyBorder());
}

public void updateCards() {
	final int scroll = scrollpanePeople.getVerticalScrollBar().getValue();
	people.removeAll();
	people.add(group.toCards());
	SwingUtilities.invokeLater(new Runnable() {
		public void run() {
			scrollpanePeople.getVerticalScrollBar().setValue(scroll);
		}
	});

	final int scroll1 = scrollpaneRules.getVerticalScrollBar().getValue();
	rules.removeAll();
	//rules.add(rules.toCards());
	SwingUtilities.invokeLater(new Runnable() {
		public void run() {
			scrollpaneRules.getVerticalScrollBar().setValue(scroll1);
		}
	});

	panel.validate();
}
}
