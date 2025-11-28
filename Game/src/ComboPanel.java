import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ComboPanel extends JPanel {
	private int combo = 0;
	private boolean burning = false;
	private JLabel comboLabel = new JLabel("0",JLabel.CENTER);
	private ImageIcon bgIcon = new ImageIcon("image/comboBg.png");
	private Image bgImg = bgIcon.getImage();
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bgImg,0,0,getWidth(),getHeight(),this);
	}
	
	public ComboPanel() {
		this.setLayout(new BorderLayout());
		comboLabel.setFont(new Font("Galmuri9",Font.BOLD, 50));
		comboLabel.setForeground(Color.ORANGE);
		add(comboLabel, BorderLayout.CENTER);
	}

	public void increaseCombo() {
		combo++;
		if (combo>=5) burning =true;
		updateCombo();
	}

	public void resetCombo() {
		combo = 0;
		burning = false;
		updateCombo();
	}

	public boolean isBurning() {
		return burning;
	}

	private void updateCombo() {
		comboLabel.setText(Integer.toString(combo));

		if (isBurning()) {
		}
	}
	
	class ComboThread extends Thread{
		@Override
		public void run() {
			
		}
	}
}
