import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ComboPanel extends JPanel {
	private static int combo = 0;
	private boolean burning = false; // 버닝모드
	private JLabel comboLabel = new JLabel("0", JLabel.CENTER);
	private ImageIcon bgIcon = new ImageIcon("image/comboBg.png");
	private Image bgImg = bgIcon.getImage();
	private SoundManager soundManager = new SoundManager();

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
	}

	public ComboPanel() {
		this.setLayout(new BorderLayout());
		comboLabel.setFont(new Font("Galmuri9", Font.BOLD, 50));
		comboLabel.setForeground(Color.ORANGE);
		add(comboLabel, BorderLayout.CENTER);
	}

	public void increaseCombo() {
		combo++;
		if (combo >= 5) {
			if (!burning)
				soundManager.playSFX("sound/burningOn.wav");
			burning = true;
			
			
		}
		updateCombo();
	}

	public void resetCombo() {
		combo = 0;
		burning = false;
		updateCombo();
		System.out.println("combo reset");
	}

	public boolean isBurning() {
		return burning;
	}

	private void updateCombo() { // 콤보 업데이트 
		comboLabel.setText(Integer.toString(combo)); // 텍스트 초기화

		if (isBurning()) { // 버닝일때 텍스트 색 변경
			comboLabel.setForeground(new Color(255,87,0));
		} // 아니면 다시 원래 색으로 
		else comboLabel.setForeground(Color.ORANGE);
	}

	class ComboThread extends Thread {
		@Override
		public void run() {

		}
	}
}
