import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public abstract class AddToken extends JDialog {
private JPanel contentPane;
private JButton buttonOK;
private JButton buttonCancel;
private JTextField name;
private JCheckBox checkNames;
private JCheckBox checkGroups;
private JPanel preview;
private JCheckBox matchCase;
private JCheckBox useRegEx;
private JCheckBox invert;

public AddToken(String title) {
	setContentPane(contentPane);
	setModal(true);
	getRootPane().setDefaultButton(buttonOK);
	setTitle(title);

	buttonOK.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			onOK();
			dispose();
		}
	});

	buttonCancel.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			onCancel();
		}
	});

// call onCancel() when cross is clicked
	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			onCancel();
		}
	});

// call onCancel() on ESCAPE
	contentPane.registerKeyboardAction(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			onCancel();
		}
	}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

	checkNames.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			updatePreview();
		}
	});
	checkGroups.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			updatePreview();
		}
	});
	matchCase.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			updatePreview();
		}
	});
	useRegEx.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			updatePreview();
		}
	});
	invert.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			updatePreview();
		}
	});
	name.getDocument().addDocumentListener(new DocumentListener() {
		public void changedUpdate(DocumentEvent e) {
			updatePreview();
		}

		public void removeUpdate(DocumentEvent e) {
			updatePreview();
		}

		public void insertUpdate(DocumentEvent e) {
			updatePreview();
		}
	});
}

public abstract void onOK();

private void onCancel() {
	dispose();
}

private void createUIComponents() {
	name = new JTextField();
	name.setBorder(BorderFactory.createEmptyBorder());
	preview = new JPanel();
	preview.setLayout(new WrapLayout(FlowLayout.LEFT));
}

private void updatePreview() {
	preview.removeAll();
	Token token = new Token(name.getText(), checkNames.isSelected(), checkGroups.isSelected(), matchCase.isSelected(), useRegEx.isSelected(), invert.isSelected());
	for(Person person : ChristmasExchange.getGroup().getPeople()) {
		if(token.check(person))
			preview.add(person.getCard().card);
	}
	preview.revalidate();
	preview.updateUI();
}

public Token getToken() {
	return new Token(name.getText(), checkNames.isSelected(), checkGroups.isSelected(), matchCase.isSelected(), useRegEx.isSelected(), invert.isSelected());
}

public void create() {
	pack();
	setMinimumSize(new Dimension(600, 400));
	setVisible(true);
}
}
