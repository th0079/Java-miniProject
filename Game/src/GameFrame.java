import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

public class GameFrame extends JFrame {
	private SoundManager soundManager = new SoundManager();
	private TextStore tStore = new TextStore();
	private JButton startBtn = new JButton("시작");
	private JButton stopBtn = new JButton("일시정지");
	private JButton rankBtn = new JButton("랭킹");
	private JButton exitBtn = new JButton("나가기");
	private JRadioButton[] rb = new JRadioButton[3];
	private String[] diffText = {"쉬움", "중간", "어려움"};
	private JLabel curState = new JLabel();
	private JLabel levLabel = new JLabel();
	private JMenu Menu = null;
	private JToolBar tBar = null;
	private ScorePanel scorePanel = new ScorePanel();
	private ComboPanel comboPanel = new ComboPanel();
	private StatusPanel statusPanel = new StatusPanel();
	private AddWordPanel addWordPanel = null;
	private SoundPanel soundPanel = null;
	private PlayerPanel playerPanel = null;
	private GamePanel gamePanel = null;
	private RankingPanel rankingPanel = null;
	private boolean stop = false; // 일시정지 시 활성화
	private boolean start = false; // 시작 시 활성화
	private boolean over = false; // 게임오버 시 활성화
	private boolean pauseMainBGM = false;
	private int difficulty =0;
	private int level = 1;
	private Font font = new Font("Galmuri9", Font.BOLD, 20);
	
	
	public GameFrame(String id) {
		super("지구를 지켜라!");
		gamePanel = new GamePanel(this, scorePanel, comboPanel, statusPanel, tStore);
		playerPanel = new PlayerPanel(id);
		rankingPanel = new RankingPanel(id, scorePanel);
		startBtn.setFont(font);
		stopBtn.setFont(font);
		exitBtn.setFont(font);
		curState.setFont(font);
		levLabel.setFont(font);
		setSize(1000, 800);
		setResizable(false); // 창 크기 변경금지
		setLocation(500, 100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		soundManager.playBGM("sound/background.wav");
		makeMenu();
		makeToolBar();
		makeSplitPane();
		setVisible(true);
	}

	private void makeMenu() {
		JMenuBar mBar = new JMenuBar();
		this.setJMenuBar(mBar);
		
		Menu = new JMenu("설정");
		Menu.setFont(font);
		Menu.setBackground(Color.WHITE);
		mBar.add(Menu);

		rankBtn.setFont(font);
		mBar.add(rankBtn);
		
		JMenuItem soundItem = new JMenuItem("소리설정");
		soundItem.setFont(font);
		Menu.add(soundItem);

		JMenuItem wordItem = new JMenuItem("단어추가");
		wordItem.setFont(font);
		Menu.add(wordItem);

		rankBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				soundManager.playSFX("sound/btnClick.wav");
				System.out.println("랭킹 open");
				stopAction(); // 게임 중일 경우 일시정지
				rankingPanel.makeRankingDialog();
			}
		});

		soundItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { 
				soundManager.playSFX("sound/btnClick.wav");
				// 소리 설정 추가
				System.out.println("소리설정 open");
				stopAction(); // 게임 중일 경우 일시정지
				if (soundPanel == null) {
                    soundPanel = new SoundPanel(soundManager);
                }
                soundPanel.makeSoundDialog();
			}
		});

		wordItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				soundManager.playSFX("sound/btnClick.wav");
				// 단어 설정 추가
				System.out.println("단어추가 open");
				stopAction(); // 게임 중일 경우 일시정지
				if (addWordPanel == null) {
                    addWordPanel = new AddWordPanel();
                }
                addWordPanel.makeAddWordDialog();
			}
		});
	}
	
	private void makeToolBar() {
		tBar = new JToolBar();
		
		tBar.setFloatable(false); 
		tBar.add(startBtn);
		tBar.add(stopBtn);
		tBar.add(exitBtn);
		tBar.add(curState);
		tBar.add(levLabel);
		
		ButtonGroup g = new ButtonGroup();
		for(int i=0; i<rb.length; i++) {
			rb[i] = new JRadioButton(diffText[i]);
			rb[i].setFont(font);
			rb[i].addItemListener(new MyItemListener());
			g.add(rb[i]);
			tBar.add(rb[i]);
		}
		rb[0].setSelected(true);
		
		stopBtn.setVisible(false); // 일시정지 버튼 게임 중 아니면 숨기기
		exitBtn.setVisible(false); // 나가기 버튼 게임 중 아니면 숨기기
		curState.setVisible(false);
		levLabel.setVisible(false);
		
		getContentPane().add(tBar, BorderLayout.NORTH); // NORTH 패널에 부착

		startBtn.addActionListener(new ActionListener() { // 시작 버튼 눌렀을 때 작동
			@Override
			public void actionPerformed(ActionEvent e) {
				soundManager.playSFX("sound/btnClick.wav");
				if (!pauseMainBGM) {
					pauseMainBGM = true;
					soundManager.pauseBGM();
					soundManager.playBGM("sound/background2.wav");
				}
				System.out.println("start action");
				if (!over) { // 게임오버 아닐 경우
					if (!start) { // 시작 전일때
						start = true; // startFlag 활성화 
						rankBtn.setVisible(false); // 랭킹버튼 비활성화
						startBtn.setVisible(false); // 시작버튼 비활성화 
						stopBtn.setVisible(true); // 일시정지버튼 활성화
						exitBtn.setVisible(true); // 나가기 버튼 활성화
						
						for (int i=0; i<rb.length; i++) {
							rb[i].setVisible(false);
						}
						curState.setVisible(true);
						levLabel.setVisible(true);
						
						gamePanel.startGame(); // gamePanel의 startGame 호출
					} else if (stop) { // 정지 상태일 경우
						stop = false; // stopFlag 해제
						startBtn.setVisible(false); // 시작버튼 비활성화
						stopBtn.setVisible(true); // 일시정지버튼 활성화
						exitBtn.setVisible(true); // 나가기 버튼 활성화 
						startBtn.setText("시작"); // 시작버튼 텍스트 초기화
						gamePanel.resumeGame(); // gamePanel의 resumeGame 호출
					}
				}
			}
		});
		
		stopBtn.addActionListener(new ActionListener() { // 일시정지 버튼 눌렀을 때 작동
			@Override
			public void actionPerformed(ActionEvent e) {
				soundManager.playSFX("sound/btnClick.wav");
				System.out.println("stop action");
				stopAction();
			}
		});
		
		exitBtn.addActionListener(new ActionListener() { // 나가기 버튼 눌렀을 때 작동
			@Override
			public void actionPerformed(ActionEvent e) {
				soundManager.playSFX("sound/btnClick.wav");
				System.out.println("exit action"); 
				gamePanel.resetGame(); // 게임 초기화
				exitBtn.setVisible(false); // 나가기 버튼 비활성화
				curState.setVisible(false);
				levLabel.setVisible(false);
				resetFlag();
			}
		});
		
	}
	
	class MyItemListener implements ItemListener { // 난이도 선택 라디오 이벤트
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.DESELECTED) return;
			if (rb[0].isSelected()) difficulty = 0;
			else if (rb[1].isSelected()) difficulty = 1;
			else difficulty = 2;
			
			curState.setText(" 난이도: "+ diffText[difficulty]);
		}
	}
	
	private void makeSplitPane() {
		JSplitPane hPane = new JSplitPane();
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		hPane.setDividerLocation(700);
		hPane.setEnabled(false);
		getContentPane().add(hPane, BorderLayout.CENTER);

		JSplitPane vPane = new JSplitPane();
		vPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		vPane.setDividerLocation(300);
		vPane.setEnabled(false);

		JPanel rightTopPanel = new JPanel(); // 우상단 패널 생성
		rightTopPanel.setLayout(new GridLayout(2, 1)); // 2행 1열로 나누기
		rightTopPanel.add(scorePanel); // 점수 패널 넣기
		rightTopPanel.add(comboPanel); // 콤보 패널 넣기

		JPanel rightBottomPanel = new JPanel(); // 우하단 패널 생성
		rightBottomPanel.setLayout(new GridLayout(2, 1)); // 2행 1열로 나누기
		rightBottomPanel.add(statusPanel); // 상태 패널 넣기
		rightBottomPanel.add(playerPanel); // 플레이어 패널 넣기

		vPane.setTopComponent(rightTopPanel); // vPane 상단에 배치
		vPane.setBottomComponent(rightBottomPanel); // vPane 하단에 배치

		hPane.setRightComponent(vPane); // hPane 오른쪽에 vPane 배치
		hPane.setLeftComponent(gamePanel); // hPane 왼쪽에 gamePanel 배치
	}
	
	public void resetStartFlag() { // startFlag 초기화
		start = false; 
	}

	public void setOverFlag() { // gameOver 시 호출
		if (over) return;
		over = true;
		exitBtn.setVisible(false);
		rankingPanel.addRanking();
	}

	public void resetOverFlag() { // overFlag 초기화
		over = false;
	}

	public JButton getStartBtn() {
		return startBtn;
	}

	public JButton getStopBtn() {
		return stopBtn;
	}
	
	public JButton getRankBtn() {
		return rankBtn;
	}
	
	public JButton getExitBtn() {
		return exitBtn;
	}
	
	public JMenu getMenu() { 
		return Menu;
	}
	
	public void setLevel(int level) {
		this.level = level;
		levLabel.setText(" , 레벨: " + level + "단계");
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	
	public void stopAction() { // 일시정지 메소드
		if (!over && start && !stop) { // 플레이 중 게임오버 상태가 아니고 일시정지 상태가 아니면
			stop = true; // stopFlag 활성화
			startBtn.setVisible(true); // 시작버튼 활성화
			stopBtn.setVisible(false); // 일시정지버튼 비활성화
			startBtn.setText("재개"); // 시작버튼 텍스트 재개로 변경
			gamePanel.stopGame(); // gamePanel의 stopGame 호출
		}
	}
	
	public void resetFlag() {// flag 초기화 메소드
		start = false;
		over = false;
		stop = false;
		pauseMainBGM = false;
		for (int i=0; i<rb.length; i++) {
			rb[i].setVisible(true);
		}
	}
}
