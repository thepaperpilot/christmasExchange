import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Tab {
public JPanel panel;
private JPanel people;
private JTextField groupName;
private JButton delete;
private JPanel rules;
private JPanel cards;
private JButton rulesButton;
private JButton settingsButton;
private JButton peopleButton;
private JButton rename;
private JButton GENERATEButton;
private JPanel settings;

public Tab(final Group group) {
	people.add(group.peopleCards());
	rules.add(group.ruleCards());
	panel.validate();
	groupName.setText(group.getName());
	peopleButton.setSelected(true);
	peopleButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			((CardLayout) cards.getLayout()).show(cards, "people");
			peopleButton.setSelected(true);
			rulesButton.setSelected(false);
			settingsButton.setSelected(false);
		}
	});
	rulesButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			((CardLayout) cards.getLayout()).show(cards, "rules");
			peopleButton.setSelected(false);
			rulesButton.setSelected(true);
			settingsButton.setSelected(false);
		}
	});
	settingsButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			((CardLayout) cards.getLayout()).show(cards, "settings");
			peopleButton.setSelected(false);
			rulesButton.setSelected(false);
			settingsButton.setSelected(true);
		}
	});
	delete.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			new ConfirmDialog() {
				@Override
				public void onOK() {
					ChristmasExchange.removeGroup();
				}
			}.create();
		}
	});
	rename.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			ChristmasExchange.renameGroup(groupName.getText());
		}
	});


	GENERATEButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			ChristmasExchange.generate();
		}
	});
}

private void createUIComponents() {
	people = new JPanel(new GridLayout(0, 1));
	rules = new JPanel(new GridLayout(0, 1));
	settings = new JPanel(new GridLayout(0, 1));

	groupName = new JTextField();
	groupName.setBorder(BorderFactory.createEmptyBorder());
}
}
