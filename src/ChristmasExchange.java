import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChristmasExchange {

public static final ArrayList<Group> groups = Parser.read();
private static final ChristmasExchange instance = new ChristmasExchange();
private static int group = 0;
private JTextArea error;
private JPanel panel;
private JButton GENERATEButton;
private JPanel cards;
private JScrollPane scrollpane;

private ChristmasExchange() {
	GENERATEButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			error.setText("");
			if (groups.size() > group) {
				cards.removeAll();
				groups.get(group).clear();
				groups.get(group).randomize();
				cards.add(groups.get(group).toCards());
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						scrollpane.getVerticalScrollBar().setValue(0);
					}
				});
			} else {
				error("Error: Invalid group selected");
			}
			panel.validate();
		}
	});
}

public static void main(String[] args) {
	JFrame frame = new JFrame("Christmas Exchange");
	frame.setContentPane(instance.panel);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.pack();
	frame.setSize(400, 600);
	frame.setVisible(true);

	instance.cards.add(groups.get(group).toCards());
	instance.panel.validate();
}

static void error(String error) {
	instance.error.append("\n" + error);
}

private void createUIComponents() {
	cards = new JPanel(new GridLayout(0, 1));
}
}
