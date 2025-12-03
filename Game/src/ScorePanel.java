import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel{
	private int score=0;
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
		score = 0; // 0으로 초기화
		scoreLabel.setFont(new Font("Galmuri9",Font.BOLD, 50));
		scoreLabel.setForeground(Color.ORANGE);
		add(scoreLabel, BorderLayout.CENTER); // 중앙 배치
		
	}
	
	public void increse(int amount) {
		score += amount; 
		scoreLabel.setText(Integer.toString(score)); // 점수 오르면 텍스트 업데이트
	}
	
	public int getScore() { 
		return score; // 스코어 반환
	}
	public void resetScore() { // 스코어 초기화
		score = 0; 
		scoreLabel.setText(Integer.toString(score));
		System.out.println("score reset");
	}
}
