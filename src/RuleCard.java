import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class RuleCard {
public JPanel card;
private JButton addBlack;
private JButton addWhite;
private JButton addSource;
private JPanel sources;
private JPanel whites;
private JPanel blacks;

public RuleCard(final Rule rule) {
	sources.add(rule.getSourcesCard());
	blacks.add(rule.getBlacksCard());
	whites.add(rule.getWhitesCard());
	addSource.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			new AddToken("Adding to Source") {
				@Override
				public void onOK() {
					rule.addSource(getToken());
					whites.remove(1);
					sources.add(rule.getSourcesCard());
					sources.revalidate();
				}
			}.create();
		}
	});
	addWhite.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			new AddToken("Adding to Whitelist") {
				@Override
				public void onOK() {
					rule.addWhite(getToken());
					whites.remove(1);
					whites.add(rule.getWhitesCard());
					whites.revalidate();
				}
			}.create();
		}
	});
	addBlack.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			new AddToken("Adding to Blacklist") {
				@Override
				public void onOK() {
					rule.addBlack(getToken());
					whites.remove(1);
					blacks.add(rule.getBlacksCard());
					blacks.revalidate();
				}
			}.create();
		}
	});
}

private void createUIComponents() {
	card = new Shadow();
}

}
