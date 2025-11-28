import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePanel extends JPanel {
	private GroundPanel groundPanel;
	private JTextField inputField = new JTextField(20);
	private InputPanel inputPanel = new InputPanel();
	private PausePanel pausePanel = new PausePanel();
	private ScorePanel scorePanel = null;
	private ComboPanel comboPanel = null;
	private ExplainPanel exPanel = null;
	private Vector<Asteroid> asteroids = new Vector<Asteroid>();
	private SetAsteroid set = null;
	private Font font = new Font("Galmuri9", Font.BOLD, 20);

	public GamePanel(ScorePanel scorePanel, ComboPanel comboPanel, StatusPanel statusPanel, TextStore tStore) {
		this.setLayout(new BorderLayout());
		this.scorePanel = scorePanel;
		this.comboPanel = comboPanel;
		inputField.setFont(font);
		exPanel = new ExplainPanel();
		groundPanel = new GroundPanel();
		add(groundPanel, BorderLayout.CENTER);
		add(inputPanel, BorderLayout.SOUTH);
		groundPanel.add(pausePanel, BorderLayout.CENTER);
		pausePanel.setBounds(0, 0, 700, 800);

		set = new SetAsteroid(groundPanel, statusPanel, tStore, asteroids);
	}

	// setAsteroid startGame() 호출
	public void startGame() {
		exPanel.setVisible(false);
		set.startGame();
		inputField.requestFocus();
	}

	// setAsteroid stopGame() 호출
	public void stopGame() {
		set.stopGame();
		pausePanel.setVisible(true);
		inputField.setEnabled(false); // 비활성화
	}

	public void resumeGame() {
		pausePanel.waitThreadStart(); // 스레드 시작 메소드 호출
	}

	class GroundPanel extends JPanel {
		private ImageIcon bgIcon = new ImageIcon("image/background.jpg");
		private Image bgImg = bgIcon.getImage();

		public GroundPanel() {
			this.setLayout(null);
			add(exPanel);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
		}

		public SetAsteroid getSetAsteroid() {
			return set;
		}
	}

	class InputPanel extends JPanel {
		private ImageIcon bgIcon = new ImageIcon("image/inputBg.jpg");
		private Image bgImg = bgIcon.getImage();

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
		}

		public InputPanel() {
			this.setBackground(Color.GRAY);
			add(inputField);

			inputField.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JTextField input = (JTextField) e.getSource();
					String userText = input.getText();
					boolean correct = false;

					if (userText.equals("")) { // 빈문자열 입력 시 return
						return;
					}
					for (int i = 0; i < asteroids.size(); i++) {
						Asteroid a = asteroids.get(i);
						if (userText.equals(a.getText())) {
							correct = true;
							comboPanel.increaseCombo();

							int score = a.getScore();
							if (comboPanel.isBurning()) {
								score *= 2;
							}
							scorePanel.increse(score);

							groundPanel.remove(a.getLabel());
							groundPanel.remove(a.getImageLabel());
							groundPanel.repaint();

							asteroids.remove(i);
							break; // 하나 맞췄으니 루프 종료
						}
					}
					if (!correct) {
						comboPanel.resetCombo(); // 콤보 초기화
					}
					inputField.setText(""); // 입력창 비우기
				}
			});
		}
	}

	class PausePanel extends JPanel {
		JLabel label = null;

		public PausePanel() {
			this.setLayout(new BorderLayout());
			this.setOpaque(false);
			this.setVisible(false);

			label = new JLabel("GAME PAUSED", JLabel.CENTER);

			label.setFont(new Font("Galmuri9", Font.BOLD, 40));
			label.setForeground(Color.WHITE);
			add(label, BorderLayout.CENTER);
		}

		@Override
		public void paintComponent(Graphics g) {
			g.setColor(new Color(0, 0, 0, 100));
			g.fillRect(0, 0, getWidth(), getHeight());

			super.paintComponent(g);
		}

		// wait 스레드 시작
		public void waitThreadStart() {
			new WaitThread().start();
		}

		// 2초 후 실행되는 코드
		public void waitAndStart() {
			set.resumeGame();
			pausePanel.setVisible(false);
			inputField.setEnabled(true); // 필드 활성화
			inputField.requestFocus(); // 포커스 필드에 다시 주기
			label.setText("GAME PAUSED");
		}

		class WaitThread extends Thread {
			private int count = 2;

			@Override
			public void run() {
				while (true) {
					if (count == 0)
						break;
					try {
						label.setText(Integer.toString(count));
						sleep(1000);
						count--;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				waitAndStart();
			}
		}
	}
}
