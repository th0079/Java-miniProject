import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

public class GameFrame extends JFrame{
	private TextStore tStore = new TextStore();
	private JMenuItem startItem = new JMenuItem("Start");
	private JButton startBtn = new JButton("Start");
	private ScorePanel scorePanel = new ScorePanel();
	private EditPanel editPanel = new EditPanel();
	private GamePanel gamePanel = new GamePanel(scorePanel, tStore);
	
	public GameFrame() {
		super("게임");
		setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		makeMenu();
		makeToolBar();
		makeSplitPane();
		setVisible(true);
	}
	
	private void makeMenu() {
		JMenuBar mBar = new JMenuBar();
		this.setJMenuBar(mBar);
		
		JMenu fileMenu = new JMenu("File");
		mBar.add(fileMenu);
		
		JMenu editMenu = new JMenu("Edit");
		mBar.add(editMenu);
		
		JMenuItem openItem = new JMenuItem("Open");
		fileMenu.add(openItem);
		fileMenu.add("Save");
		fileMenu.addSeparator();
		fileMenu.add(startItem);
	}
	
	private void makeToolBar() {
		JToolBar tBar = new JToolBar();
		tBar.setFloatable(false);
		tBar.add(startBtn);
		getContentPane().add(tBar,BorderLayout.NORTH);
		
		
	}
	
	private void makeSplitPane() {
		JSplitPane hPane = new JSplitPane();
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		hPane.setDividerLocation(500);
		hPane.setEnabled(false);
		getContentPane().add(hPane,BorderLayout.CENTER);
		
		JSplitPane vPane = new JSplitPane();
		vPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		vPane.setDividerLocation(200);
		vPane.setEnabled(false);
		vPane.setTopComponent(scorePanel);
		vPane.setBottomComponent(editPanel);
		
		hPane.setRightComponent(vPane);
		hPane.setLeftComponent(gamePanel);
	}
}
