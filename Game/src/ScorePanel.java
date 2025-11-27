import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel{
	private int score =0;
	private JLabel scoreLabel = new JLabel(Integer.toString(score));

	public ScorePanel() {
		this.setBackground(Color.yellow);
		add(scoreLabel);
	}
	
	public void increse(int amount) {
		score +=amount;
		scoreLabel.setText(Integer.toString(score));
	}
}
