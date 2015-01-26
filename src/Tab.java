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

    public Tab(final Group group) {
        people.add(group.peopleCards());
        rules.add(group.ruleCards());
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

        groupName = new JTextField();
        groupName.setBorder(BorderFactory.createEmptyBorder());
    }
}
