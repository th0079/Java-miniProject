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

	public void damaged(int damage) {
		hp -= damage;
		if (hp <= 0) {
			gameOver = true;
			hp = 0;
		}
		hpLabel.setText(Integer.toString(hp));
	}

	public boolean isGameOver() {
		return gameOver;
	}
}
