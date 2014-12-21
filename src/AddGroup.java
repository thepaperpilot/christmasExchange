import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddGroup {
public JPanel panel;
private JTextField groupName;
private JComboBox type;
private JButton submit;

public AddGroup() {
	submit.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			switch (type.getSelectedIndex()) {
				default:
					// invalid group #
					break;
				case 0:
					ChristmasExchange.addGroup(new FamilyGroup(groupName.getText()));
					break;
			}
		}
	});
}

private void createUIComponents() {
	groupName = new JTextField();
	groupName.setBorder(BorderFactory.createEmptyBorder());
}
}
