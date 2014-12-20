import javax.swing.*;
import java.awt.*;

public class Tab {
private JScrollPane scrollpane;
private JPanel cards;
public JPanel panel;

final Group group;

public Tab(Group group) {
	this.group = group;
	cards.add(group.toCards());
	panel.validate();
}

private void createUIComponents() {
	cards = new JPanel(new GridLayout(0, 1));
}

public void updateCards() {
	final int scroll = scrollpane.getVerticalScrollBar().getValue();
	cards.removeAll();
	cards.add(group.toCards());
	SwingUtilities.invokeLater(new Runnable() {
		public void run() {
			scrollpane.getVerticalScrollBar().setValue(scroll);
		}
	});
	panel.validate();
}
}
