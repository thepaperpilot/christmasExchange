package com.thepaperpilot.giftexchange.desktop.ui;

import com.thepaperpilot.giftexchange.core.Group;
import com.thepaperpilot.giftexchange.desktop.JGroup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class GiftExchange {

private static final GiftExchange instance = new GiftExchange();

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

	Group.groups = read();

	populateTabs();
}

private static ArrayList<Group> read() {
	ArrayList<Group> groups = new ArrayList<>();
	try {
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(new FileReader("data.json"));
		for(Object group : (JSONArray) object.get("groups")) {
			groups.add(new JGroup((JSONObject) group));
		}
	} catch(IOException | NullPointerException | ParseException e) {
		e.printStackTrace();
	}
	return groups;
}

private static void populateTabs() {
	groupTabs = new ArrayList<>();
	instance.tabs.removeAll();
	for(int i = 0; i < Group.groups.size(); i++) {
		Group group = Group.groups.get(i);
		Tab tab = new Tab((JGroup) group);
		instance.tabs.add(tab.panel);
		instance.tabs.setTitleAt(i, group.getName());
		groupTabs.add(tab);
	}

	instance.tabs.add(new AddGroup().panel);
	instance.tabs.setTitleAt(instance.tabs.getTabCount() - 1, "+");
}

public static void generate() {
	GiftExchange.instance.error.setText("");
	if(Group.groups.size() <= 0) {
		instance.error("Error: Invalid group selected");
	} else {
		getGroup().randomize();
	}
}

void error(String error) {
	instance.error.append("\n" + error);
}

static Group getGroup() {
	return Group.groups.get(instance.tabs.getSelectedIndex());
}

public static void removeGroup() {
	removeGroup(getGroup());
}

private static void removeGroup(Group group) {
	Group.groups.remove(group);
	Group.write();
	instance.tabs.remove(instance.tabs.getSelectedIndex());
}

public static void addGroup(Group group) {
	Group.groups.add(group);
	Group.write();
	Tab tab = new Tab((JGroup) group);
	instance.tabs.add(tab.panel, instance.tabs.getTabCount() - 1);
	instance.tabs.setTitleAt(instance.tabs.getTabCount() - 2, group.getName());
	groupTabs.add(tab);
}

public static void renameGroup(String text) {
	renameGroup(getGroup(), text);
}

private static void renameGroup(Group group, String name) {
	group.setName(name);
	Group.write();
	instance.tabs.setTitleAt(instance.tabs.getSelectedIndex(), name);
}

}
