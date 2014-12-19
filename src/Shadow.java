import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

class Shadow extends JPanel {

private static final long serialVersionUID = 1L;

private final int pixels = 5;

public Shadow() {
	Border border = BorderFactory.createEmptyBorder(pixels, pixels, pixels, pixels);
	this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), border));
	this.setLayout(new BorderLayout());
}

@Override
protected void paintComponent(Graphics g) {
	int shade = 0;
	int topOpacity = 80;
	for (int i = 0; i < pixels; i++) {
		g.setColor(new Color(shade, shade, shade, ((topOpacity / pixels) * i)));
		g.drawRect(i, i, this.getWidth() - ((i * 2) + 1), this.getHeight() - ((i * 2) + 1));
	}
}
}
