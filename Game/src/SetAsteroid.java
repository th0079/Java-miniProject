import java.awt.Font;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class SetAsteroid {
	private GamePanel.GroundPanel groundPanel = null;
	private StatusPanel statusPanel = null;
	private TextStore tStore;
	private Vector<Asteroid> asteroids;
	private ImageIcon blueAsteroidImage = new ImageIcon("image/blueAsteroid.png");
	private ImageIcon redAsteroidImage = new ImageIcon("image/RedAsteroid.png");
	private ImageIcon grayAsteroidImage = new ImageIcon("image/GrayAsteroid.png");
	private Font font = new Font("Galmuri9", Font.BOLD, 20);
	private boolean stopFlag = false;
	private boolean reset = false;
	private AsteroidSpawner asteroidSpawner = null; 
	private AsteroidDestroyer asteroidDestroyer = null;
	
	public SetAsteroid(GamePanel.GroundPanel groundPanel, StatusPanel statusPanel, TextStore tStore,
			Vector<Asteroid> asteroids) {
		this.groundPanel = groundPanel;
		this.tStore = tStore;
		this.asteroids = asteroids;
		this.statusPanel = statusPanel;
	}

	public boolean checkReset() {
		return reset;
	}
	public boolean getStopFlag() {
		return stopFlag;
	}

	public void startGame() {
		stopFlag = false;
		reset = false;
		
		asteroidSpawner = new AsteroidSpawner();
		asteroidSpawner.start();
		asteroidDestroyer = new AsteroidDestroyer();
		asteroidDestroyer.start();
	}

	public void stopGame() {
		stopFlag = true;
		asteroidSpawner.interrupt();
		asteroidDestroyer.interrupt();
	}

	public void resetGame() {
		System.out.println("소행성 개수 : " + asteroids.size());
		for (int i=0; i<asteroids.size(); i++) {
			Asteroid a = asteroids.get(i);
			a.interrupt();
			groundPanel.remove(a.getLabel());
			groundPanel.remove(a.getImageLabel());
		}
		asteroidSpawner.interrupt();
		asteroidDestroyer.interrupt();
		groundPanel.repaint();
		asteroids.clear();
		System.out.println("소행성 초기화 : " + asteroids.size());
		reset = true;
	}

	public void resumeGame() {
		stopFlag = false;
		
		asteroidSpawner = new AsteroidSpawner();
		asteroidSpawner.start();
		asteroidDestroyer = new AsteroidDestroyer();
		asteroidDestroyer.start();
	}

	public void spawnAsteroid(int level) {
		int x = (int) (Math.random() * (groundPanel.getWidth() - 170)) + 40;

		String word = tStore.getShortWord();
		JLabel text = new JLabel(word, JLabel.CENTER);
		text.setFont(font);
		Asteroid asteroid = null;
		JLabel imageLabel = null;
		double rand = Math.random();

		switch (level) {
		case 1: // 레벨1
			if (rand < 0.9) {
				imageLabel = new JLabel(blueAsteroidImage);
				imageLabel.setSize(80, 80);
				asteroid = new BlueAsteroid(groundPanel, x, text, imageLabel);
			} else {
				imageLabel = new JLabel(redAsteroidImage);
				imageLabel.setSize(80, 80);
				asteroid = new RedAsteroid(groundPanel, x, text, imageLabel);
			}
			break;

		case 2: // 레벨2
			if (rand < 0.7) {
				imageLabel = new JLabel(blueAsteroidImage);
				imageLabel.setSize(80, 80);
				asteroid = new BlueAsteroid(groundPanel, x, text, imageLabel);
			} else {
				imageLabel = new JLabel(redAsteroidImage);
				imageLabel.setSize(80, 80);
				asteroid = new RedAsteroid(groundPanel, x, text, imageLabel);
			}
			break;

		case 3: // 레벨3
			if (rand < 0.6) {
				imageLabel = new JLabel(blueAsteroidImage);
				imageLabel.setSize(80, 80);
				asteroid = new BlueAsteroid(groundPanel, x, text, imageLabel);
			} else if (rand < 0.9) {
				imageLabel = new JLabel(redAsteroidImage);
				imageLabel.setSize(80, 80);
				asteroid = new RedAsteroid(groundPanel, x, text, imageLabel);
			} else {
				word = tStore.getLongWord();
				text = new JLabel(word, JLabel.CENTER);
				text.setFont(font);
				imageLabel = new JLabel(grayAsteroidImage);
				imageLabel.setSize(160, 160);
				asteroid = new GrayAsteroid(groundPanel, x, text, imageLabel);
			}
			break;
		}

		text.setSize(200, 30);
		
		text.setLocation(x,-100);
		imageLabel.setLocation(x, -100);
		
		groundPanel.add(text);
		groundPanel.add(imageLabel);

		asteroids.add(asteroid);
		asteroid.start();
	}

	class AsteroidSpawner extends Thread {
		@Override
		public void run() {
			try {
				Thread.sleep(1000); // 시작 or 재개할 때 딜레이
				while (true) {
					if (reset)
						return;
					spawnAsteroid(3);
					Thread.sleep(2000); // 2초마다 소환
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
					if (reset)
						return;
					checkY();
					groundPanel.repaint();
					Thread.sleep(100);
				}
			} catch (InterruptedException e) {
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
				groundPanel.remove(a.getImageLabel());
				asteroids.remove(i);

				statusPanel.damaged(a.damage);
				System.out.println("지구에게 " + a.damage + "데미지");
			}
		}
	}
}
