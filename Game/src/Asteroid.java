import java.awt.Color;

import javax.swing.JLabel;

public class Asteroid extends Thread {
	private double x = -100;
	private double y = -100;
	protected int score;
	protected int damage;
	protected int speed;
	protected JLabel imageLabel;
	private GamePanel.GroundPanel panel;
	private JLabel text;
	private SetAsteroid set = null;
	public Asteroid(GamePanel.GroundPanel panel, double x, JLabel text) {
		this.panel = panel;
		this.x = x;
		this.text = text;
		this.text.setForeground(Color.white);
		set = panel.getSetAsteroid();
	}
	public String getText() {
		return text.getText();
	}
	public JLabel getLabel() {
		return text;
	}
	public JLabel getImageLabel() {
		return imageLabel;
	}
	public int getScore() {
		return score;
	}
	public void fall() {
		y+=speed;
		
		imageLabel.setLocation((int)x, (int)y);
		int textX = (int)x + (imageLabel.getWidth() - text.getWidth()) / 2;
		int textY = (int)y + (imageLabel.getHeight() - text.getHeight()) / 2;
		text.setLocation(textX, textY);
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				set.checkFlag();
				fall();
				panel.repaint();
				
				if (y>panel.getHeight()) {
					break;
				}
				
				Thread.sleep(100);
			}
		}
		catch(InterruptedException e) {
			return;
		}
	}
	
}
// 푸른 소행성 
class BlueAsteroid extends Asteroid{
	public BlueAsteroid(GamePanel.GroundPanel panel, double x, JLabel text, JLabel imageLabel) {
		super(panel, x, text);
		this.score = 10;
		this.speed = 5;
		this.damage = 10;
		this.imageLabel = imageLabel;
	}
}

// 붉은 소행성
class RedAsteroid extends Asteroid{
	public RedAsteroid(GamePanel.GroundPanel panel, double x, JLabel text, JLabel imageLabel) {
		super(panel, x, text);
		this.score = 30;
		this.speed = 10;
		this.damage = 20;
		this.imageLabel = imageLabel;
	}
}

// 회색 소행성
class GrayAsteroid extends Asteroid{
	public GrayAsteroid(GamePanel.GroundPanel panel, double x, JLabel text, JLabel imageLabel) {
		super(panel, x, text);
		this.score = 50;
		this.speed = 2;
		this.damage = 30;
		this.imageLabel = imageLabel;
	}
}


