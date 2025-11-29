import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

public class GameFrame extends JFrame {
	private TextStore tStore = new TextStore();
	private JButton startBtn = new JButton("시작");
	private JButton stopBtn = new JButton("일시정지");
	private ScorePanel scorePanel = new ScorePanel();
	private ComboPanel comboPanel = new ComboPanel();
	private StatusPanel statusPanel = new StatusPanel();
	private PlayerPanel playerPanel = null;
	private GamePanel gamePanel = null;
	private boolean stop = false;
	private boolean start = false;
	private boolean over = false;
	private String id = null;
	private Font font = new Font("Galmuri9", Font.BOLD, 20);
	private ImageIcon bgImg = new ImageIcon("image/background.jpg");
	

	public GameFrame(String id) {
		super("지구를 지켜라!");
		gamePanel = new GamePanel(this, scorePanel, comboPanel, statusPanel, tStore);
		this.id = id;
		playerPanel = new PlayerPanel(id);
		startBtn.setFont(font);
		stopBtn.setFont(font);
		setSize(1000, 800);
		setLocation(500, 100);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		makeMenu();
		makeToolBar();
		makeSplitPane();
		setVisible(true);

	}

	private void makeMenu() {
		JMenuBar mBar = new JMenuBar();
		this.setJMenuBar(mBar);

		JMenu Menu = new JMenu("설정");
		Menu.setFont(font);
		mBar.add(Menu);

		JButton rankBtn = new JButton("랭킹");
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
				// 랭킹 대시보드 추가
				System.out.println("랭킹 open");
			}
		});

		soundItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 소리 설정 추가
				System.out.println("소리설정 open");
			}
		});

		wordItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 단어 설정 추가
				System.out.println("단어추가 open");
			}
		});
	}

	public void resetStartFlag() {
		start = false;
	}

	public void setOverFlag() {
		over = true;
	}

	public void resetOverFlag() {
		over = false;
	}

	public JButton getStartBtn() {
		return startBtn;
	}

	public JButton getStopBtn() {
		return stopBtn;
	}

	private void makeToolBar() {
		JToolBar tBar = new JToolBar();
		tBar.setFloatable(false);
		tBar.add(startBtn);
		tBar.add(stopBtn);
		stopBtn.setVisible(false);
		getContentPane().add(tBar, BorderLayout.NORTH);

		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!over) {
					if (!start) {
						start = true;
						startBtn.setVisible(false);
						stopBtn.setVisible(true);
						gamePanel.startGame();
					} else if (stop) {
						stop = false;
						startBtn.setVisible(false);
						stopBtn.setVisible(true);
						startBtn.setText("시작");
						gamePanel.resumeGame();
					}
				}
			}
		});
		stopBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!over && start && !stop) {
					stop = true;
					startBtn.setVisible(true);
					stopBtn.setVisible(false);
					startBtn.setText("재개");
					gamePanel.stopGame();
				}
			}
		});
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

		JPanel rightTopPanel = new JPanel();
		rightTopPanel.setLayout(new GridLayout(2, 1));
		rightTopPanel.add(scorePanel);
		rightTopPanel.add(comboPanel);

		JPanel rightBottomPanel = new JPanel();
		rightBottomPanel.setLayout(new GridLayout(2, 1));
		rightBottomPanel.add(statusPanel);
		rightBottomPanel.add(playerPanel);

		vPane.setTopComponent(rightTopPanel);
		vPane.setBottomComponent(rightBottomPanel);

		hPane.setRightComponent(vPane);
		hPane.setLeftComponent(gamePanel);
	}
}
