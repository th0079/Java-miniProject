import java.awt.Color;
import java.awt.Image;

import javax.swing.JLabel;

public abstract class Asteroid extends Thread {
	private double x;
	private double y = -50;
	protected int score;
	protected int damage;
	protected int speed;
	private GamePanel.GroundPanel panel;
	private JLabel text;
	private Image image;
	
	public Asteroid(GamePanel.GroundPanel panel, double x, JLabel text, Image image) {
		this.panel = panel;
		this.x = x;
		this.text = text;
		this.image = image;
	}
	public String getText() {
		return text.getText();
	}
	public JLabel getLabel() {
		return text;
	}
	public int getScore() {
		return score;
	}
	public void fall() {
		y+=speed;
		text.setLocation((int)x, (int)y);
	}
	
	@Override
	public void run() {
		try {
			while(true) {
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
	public BlueAsteroid(GamePanel.GroundPanel panel, double x, JLabel text, Image image) {
		super(panel, x, text, image);
		text.setForeground(Color.BLUE);
		this.score = 10;
		this.speed = 10;
		this.damage = 10;
	}
}

// 붉은 소행성
class RedAsteroid extends Asteroid{
	public RedAsteroid(GamePanel.GroundPanel panel, double x, JLabel text, Image image) {
		super(panel, x, text, image);
		text.setForeground(Color.RED);
		this.score = 30;
		this.speed = 20;
		this.damage = 20;
	}
}

// 회색 소행성
class GrayAsteroid extends Asteroid{
	public GrayAsteroid(GamePanel.GroundPanel panel, double x, JLabel text, Image image) {
		super(panel, x, text, image);
		text.setForeground(Color.GRAY);
		this.score = 50;
		this.speed = 5;
		this.damage = 30;
	}
}


