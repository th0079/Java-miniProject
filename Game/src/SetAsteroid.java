import java.util.Vector;

import javax.swing.JLabel;

public class SetAsteroid {
	private GamePanel.GroundPanel groundPanel;
	private TextStore tStore;
	private Vector<Asteroid> asteroids;
	private Earth earth = new Earth();

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
		text.setSize(100, 20);
		text.setLocation(x, 0);

		groundPanel.add(text);

		Asteroid asteroid = null;
		double rand = Math.random();

		switch (level) {
		case 1: {
			if (rand < 0.9)
				asteroid = new BlueAsteroid(groundPanel, x, text);
			else
				asteroid = new RedAsteroid(groundPanel, x, text);
			break;
		}
		case 2: {
			if (rand < 0.7)
				asteroid = new BlueAsteroid(groundPanel, x, text);
			else
				asteroid = new RedAsteroid(groundPanel, x, text);
			break;
		}
		case 3: {
			if (rand < 0.6)
				asteroid = new BlueAsteroid(groundPanel, x, text);
			else if (rand < 0.3)
				asteroid = new RedAsteroid(groundPanel, x, text);
			else
				asteroid = new GrayAsteroid(groundPanel, x, text);
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
