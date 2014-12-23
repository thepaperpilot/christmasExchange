import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class AddPerson {
private JPanel cards;
private JPanel settings;
private JTextField personName;
private JButton create;
private JSpinner numGroup;
private JButton cancel;
private JLabel add;

public AddPerson() {
	create.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			// TODO How to deal with different group types having different structures? (as in, I need to send a value for family here, and people's families are currently uneditable)
			// ChristmasExchange.getGroup().addPerson()
		}
	});
	add.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			((CardLayout) cards.getLayout()).show(cards, "settings");
		}
	});
	cancel.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			((CardLayout) cards.getLayout()).show(cards, "add");
		}
	});
}

private void createUIComponents() {
	settings = new Shadow();
	personName = new JTextField();
	personName.setBorder(BorderFactory.createEmptyBorder());
}
}
