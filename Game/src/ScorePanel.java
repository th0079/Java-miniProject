import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel{
	private int score =0;
	private JLabel scoreLabel = new JLabel(Integer.toString(score),JLabel.CENTER);
	private JLabel text = new JLabel("점수",JLabel.CENTER);

	public ScorePanel() {
		this.setLayout(new BorderLayout());
		
		text.setFont(new Font("NanumBarunGothic",Font.BOLD, 20));
		scoreLabel.setFont(new Font("NanumBarunGothic",Font.BOLD, 40));
		
		this.setBackground(Color.yellow);
		add(text, BorderLayout.NORTH);
		add(scoreLabel, BorderLayout.CENTER);
	}
	
	public void increse(int amount) {
		score += amount;
		scoreLabel.setText(Integer.toString(score));
	}
}
