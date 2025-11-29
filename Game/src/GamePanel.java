import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePanel extends JPanel {
	private GameFrame gameFrame;
	private GroundPanel groundPanel;
	private JTextField inputField = new JTextField(20);
	private InputPanel inputPanel = new InputPanel();
	private PausePanel pausePanel = new PausePanel();
	private GameOverPanel gameOverPanel = null;
	private ScorePanel scorePanel = null;
	private ComboPanel comboPanel = null;
	private ExplainPanel exPanel = null;
	private StatusPanel statusPanel = null;
	private Vector<Asteroid> asteroids = new Vector<Asteroid>();
	private SetAsteroid set = null;
	private Font font = new Font("Galmuri9", Font.BOLD, 20);

	public GamePanel(GameFrame gameFrame, ScorePanel scorePanel, 
			ComboPanel comboPanel, StatusPanel statusPanel, TextStore tStore) 
	{
		this.gameFrame = gameFrame;
		this.setLayout(new BorderLayout());
		this.scorePanel = scorePanel;
		this.comboPanel = comboPanel;
		this.statusPanel = statusPanel;
		
		inputField.setFont(font);
		
		exPanel = new ExplainPanel();
		gameOverPanel = new GameOverPanel();
		groundPanel = new GroundPanel();
		
		add(groundPanel, BorderLayout.CENTER);
		add(inputPanel, BorderLayout.SOUTH);
		groundPanel.add(pausePanel, BorderLayout.CENTER);
		
		gameOverPanel.setBounds(0, 0, 700, 800);
		pausePanel.setBounds(0, 0, 700, 800);
		
		set = new SetAsteroid(groundPanel, statusPanel, tStore, asteroids);
	}

	// setAsteroid startGame() 호출
	public void startGame() {
		exPanel.setVisible(false);
		set.startGame();
		gameOverPanel.threadStart();
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
	
	public void resetGame() {
		gameFrame.getStartBtn().setVisible(true);
		gameFrame.getStopBtn().setVisible(false);
		gameFrame.getStartBtn().setText("시작");
		statusPanel.resetHp(); // hp 초기화
		scorePanel.resetScore(); // score 초기화
		comboPanel.resetCombo(); // combo 초기화
		inputField.setText("");
		inputField.setEnabled(true);
		set.resetGame();
	}
	
	class GroundPanel extends JPanel {
		private ImageIcon bgIcon = new ImageIcon("image/background.jpg");
		private Image bgImg = bgIcon.getImage();

		public GroundPanel() {
			this.setLayout(null);
			add(exPanel);
			add(gameOverPanel);
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
							gameOverPanel.scoreLabel.setText(Integer.toString(scorePanel.getScore()));

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
			this.setLayout(null);
			this.setOpaque(false);
			this.setVisible(false);
			setSize(700,800);
			
			label = new JLabel("GAME PAUSED", JLabel.CENTER);
			label.setFont(new Font("Galmuri9", Font.BOLD, 40));
			label.setForeground(Color.WHITE);
			label.setSize(300,200);
			label.setLocation(200,200);
			add(label);
		}

		@Override
		public void paintComponent(Graphics g) {
			g.setColor(new Color(0, 0, 0, 100));
			g.fillRect(0, 0, getWidth(), getHeight());

			super.paintComponent(g);
		}

		// wait 스레드 시작
		public void waitThreadStart() {
			gameFrame.getStartBtn().setEnabled(false);
	        gameFrame.getStopBtn().setEnabled(false);
			new WaitThread().start();
		}

		// 2초 후 실행되는 코드
		public void waitAndStart() {
			gameFrame.getStopBtn().setVisible(true);
			gameFrame.getStartBtn().setVisible(false);
			gameFrame.getStartBtn().setText("시작");
			
			set.resumeGame();
			pausePanel.setVisible(false);
			
			inputField.setEnabled(true); // 필드 활성화
			inputField.requestFocus(); // 포커스 필드에 다시 주기
			
			gameFrame.getStartBtn().setEnabled(true);
	        gameFrame.getStopBtn().setEnabled(true);
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

	class GameOverPanel extends JPanel {
		private JLabel label = null;
		private JLabel scoreLabel = null;
		private JButton homeBtn = null;

		public GameOverPanel() {
			this.setLayout(null);
			this.setOpaque(false);
			this.setVisible(false);
			setSize(700,800);
			setLocation(0,0);
			
			label = new JLabel("GAME OVER", JLabel.CENTER);
			label.setFont(new Font("Galmuri9", Font.BOLD, 40));
			label.setForeground(Color.WHITE);
			label.setSize(300,200);
			label.setLocation(200,100);
			
			scoreLabel = new JLabel("0",JLabel.CENTER);
			scoreLabel.setFont(new Font("Galmuri9", Font.BOLD, 40));
			scoreLabel.setForeground(Color.ORANGE);
			scoreLabel.setSize(300,200);
			scoreLabel.setLocation(200,200);
			
			homeBtn = new JButton("나가기");
			homeBtn.setFont(new Font("Galmuri9", Font.BOLD, 40));
			homeBtn.setForeground(Color.WHITE);
			homeBtn.setBackground(Color.DARK_GRAY);
			homeBtn.setSize(200,100);
			homeBtn.setLocation(250,400);
			
			add(label);
			add(scoreLabel);
			add(homeBtn);
			
			homeBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					exPanel.setVisible(true);
					gameFrame.resetStartFlag();
					gameFrame.resetOverFlag();
					resetGame();
				}
			});
		}
		public void threadStart() {
			GameOverThread th = new GameOverThread();
			th.start();
		}
		public void gameOver() {
			set.stopGame();
			gameFrame.setOverFlag();
			setVisible(true);
			inputField.setEnabled(false);
		}

		@Override
		public void paintComponent(Graphics g) {
			g.setColor(new Color(0, 0, 0, 100));
			g.fillRect(0, 0, getWidth(), getHeight());

			super.paintComponent(g);
		}
		
		class GameOverThread extends Thread{
			@Override
			public void run() {
				while(true) {
					try {
						sleep(100); // 추가하니까 해결
						if (statusPanel.isGameOver()) {
							gameOver();
							break;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}
