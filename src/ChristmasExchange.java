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

public static final ChristmasExchange instance = new ChristmasExchange();

public static void main(String[] args) {
    JFrame frame = new JFrame("Christmas Exchange");
    frame.setContentPane(instance.panel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
}

private ChristmasExchange() {
    GENERATEButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cards.removeAll();
            error.setText("");
            ArrayList<ArrayList<Person>> groups = Generator.generate();
            for (ArrayList<Person> group : groups) {
                for (Person person : group) {
                    cards.add(new JCard(person).card);
                }
                //TODO add divider of some sort?
            }
            panel.validate();
        }
    });
}

private void createUIComponents() {
    cards = new JPanel(new GridLayout(0, 1));
}
}
