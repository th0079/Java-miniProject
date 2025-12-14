import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

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

// 랭킹 패널
public class RankingPanel extends JPanel {
	private String id = null;
	private ScorePanel scorePanel = null;
	private JLabel label = new JLabel("순위", JLabel.CENTER);
	private JDialog ranking = null;
	private ArrayList<Player> players = new ArrayList<Player>(); // 플레이어 객체 ArrayList
	private Font mainFont = new Font("Galmuri9", Font.BOLD, 40);
	private Font subFont = new Font("Galmuri9", Font.BOLD, 30);
	private ImageIcon bgIcon = new ImageIcon("image/background.jpg");
	private Image bgImg = bgIcon.getImage();

	public RankingPanel(String id, ScorePanel scorePanel) {
		this.id = id; // 플레이어 이름
		this.scorePanel = scorePanel; // 점수 받아올 클래스
		setLayout(new GridLayout(11, 1));
		setSize(500, 700);
		
		label.setFont(mainFont);
		label.setSize(300, 50);
		label.setForeground(Color.WHITE);

		add(label);
		addKeyListener(new ExitESC());
		
		setFocusable(true);
		requestFocus();
		setVisible(true);
		
		loadPlayer();
		updateRanking();
	}
	
	public void updateRanking() {
		this.removeAll();
		add(label);
		players.sort((p1, p2) -> p2.getScore() - p1.getScore()); // 내림차 순으로 정렬
		
		for (int i = 0; i < players.size(); i++) {
			if (i==10) break; // 10등까지 출력
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
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
	}

	public void loadPlayer() { // 파일에서 플레이어와 점수 읽기
		players.clear();
		try {
			FileReader fr = new FileReader("file/players.txt"); // 파일 열기
			BufferedReader br = new BufferedReader(fr);

			String idLine = ""; // id 라인
			String scoreLine = ""; // score 라인
			while (true) {
				idLine = br.readLine();
				scoreLine = br.readLine();

				if (idLine == null || scoreLine == null) // 둘 중 하나 null 이면 종료
					break;

				if (idLine.length() > 0 && scoreLine.length () > 0) { // 길이 0 아니면
					idLine = idLine.trim(); // 양 옆 공백 제거
					scoreLine = scoreLine.trim(); // 양 옆 공백 제거
					int s = Integer.parseInt(scoreLine); // int로 변환
					Player p = new Player(idLine, s); // Player 객체 p 생성
					players.add(p); // ArrayList에 추가
				}
			}
			br.close(); // 파일 닫기
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addRanking() {
		try {
			FileWriter fw = new FileWriter("file/players.txt", true); // 파일 열기
			PrintWriter pw = new PrintWriter(fw);
			if (scorePanel.getScore() >0) {
				pw.println(id); // id 적고 줄바꿈
				pw.println(scorePanel.getScore()); // 점수 받아오고 줄바꿈
			}
			pw.close(); // 파일 닫기
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void makeRankingDialog() { // Dialog 생성 메소드
		loadPlayer();
		updateRanking();
		ranking = new JDialog(); // Dialog 객체 생성

		ranking.setTitle("랭킹");
		ranking.setSize(500, 700);
		ranking.setLocation(700, 150);
		
		ranking.setContentPane(this); // RankingPanel 넣기
		ranking.setModal(true); // 다른 작업 못하게 modal 설정
		ranking.setVisible(true); // 활성화
	}
	
	class ExitESC extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			int code = e.getKeyCode();
			switch (code) {
			case KeyEvent.VK_ESCAPE:
				ranking.setVisible(false);
			}
		}
	}
}
