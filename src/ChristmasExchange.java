import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class ChristmasExchange {

public static final ArrayList<Group> groups = Parser.read();
private static final ChristmasExchange instance = new ChristmasExchange();
private static ArrayList<Tab> groupTabs = new ArrayList<>();
private JTextArea error;
private JPanel panel;
private JTabbedPane tabs;

public static void main(String[] args) {
	JFrame frame = new JFrame("Christmas Exchange");
	frame.setContentPane(instance.panel);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.pack();
	frame.setMinimumSize(new Dimension(400, 300));
	frame.setSize(800, 600);
	frame.setVisible(true);

	populateTabs();
}

private static void populateTabs() {
	groupTabs = new ArrayList<>();
	instance.tabs.removeAll();
	for(int i = 0; i < groups.size(); i++) {
		Group group = groups.get(i);
		Tab tab = new Tab(group);
		instance.tabs.add(tab.panel);
		instance.tabs.setTitleAt(i, group.getName());
		groupTabs.add(tab);
	}

	instance.tabs.add(new AddGroup().panel);
	instance.tabs.setTitleAt(instance.tabs.getTabCount() - 1, "+");
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
	Tab tab = new Tab(group);
	instance.tabs.add(tab.panel, instance.tabs.getTabCount() - 1);
	instance.tabs.setTitleAt(instance.tabs.getTabCount() - 2, group.getName());
	groupTabs.add(tab);
}

static void removeGroup() {
	groups.remove(getGroup());
	Parser.write();
	instance.tabs.remove(instance.tabs.getSelectedIndex());
}

static void renameGroup(String name) {
	getGroup().setName(name);
	Parser.write();
	instance.tabs.setTitleAt(instance.tabs.getSelectedIndex(), name);
}

public static void generate() {
	ChristmasExchange.instance.error.setText("");
	if(groups.size() <= 0) {
		error("Error: Invalid group selected");
	} else {
		getGroup().randomize();
	}
}
}
