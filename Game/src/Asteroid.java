import javax.swing.JLabel;

public abstract class Asteroid extends Thread {
	protected double x;
	protected double y = -50;
	protected int score;
	protected int damage;
	protected int speed;
	protected GamePanel.GroundPanel panel;
	protected JLabel text;
	protected boolean isDead =false;
	
	public Asteroid(GamePanel.GroundPanel panel, double x, JLabel text) {
		this.panel = panel;
		this.x = x;
		this.text = text;
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
	public void setDead() {
		isDead = true;
	}
	public void fall() {
		y+=speed;
		text.setLocation((int)x, (int)y);
	}
	
	@Override
	public void run() {
		try {
			while(!isDead) {
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
	public BlueAsteroid(GamePanel.GroundPanel panel, double x, JLabel text) {
		super(panel, x, text);
		this.score = 10;
		this.speed = 10;
		this.damage = 10;
	}
}

// 붉은 소행성
class RedAsteroid extends Asteroid{
	public RedAsteroid(GamePanel.GroundPanel panel, double x, JLabel text) {
		super(panel, x, text);
		this.score = 30;
		this.speed = 20;
		this.damage = 20;
	}
}

// 회색 소행성
class GrayAsteroid extends Asteroid{
	public GrayAsteroid(GamePanel.GroundPanel panel, double x, JLabel text) {
		super(panel, x, text);
		this.score = 50;
		this.speed = 5;
		this.damage = 30;
	}
}


