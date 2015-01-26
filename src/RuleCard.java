import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class RuleCard {
    private final Rule parent;
    public JPanel card;
    private JButton addBlack;
    private JButton addWhite;
    private JButton addSource;
    private JPanel sources;
    private JPanel whites;
    private JPanel blacks;
    private JButton deleteButton;

    public RuleCard(final Rule rule) {
        parent = rule;
        sources.add(rule.getSourcesCard());
        blacks.add(rule.getBlacksCard());
        whites.add(rule.getWhitesCard());
        addSource.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                rule.addSource();
                sources.remove(1);
                sources.add(rule.getSourcesCard());
                sources.revalidate();
            }
        });
        addWhite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                rule.addWhite();
                whites.remove(1);
                whites.add(rule.getWhitesCard());
                whites.revalidate();
            }
        });
        addBlack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                rule.addBlack();
                blacks.remove(1);
                blacks.add(rule.getBlacksCard());
                blacks.revalidate();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new ConfirmDialog() {
                    @Override
                    public void onOK() {
                        ChristmasExchange.getGroup().removeRule(rule);
                    }
                }.create();
            }
        });
    }

    public void update() {
        sources.remove(1);
        sources.add(parent.getSourcesCard());
        sources.revalidate();
        whites.remove(1);
        whites.add(parent.getWhitesCard());
        whites.revalidate();
        blacks.remove(1);
        blacks.add(parent.getBlacksCard());
        blacks.revalidate();
    }

    private void createUIComponents() {
        card = new Shadow();
    }

}
