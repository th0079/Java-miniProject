import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerPanel extends JPanel { // 플레이어 id 나오는 패널
	private ImageIcon bgIcon = new ImageIcon("image/astronaut.png");
	private Image bgImg = bgIcon.getImage();
	private JLabel userId = null;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
	}

	public PlayerPanel(String id) {
		setLayout(new BorderLayout());
		setSize(275, 200);
		String idLine = "[" + id + "]";
		userId = new JLabel(idLine, JLabel.CENTER); // 중앙 정렬
		userId.setSize(100, 100);
		userId.setFont(new Font("Galmuri9", Font.BOLD, 30));
		userId.setForeground(Color.WHITE);

		add(userId, BorderLayout.SOUTH); // id label 남쪽 배치
	}
}
