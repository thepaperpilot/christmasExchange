import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChristmasExchange {

private JPanel panel;

private JButton GENERATEButton;
private JTextArea textArea1;
private JTextArea textArea2;
private JTextArea textArea3;
public JTextArea error;

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
            error.setText("");
            ArrayList<ArrayList<Person>> groups = Generator.generate();
            String names = "";
            String to = "";
            String from = "";
            for (ArrayList<Person> group : groups) {
                for (Person person : group) {
                    names += person.name + "\n";
                    to += person.givingTo + "\n";
                    from += person.receivingFrom + "\n";
                }
                names += "\n";
                to += "\n";
                from += "\n";
            }
            textArea1.setText(names.trim());
            textArea2.setText(to.trim());
            textArea3.setText(from.trim());
        }
    });
}
}
