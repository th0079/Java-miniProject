import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusPanel extends JPanel {
	private int hp = 100;
	private JLabel hpLabel = new JLabel(Integer.toString(hp), JLabel.CENTER);
	private boolean gameOver = false;

	private ImageIcon bgIcon = new ImageIcon("image/hpBg.png");
	private Image bgImg = bgIcon.getImage();

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
	}

	public StatusPanel() {
		this.setLayout(new BorderLayout());
		
		hpLabel.setFont(new Font("Galmuri9", Font.BOLD, 50));
		hpLabel.setForeground(new Color(255, 30, 30));
		add(hpLabel, BorderLayout.CENTER);

	}

	public void damaged(int damage) { // 요격 실패 시 호출
		hp -= damage; // damage만큼 hp 감소
		if (hp <= 0) { 
			gameOver = true; // gameOver 플레그 활성화
			hp = 0; // 0아래로 안내려가게
		}
		hpLabel.setText(Integer.toString(hp)); // 라밸 갱신
	}

	public boolean isGameOver() {
		return gameOver;
	}
	
	public void resetHp() { // 게임 초기화 시 호출
		hp =100; // 지구 체력 100으로 초기화
		gameOver =false; // gameOver 플래그 비활성화
		hpLabel.setText(Integer.toString(hp)); // 라밸 갱신
		System.out.println("hp reset");
	}
}
