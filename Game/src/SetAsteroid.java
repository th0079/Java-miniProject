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

	public void startGame() { // 게임 시작 메소드
		stopFlag = false; // stopFlag 비활성화
		reset = false; // reset 비활성화
		
		asteroidSpawner = new AsteroidSpawner(); // 소행성 spawner 스레드
		asteroidSpawner.start(); // 소행성 생성 시작
	}

	public void stopGame() { // 게임 일시 정지 메소드
		stopFlag = true; // stopFlag 활성화
		asteroidSpawner.interrupt(); // spawner 스레드 종료
	}

	public void resetGame() { // 게임 초기화 메소드
		System.out.println("소행성 개수 : " + asteroids.size());
		for (int i=0; i<asteroids.size(); i++) { 
			Asteroid a = asteroids.get(i);
			a.interrupt(); // 실행 중인 asteroid 스레드 종료
			groundPanel.remove(a.getLabel());
			groundPanel.remove(a.getImageLabel());
		}
		asteroidSpawner.interrupt(); // spawner 스레드 종료
		groundPanel.repaint(); // 다시 그리기
		asteroids.clear(); // 벡터 초기화
		System.out.println("소행성 초기화 : " + asteroids.size());
		reset = true; // reset 상태
	}

	public void resumeGame() { // 게임 재개 메소드 
		stopFlag = false; // stopFlag 비활성화
		
		asteroidSpawner = new AsteroidSpawner(); // 소행성 spawner 스레드
		asteroidSpawner.start(); // 소행성 생성 시작
	}

	public void spawnAsteroid(int level) { // 생성되는 소행성 결정하는 메소드
		int x = (int) (Math.random() * (groundPanel.getWidth() - 170)) + 40; // 무작위 x값 얻기

		String word = tStore.getShortWord(); // 6자리 이하 단어 
		JLabel text = new JLabel(word, JLabel.CENTER); // 중앙 정렬
		text.setFont(font);
		Asteroid asteroid = null;
		JLabel imageLabel = null;
		double rand = Math.random(); // 0<rand<1 까지 랜덤값 생성

		switch (level) { // 레벨에 따른 생성 확률 변화
		case 1: // 레벨1
			if (rand < 0.9) { // 90% 
				imageLabel = new JLabel(blueAsteroidImage);
				imageLabel.setSize(80, 80);
				asteroid = new BlueAsteroid(groundPanel, x, text, imageLabel);
			} else { // 10%
				imageLabel = new JLabel(redAsteroidImage);
				imageLabel.setSize(80, 80);
				asteroid = new RedAsteroid(groundPanel, x, text, imageLabel);
			}
			break;

		case 2: // 레벨2
			if (rand < 0.7) { // 70%
				imageLabel = new JLabel(blueAsteroidImage);
				imageLabel.setSize(80, 80);
				asteroid = new BlueAsteroid(groundPanel, x, text, imageLabel);
			} else { // 30%
				imageLabel = new JLabel(redAsteroidImage);
				imageLabel.setSize(80, 80);
				asteroid = new RedAsteroid(groundPanel, x, text, imageLabel);
			}
			break;

		case 3: // 레벨3
			if (rand < 0.6) { // 60%
				imageLabel = new JLabel(blueAsteroidImage);
				imageLabel.setSize(80, 80);
				asteroid = new BlueAsteroid(groundPanel, x, text, imageLabel);
			} else if (rand < 0.9) { // 30%
				imageLabel = new JLabel(redAsteroidImage);
				imageLabel.setSize(80, 80);
				asteroid = new RedAsteroid(groundPanel, x, text, imageLabel);
			} else { // 10%
				word = tStore.getLongWord(); // 회색 소행성의 경우 7자 이상 단어
				text = new JLabel(word, JLabel.CENTER);
				text.setFont(font);
				imageLabel = new JLabel(grayAsteroidImage);
				imageLabel.setSize(160, 160);
				asteroid = new GrayAsteroid(groundPanel, x, text, imageLabel);
			}
			break;
		}

		text.setSize(200, 30);
		
//		text.setLocation(x,-100);
//		imageLabel.setLocation(x, -100);
		
		groundPanel.add(text);
		groundPanel.add(imageLabel);

		asteroids.add(asteroid); // 벡터에 추가 
		asteroid.start(); // 스레드 시작
	}

	class AsteroidSpawner extends Thread { // 소행성 생성 스레드
		@Override
		public void run() {
			try {
				Thread.sleep(1000); // 시작 or 재개할 때 딜레이 1초
				while (true) {
					if (reset) // reset 상태면 종료
						return;
					spawnAsteroid(1); // level에 맞게 소환
					Thread.sleep(2000); // level에 따라 값 변경 (예정)
				}
			} catch (InterruptedException e) {
				return;
			}
		}
	}

	public void destroyAsteroid(Asteroid a) {
	    if (reset || !asteroids.contains(a)) return;

	    groundPanel.remove(a.getLabel());
	    groundPanel.remove(a.getImageLabel());
	    
	    asteroids.remove(a);

	    statusPanel.damaged(a.damage);
	    System.out.println("지구에게 " + a.damage + "데미지");
	    
	    groundPanel.repaint();
	}

//	private void checkY() {
//		for (int i = asteroids.size() - 1; i >= 0; i--) { // 뒤에서 부터 검사
//			Asteroid a = asteroids.get(i);
//			JLabel label = a.getLabel();
//			int limitY = groundPanel.getHeight(); // 한계값 설정
//
//			if (label.getY() >= limitY - 40) { // 한계값 근처 도달 시
//				groundPanel.remove(label); // 텍스트 제거
//				groundPanel.remove(a.getImageLabel()); // 이미지 제거
//				asteroids.remove(i); // 벡터에서 제거
//
//				statusPanel.damaged(a.damage); // 소행성 데미지만큼 피해입음 
//				System.out.println("지구에게 " + a.damage + "데미지");
//			}
//		}
//	}
}
