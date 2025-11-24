import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePanel extends JPanel {
	private GroundPanel groundPanel;
	private JTextField inputField = new JTextField(20);
	private InputPanel inputPanel = new InputPanel();
	private ScorePanel scorePanel = null;
	private ComboPanel comboPanel = null;
	private TextStore tStore = null;
	private Vector<Asteroid> asteroids = new Vector<Asteroid>();

	public GamePanel(ScorePanel scorePanel, ComboPanel comboPanel, TextStore tStore) {
		this.setLayout(new BorderLayout());
		this.scorePanel = scorePanel;
		this.comboPanel = comboPanel;
		this.tStore = tStore;
		groundPanel = new GroundPanel();
		add(groundPanel, BorderLayout.CENTER);
		add(inputPanel, BorderLayout.SOUTH);

		startGame();
	}

	public void startGame() {
		new Thread(new Runnable() {
	        @Override
	        public void run() {
	            while (true) {
	                try {
	                    spawnAsteroid(1); // 레벨 1 난이도로 생성
	                    Thread.sleep(2000); // 2초마다 생성 (조절 가능)
	                } catch (InterruptedException e) {
	                    return;
	                }
	            }
	        }
	    }).start();
	}

	class GroundPanel extends JPanel {
		public GroundPanel() {
			this.setBackground(Color.white);
			this.setLayout(null);
		}
	}

	class InputPanel extends JPanel {
		public InputPanel() {
			this.setBackground(Color.gray);
			add(inputField);
			
			inputField.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JTextField input = (JTextField) e.getSource();
					String userText = input.getText();

					for (int i = 0; i < asteroids.size(); i++) {
						Asteroid a = asteroids.get(i);

						if (userText.equals(a.getText())) {
							// 1. 점수 증가
							comboPanel.increaseCombo();
							
							int score = a.getScore();
							if (comboPanel.isBurning()) {
								score *=2;
							}
							scorePanel.increse(score); // Asteroid에 점수 정보 있다고 가정

							// 2. 화면에서 라벨 제거
							groundPanel.remove(a.getLabel());
							groundPanel.repaint(); // 화면 갱신

							// 3. 스레드 중지 및 벡터에서 제거
							
							asteroids.remove(i);

							// 4. 입력창 비우기
							inputField.setText("");
							break; // 하나 맞췄으니 루프 종료
						}
						else {
							comboPanel.resetCombo();
							inputField.setText("");
						}

					}
				}
			});
		}
	}

	// 소행성 생성 메소드
	public void spawnAsteroid(int level) {
		int x = (int) (Math.random() * (this.getWidth()) - 100) + 100;

		String word = tStore.get();
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
}
