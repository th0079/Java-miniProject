import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Asteroid extends Thread {
	private double x; // 생성 시 x 위치
	private double y; // 생성 시 y 위치
	protected int score; // 파괴 시 점수
	protected int damage; // 받는 데미지
	protected int speed; // 소행성 속도
	private boolean isEnd = false;
	protected JLabel imageLabel; // 소행성 이미지
	private ImageIcon explosionImg = new ImageIcon("image/explosion.png"); // 지구에 닿았을 때
	private ImageIcon explosionImg2 = new ImageIcon("image/explosion2.png"); // 요격했을 때
	private GamePanel.GroundPanel panel;
	private JLabel text;
	private SetAsteroid set = null;
	private SoundManager soundManager = new SoundManager();
	private ComboPanel comboPanel = null;
	
	public Asteroid(GamePanel.GroundPanel panel, double x, JLabel text) {
		this.panel = panel;
		this.x = x;
		this.y = -100;
		this.text = text;
		this.text.setForeground(Color.white);
		set = panel.getSetAsteroid();
		comboPanel = set.getComboPanel();
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
	
	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}
	public void fall() { // 소행성 추락
		y += speed;

		imageLabel.setLocation((int) x, (int) y);
		int textX = (int) x + (imageLabel.getWidth() - text.getWidth()) / 2;
		int textY = (int) y + imageLabel.getHeight()-5;
		text.setLocation(textX, textY);
	}

	@Override
	public void run() { 
		try {
			while (true) {
				if (set.checkReset()) break; // 게임이 리셋 되었으면 종료
				if (set.getStopFlag()) { // 게임 일시정지 시 대기
					Thread.sleep(100);
	                continue;
	            }
				if (isEnd) {
					soundManager.playSFX("sound/boom2.wav");
					int prevWidth = imageLabel.getWidth();
					imageLabel.setIcon(explosionImg2);
					
					int size = 200;
					imageLabel.setSize(size, size);
					
					int newX = (size-prevWidth)/2;
					imageLabel.setLocation((int)x - newX, (int)y);
					
					panel.remove(text);
					imageLabel.repaint();
					
					Thread.sleep(100);
					panel.remove(imageLabel);
					
					break;
				}

				if (y > panel.getHeight()-40) { // panel 끝에 도달하면 종료
					soundManager.playSFX("sound/boom.wav");
					comboPanel.resetCombo();
					
					set.destroyAsteroid(this);
					int prevWidth = imageLabel.getWidth();
					imageLabel.setIcon(explosionImg);
					int size = 200;
					imageLabel.setSize(size, size);
					
					int newX = (size-prevWidth)/2;
					imageLabel.setLocation((int)x - newX, panel.getHeight()-100);
					text.setVisible(false);
					imageLabel.repaint();
					
					Thread.sleep(1000);
					panel.remove(imageLabel);
					break;
				}
				
				fall(); // 떨어짐
				panel.repaint(); // 다시 그려주기 
				Thread.sleep(100); // 0.1초마다 갱신
			}
		} catch (InterruptedException e) {
			return;
		}
	}

}

// 푸른 소행성 
class BlueAsteroid extends Asteroid {
	public BlueAsteroid(GamePanel.GroundPanel panel, double x, JLabel text, JLabel imageLabel) {
		super(panel, x, text);
		this.score = 10;
		this.speed = 5;
		this.damage = 10;
		this.imageLabel = imageLabel;
	}
}

// 붉은 소행성
class RedAsteroid extends Asteroid {
	public RedAsteroid(GamePanel.GroundPanel panel, double x, JLabel text, JLabel imageLabel) {
		super(panel, x, text);
		this.score = 30;
		this.speed = 10;
		this.damage = 20;
		this.imageLabel = imageLabel;
	}
}

// 회색 소행성
class GrayAsteroid extends Asteroid {
	public GrayAsteroid(GamePanel.GroundPanel panel, double x, JLabel text, JLabel imageLabel) {
		super(panel, x, text);
		this.score = 50;
		this.speed = 2;
		this.damage = 30;
		this.imageLabel = imageLabel;
	}
}
