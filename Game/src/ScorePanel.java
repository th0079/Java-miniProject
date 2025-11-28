import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel{
	private int score =0;
	private JLabel scoreLabel = new JLabel(Integer.toString(score),JLabel.CENTER);
	private ImageIcon bgIcon = new ImageIcon("image/scoreBg.png");
	private Image bgImg = bgIcon.getImage();
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bgImg,0,0,getWidth(),getHeight(),this);
	}
	
	public ScorePanel() {
		this.setLayout(new BorderLayout());
	
		scoreLabel.setFont(new Font("NanumBarunGothic",Font.BOLD, 50));
		scoreLabel.setForeground(Color.ORANGE);
		add(scoreLabel, BorderLayout.CENTER);
		
	}
	
	public void increse(int amount) {
		score += amount;
		scoreLabel.setText(Integer.toString(score));
	}
	
	public int getScore() {
		return score;
	}
}
