import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

public class GameFrame extends JFrame{
	private TextStore tStore = new TextStore();
	private JButton startBtn = new JButton("시작");
	private JButton stopBtn = new JButton("일시정지");
	private ScorePanel scorePanel = new ScorePanel();
	private ComboPanel comboPanel = new ComboPanel();
	private StatusPanel statusPanel = new StatusPanel();
	private RankPanel rankPanel = new RankPanel();
	private GamePanel gamePanel = new GamePanel(scorePanel, comboPanel, statusPanel, tStore);
	private boolean stop = false;
	private boolean start = false;
	
	public GameFrame(String id) {
		super("지구를 지켜라!");
		
		setSize(1000,800);
		setLocation(1000,1000);
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
		mBar.add(Menu);
		
//		JMenu editMenu = new JMenu("Edit");
//		mBar.add(editMenu);
		
		JMenuItem openItem = new JMenuItem("Open");
		Menu.add(openItem);
		Menu.add("Save");
		Menu.addSeparator();
	}
	
	private void makeToolBar() {
		JToolBar tBar = new JToolBar();
		tBar.setFloatable(false);
		tBar.add(startBtn);
		tBar.add(stopBtn);
		getContentPane().add(tBar,BorderLayout.NORTH);
		
		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!start) {
					start = true;
					gamePanel.startGame();
				}
				else if (stop) {
					stop = false;
					gamePanel.resumeGame();
				}
			}
		});
		stopBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (stop) return;
				else {
					stop = true;
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
		getContentPane().add(hPane,BorderLayout.CENTER);
		
		JSplitPane vPane = new JSplitPane();
		vPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		vPane.setDividerLocation(300);
		vPane.setEnabled(false);
		
		JPanel rightTopPanel = new JPanel();
        rightTopPanel.setLayout(new GridLayout(2, 1));
        rightTopPanel.add(scorePanel);
        rightTopPanel.add(comboPanel);
		
        JPanel rightBottomPanel = new JPanel();
        rightBottomPanel.setLayout(new GridLayout(2,1));
        rightBottomPanel.add(rankPanel);
        rightBottomPanel.add(statusPanel);
        
        vPane.setTopComponent(rightTopPanel);
        vPane.setBottomComponent(rightBottomPanel);
        
		hPane.setRightComponent(vPane);
		hPane.setLeftComponent(gamePanel);
	}
}
