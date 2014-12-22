import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChristmasExchange {

private static final ChristmasExchange instance = new ChristmasExchange();
public static final ArrayList<Group> groups = Parser.read();
public static ArrayList<Tab> groupTabs = new ArrayList<>();
private JTextArea error;
private JPanel panel;
private JButton GENERATEButton;
private JTabbedPane tabs;

private ChristmasExchange() {
	GENERATEButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			error.setText("");
			if (groups.size() <= 0) {
				error("Error: Invalid group selected");
			} else {
				groups.get(tabs.getSelectedIndex()).clear();
				groups.get(tabs.getSelectedIndex()).randomize();
				updateCards();
			}
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

	populateTabs();
}

private static void populateTabs() {
	groupTabs = new ArrayList<>();
	instance.tabs.removeAll();
	for (int i = 0; i < groups.size(); i++) {
		Group group = groups.get(i);
		Tab tab = new Tab(group);
		instance.tabs.add(tab.panel);
		instance.tabs.setTitleAt(i, group.getName());
		groupTabs.add(tab);
	}

	instance.tabs.add(new AddGroup().panel);
	instance.tabs.setTitleAt(instance.tabs.getTabCount() - 1, "+");

	updateCards();
}

public static void updateCards() {
	for(Tab tab : groupTabs) {
		tab.updateCards();
	}
}

static Group getGroup() {
	return groups.get(instance.tabs.getSelectedIndex());
}

static void error(String error) {
	instance.error.append("\n" + error);
}

static void addGroup(Group group) {
	groups.add(group);
	Parser.write();
	populateTabs();
}

static void removeGroup() {
	groups.remove(getGroup());
	Parser.write();
	populateTabs();
}

static void renameGroup(String name) {
	getGroup().setName(name);
	Parser.write();
	populateTabs();
}
}
