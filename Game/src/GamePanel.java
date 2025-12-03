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
	private Vector<Asteroid> asteroids = new Vector<Asteroid>(); // 화면에 출력 중인 소행성 저장할 백터
	private SetAsteroid set = null;
	private Font font = new Font("Galmuri9", Font.BOLD, 20);

	public GamePanel(GameFrame gameFrame, ScorePanel scorePanel, ComboPanel comboPanel, StatusPanel statusPanel,
			TextStore tStore) {
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

	
	public void startGame() { // 게임 시작 메소드
		exPanel.setVisible(false); // 설명 화면 비활성화
		set.startGame(); // setAsteroid startGame() 호출
		gameOverPanel.threadStart(); // 게임오버 체크하는 스레드 실행
		inputField.requestFocus(); // 단어 입력 창에 포커스
	}

	 
	public void stopGame() { // 일시정지 메소드
		set.stopGame(); // setAsteroid stopGame() 호출
		pausePanel.setVisible(true);
		inputField.setEnabled(false); // 비활성화
	}

	public void resumeGame() { // 재개 메소드
		pausePanel.waitThreadStart(); // 대기 스레드 시작
	}

	public void resetGame() { // 게임 초기화 메소드 
		gameFrame.getStartBtn().setVisible(true); // 시작 버튼 활성화
		gameFrame.getStopBtn().setVisible(false); // 일시정지버튼 비활성화
		gameFrame.getRankBtn().setVisible(true); // 랭킹 버튼 활성화
		gameFrame.getStartBtn().setText("시작"); // 텍스트 초기화
		exPanel.setVisible(true); // 설명 패널 활성화
		pausePanel.setVisible(false); // 정지 패널 비활성화
		gameOverPanel.setVisible(false); // 게임오버 패널 비활성화
		statusPanel.resetHp(); // hp 초기화
		scorePanel.resetScore(); // score 초기화
		comboPanel.resetCombo(); // combo 초기화
		inputField.setText(""); // 입력창 초기화
		inputField.setEnabled(true); // 입력 활성화
		set.resetGame(); // setAsteroid의 resetGame 호출
	}

	class GroundPanel extends JPanel { // 게임이 진행되는 패널
		private ImageIcon bgIcon = new ImageIcon("image/background.jpg");
		private Image bgImg = bgIcon.getImage();

		public GroundPanel() {
			this.setLayout(null);
			add(exPanel); // 설명패널 추가
			add(gameOverPanel); // 게임오버 패널 추가
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
		}

		public SetAsteroid getSetAsteroid() {
			return set;
		}
	}

	class InputPanel extends JPanel { // 플레이어가 입력하는 입력창이 있는 패널
		private ImageIcon bgIcon = new ImageIcon("image/inputBg.jpg");
		private Image bgImg = bgIcon.getImage();

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
		}

		public InputPanel() {
			this.setBackground(Color.GRAY);
			add(inputField); // 입력창 추가

			inputField.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JTextField input = (JTextField) e.getSource();
					String userText = input.getText();
					boolean correct = false;

					if (userText.equals("")) { // 빈문자열 입력 시 return
						return;
					}
					for (int i = 0; i < asteroids.size(); i++) { // 생성된 소행성의 텍스트값과 비교하기
						Asteroid a = asteroids.get(i);
						if (userText.equals(a.getText())) { // 맞았을 경우
							correct = true;
							comboPanel.increaseCombo(); // 콤보 증가

							int score = a.getScore(); // 소행성 점수 가져오기
							if (comboPanel.isBurning()) { // 버닝모드일 경우 점수 2배
								score *= 2;
							}
							scorePanel.increse(score); // 점수 증가
							gameOverPanel.scoreLabel.setText(Integer.toString(scorePanel.getScore())); // 게임오버 패널 점수 변경

							groundPanel.remove(a.getLabel()); // 맞춘 소행성 텍스트 삭제
							groundPanel.remove(a.getImageLabel()); // 이미지 삭제
							groundPanel.repaint(); // 다시 그리기

							asteroids.remove(i); // 벡터에서 지우기
							break; // 하나 맞췄으니 루프 종료
						}
					}
					if (!correct) { // 틀렸을 때
						comboPanel.resetCombo(); // 콤보 초기화
					}
					inputField.setText(""); // 입력창 비우기
				}
			});
		}
	}

	class PausePanel extends JPanel { // 일시정지 시 활성화되는 패널
		JLabel label = null;

		public PausePanel() {
			this.setLayout(null);
			this.setOpaque(false); // 투명도 설정
			this.setVisible(false); // 숨기기
			setSize(700, 800);

			label = new JLabel("GAME PAUSED", JLabel.CENTER);
			label.setFont(new Font("Galmuri9", Font.BOLD, 40));
			label.setForeground(Color.WHITE);
			label.setSize(300, 200);
			label.setLocation(200, 200);
			add(label);
		}

		@Override
		public void paintComponent(Graphics g) {
			g.setColor(new Color(0, 0, 0, 100)); // 투명도 조절
			g.fillRect(0, 0, getWidth(), getHeight()); // 패널 채우기
 
			super.paintComponent(g);
		}

		// wait 스레드 시작
		public void waitThreadStart() {
			System.out.println("대기 시작");
			// 대기 중 모든 버튼 비활성화
			gameFrame.getStartBtn().setEnabled(false);
			gameFrame.getStopBtn().setEnabled(false);
			gameFrame.getExitBtn().setEnabled(false);
			gameFrame.getMenu().setEnabled(false);
			new WaitThread().start(); // 대기 스레드 시작
		}

		// 2초 후 실행되는 코드
		public void waitAndStart() {
			System.out.println("대기 종료");
			gameFrame.getStopBtn().setVisible(true);
			gameFrame.getStartBtn().setVisible(false);
			gameFrame.getStartBtn().setText("시작");

			set.resumeGame(); 
			pausePanel.setVisible(false); // 숨기기

			inputField.setEnabled(true); // 필드 활성화
			inputField.requestFocus(); // 포커스 필드에 다시 주기
			
			// 버튼 활성화
			gameFrame.getStartBtn().setEnabled(true);
			gameFrame.getStopBtn().setEnabled(true);
			gameFrame.getExitBtn().setEnabled(true);
			gameFrame.getMenu().setEnabled(true);
			label.setText("GAME PAUSED"); // label 텍스트 초기화
		}

		class WaitThread extends Thread { // 2초 대기 스레드
			private int count = 2;

			@Override
			public void run() {
				while (true) {
					if (count == 0) // 2초 후 종료
						break;
					try {
						label.setText(Integer.toString(count)); // 남은 시간 카운트
						sleep(1000); // 1초 대기
						count--;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				waitAndStart(); // 게임 시작
			}
		}
	}

	class GameOverPanel extends JPanel { // 게임오버 시 활성화되는 패널
		private JLabel label = null;
		private JLabel scoreLabel = null;
		private JButton homeBtn = null;

		public GameOverPanel() {
			this.setLayout(null); 
			this.setOpaque(false); // 투명도 설정
			this.setVisible(false); // 숨기기
			setSize(700, 800);
			setLocation(0, 0);

			label = new JLabel("GAME OVER", JLabel.CENTER);
			label.setFont(new Font("Galmuri9", Font.BOLD, 40));
			label.setForeground(Color.WHITE);
			label.setSize(300, 200);
			label.setLocation(200, 100);

			scoreLabel = new JLabel("0", JLabel.CENTER); // 점수 label
			scoreLabel.setFont(new Font("Galmuri9", Font.BOLD, 40));
			scoreLabel.setForeground(Color.ORANGE);
			scoreLabel.setSize(300, 200);
			scoreLabel.setLocation(200, 200);

			homeBtn = new JButton("나가기"); 
			homeBtn.setFont(new Font("Galmuri9", Font.BOLD, 40));
			homeBtn.setForeground(Color.WHITE);
			homeBtn.setBackground(Color.DARK_GRAY);
			homeBtn.setSize(200, 100);
			homeBtn.setLocation(250, 400);

			add(label);
			add(scoreLabel);
			add(homeBtn);

			homeBtn.addActionListener(new ActionListener() { // 나가기 버튼 눌렀을 때 
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false); // 게임오버패널 비활성화
					gameFrame.resetFlag(); // 플래그 초기화
					resetGame(); // 게임 초기화 메소드 호출
				}
			});
		}

		public void threadStart() { // 게임 오버 감시 스레드 시작
			GameOverThread th = new GameOverThread();
			th.start();
		}

		public void gameOver() { // 게임 오버 시 호출
			set.stopGame(); // 게임 정지
			gameFrame.setOverFlag(); // 게임 오버 플레그 활성화
			setVisible(true); // 게임 오버 패널 활성화
			inputField.setEnabled(false); // 게임 오버일 때 입력 방지
		}

		@Override
		public void paintComponent(Graphics g) {
			g.setColor(new Color(0, 0, 0, 100)); // 투명도 조절
			g.fillRect(0, 0, getWidth(), getHeight()); // 패널 채우기

			super.paintComponent(g);
		}

		class GameOverThread extends Thread {
			@Override
			public void run() {
				while (true) {
					try {
						sleep(100); // 게임오버 0.1초마다 체크 (추가하니까 해결) 
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
