import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChristmasExchange {

private JPanel panel;

private JButton GENERATEButton;
public JTextArea error;
private JPanel cards;

public static final ArrayList<Group> groups = Parser.read();
public static int group = 0;

public static final ChristmasExchange instance = new ChristmasExchange();

public static void main(String[] args) {
    JFrame frame = new JFrame("Christmas Exchange");
    frame.setContentPane(instance.panel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setSize(400, 600);
    frame.setVisible(true);
}

private ChristmasExchange() {
    GENERATEButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            error.setText("");
            if(groups.size() > group) {
                cards.removeAll();
                groups.get(group).clear();
                groups.get(group).randomize();
                cards.add(groups.get(group).toCards());
            } else {
                error("Error: Invalid group selected");
            }
            panel.validate();
        }
    });
}

static void error(String error) {
    instance.error.append("\n" + error);
}

private void createUIComponents() {
    cards = new JPanel(new GridLayout(0, 1));
}
}
