import java.awt.Font;
import java.awt.Image;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class SetAsteroid {
	private GamePanel.GroundPanel groundPanel;
	private TextStore tStore;
	private Vector<Asteroid> asteroids;
	private Earth earth = new Earth();
	private Image blueAsteriodImage = new ImageIcon("image/blueAsteroid.png").getImage();
	private Image RedAsteriodImage = new ImageIcon("image/RedAsteroid.png").getImage();
	private Image GrayAsteriodImage = new ImageIcon("image/GrayAsteroid.png").getImage();
	
	public SetAsteroid(GamePanel.GroundPanel groundPanel, TextStore tStore, Vector<Asteroid> asteroids) {
		this.groundPanel = groundPanel;
		this.tStore = tStore;
		this.asteroids = asteroids;
	}
	
	public void startGame() {
        new AsteroidSpawner().start();
        new AsteroidDestroyer().start();
    }
	
	public void spawnAsteroid(int level) {
		int x = (int) (Math.random() * (groundPanel.getWidth() - 100)) + 50;

		String word = tStore.getWord();
		JLabel text = new JLabel(word);
		text.setFont(new Font("",Font.BOLD, 10));
		text.setSize(100,30);
		text.setLocation(x, 0);

		groundPanel.add(text);

		Asteroid asteroid = null;
		double rand = Math.random();

		switch (level) {
		case 1: {
			if (rand < 0.9)
				asteroid = new BlueAsteroid(groundPanel, x, text, blueAsteriodImage);
			else
				asteroid = new RedAsteroid(groundPanel, x, text, RedAsteriodImage);
			break;
		}
		case 2: {
			if (rand < 0.7)
				asteroid = new BlueAsteroid(groundPanel, x, text, blueAsteriodImage);
			else
				asteroid = new RedAsteroid(groundPanel, x, text, RedAsteriodImage);
			break;
		}
		case 3: {
			if (rand < 0.6)
				asteroid = new BlueAsteroid(groundPanel, x, text, blueAsteriodImage);
			else if (rand < 0.9)
				asteroid = new RedAsteroid(groundPanel, x, text, RedAsteriodImage);
			else
				asteroid = new GrayAsteroid(groundPanel, x, text, GrayAsteriodImage);
			break;
		}
		}
		asteroids.add(asteroid);
		asteroid.start();
	}

	class AsteroidSpawner extends Thread {
		@Override
		public void run() {
			try {
				while (true) {
					spawnAsteroid(3);
					Thread.sleep(2000);
				}
			} catch (InterruptedException e) {
				return;
			}
		}
	}

	class AsteroidDestroyer extends Thread {
		@Override
		public void run() {
			try {
				while (true) {
					checkY();
					groundPanel.repaint();
					Thread.sleep(100);
				}
			} catch (Exception e) {
				return;
			}
		}

	}

	private void checkY() {
		for (int i = asteroids.size() - 1; i >= 0; i--) {
			Asteroid a = asteroids.get(i);
			JLabel label = a.getLabel();
			int limitY = groundPanel.getHeight();
			
			if (label.getY() >= limitY - 40) {
				groundPanel.remove(label);
				asteroids.remove(i);
				// (체력 감소 코드 추가 위치
				earth.damaged(a.damage);
			}
		}
	}
}
