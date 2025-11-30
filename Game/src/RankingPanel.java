import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

class Player {
	private String id;
	private int score;

	public Player(String id, int score) {
		this.id = id;
		this.score = score;
	}

	public String getId() {
		return id;
	}

	public int getScore() {
		return score;
	}
}

public class RankingPanel extends JPanel {
	private String id = null;
	private ScorePanel scorePanel = null;
	private JLabel label = new JLabel("순위", JLabel.CENTER);
	private JDialog ranking = null;
	private ArrayList<Player> players = new ArrayList<Player>();
	private Font mainFont = new Font("Galmuri9", Font.BOLD, 40);
	private Font subFont = new Font("Galmuri9", Font.BOLD, 30);
	private ImageIcon bgIcon = new ImageIcon("image/background.jpg");
	private Image bgImg = bgIcon.getImage();

	public RankingPanel(String id, ScorePanel scorePanel) {
		this.id = id;
		this.scorePanel = scorePanel;
		setLayout(new GridLayout(11, 1));
		setSize(500, 700);
		loadPlayer();
		players.sort((p1, p2) -> p2.getScore() - p1.getScore());

		label.setFont(mainFont);
		label.setSize(300, 50);
		label.setForeground(Color.WHITE);

		add(label);

		for (int i = 0; i < players.size(); i++) {
			if (i==10) break;
			Player p = players.get(i);
			String rankId = p.getId();
			String rankScore = Integer.toString(p.getScore());

			String rankLine = i + 1 + ". " + rankId + " : " + rankScore;
			JLabel rankLabel = new JLabel(rankLine, JLabel.CENTER);
			rankLabel.setFont(subFont);
			rankLabel.setSize(200, 40);
			rankLabel.setForeground(Color.WHITE);
			add(rankLabel);

		}
		requestFocus();
		setVisible(true);

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
	}

	public void loadPlayer() {
		try {
			FileReader fr = new FileReader("text/players.txt");
			BufferedReader br = new BufferedReader(fr);

			String idLine = "";
			String scoreLine = "";
			while (true) {
				idLine = br.readLine();
				scoreLine = br.readLine();

				if (idLine == null || scoreLine == null)
					break;

				if (idLine.length() > 0 && scoreLine.length() > 0) {

					idLine = idLine.trim();
					scoreLine = scoreLine.trim();
					int s = Integer.parseInt(scoreLine);
					Player p = new Player(idLine, s);
					players.add(p);
				}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addRanking() {
		try {
			FileWriter fw = new FileWriter("text/players.txt", true);
			PrintWriter pw = new PrintWriter(fw);

			pw.println(id);
			pw.println(scorePanel.getScore());

			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void makeRankingDialog() {
		ranking = new JDialog();

		ranking.setTitle("랭킹");
		ranking.setSize(500, 700);

		ranking.setLocation(700, 100);
		ranking.setContentPane(this);
		ranking.setModal(true);
		ranking.setVisible(true);
	}
}
