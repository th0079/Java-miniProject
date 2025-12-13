import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class AddWordPanel extends JPanel {
	private JLabel label = new JLabel("추가할 단어를 입력해주세요!",JLabel.CENTER);
	private JTextField wordField = new JTextField(20);
	private JButton addBtn = new JButton("저장");
	private JDialog addWord = null;
	private JList<String> wordList = new JList<String>(); // 단어 출력할 리스트
	private Vector<String> word = new Vector<String>(); // 단어 받아와서 저장할 벡터
	private JScrollPane scrollPane = null;
	private Font font = new Font("Galmuri9", Font.BOLD, 20);
	private ImageIcon bgIcon = new ImageIcon("image/background.jpg");
	private Image bgImg = bgIcon.getImage();
	private SoundManager soundManager = new SoundManager();
	
	public AddWordPanel() {
		setLayout(null);
		setSize(500, 700);
		loadWord();
		wordList.setListData(word);
		wordList.setFont(font);
		
		label.setFont(font);
		label.setBounds(100, 50, 300, 50);
		label.setForeground(Color.WHITE);

		wordField.setBounds(100, 100, 225, 30);
		wordField.setFont(font);

		addBtn.setBounds(325, 100, 75, 30);
		addBtn.setFont(font);
		
		scrollPane = new JScrollPane(wordList); // 스크롤 패널에 wordList 넣기
		scrollPane.setBounds(100,200,300,400);
		
		add(label);
		add(wordField);
		add(addBtn);
		add(scrollPane);
		addKeyListener(new ExitESC());
		
		setFocusable(true);
		requestFocus();
		setVisible(true); 

		addBtn.addActionListener(new ActionListener() { // 저장 버튼을 누르면
			@Override
			public void actionPerformed(ActionEvent e) {
				soundManager.playSFX("sound/btnClick.wav");
				if (wordField.getText().trim().length() > 0) { // 입력된 값이 있을 때
					addWord();
					wordField.setText(""); // 입력창 초기화
				}
			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
	}

	public void addWord() {
		String newWord = wordField.getText().trim(); // 입력받은 값 앞뒤 공백 제거 후 반환
		word.add(newWord); // word 벡터에 저장
		writeFile(newWord); // text.txt 파일에 저장
		wordList.setListData(word); // 리스트 저장
		System.out.println(newWord +" 저장 완료");
	}

	public void writeFile(String word) {
		try {
			FileWriter fw = new FileWriter("file/text.txt", true); // 파일 열기
			PrintWriter pw = new PrintWriter(fw);

			pw.println(word); // 입력받은 word 작성 후 줄바꿈

			pw.close(); // 파일 닫기
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void makeAddWordDialog() { // Dialog 생성 메소드
		addWord = new JDialog(); // Dialog 객체 생성
 
		addWord.setTitle("단어추가");
		addWord.setSize(500, 700);

		addWord.setLocation(700, 150);
		addWord.setContentPane(this); // AddWordPanel 넣기
		addWord.setModal(true); // 다른 작업 못하게 modal 설정
		addWord.setVisible(true); // 활성화
	}
	
	private void loadWord() { // 파일에서 단어 읽기
		try {
			FileReader fr = new FileReader("file/text.txt"); // 파일 열기
			BufferedReader br = new BufferedReader(fr);

			String line = ""; 
			while (true) {
				line = br.readLine(); // 라인 읽기
				
				if (line == null) // 라인 null 이면 break
					break;

				if (line.trim().length() > 0) { // 공백 제거한 길이가 0이상이면
					word.add(line.trim()); // word 벡터에 추가
				}
			}
			br.close(); // 파일 닫기
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	class ExitESC extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			int code = e.getKeyCode();
			switch (code) {
			case KeyEvent.VK_ESCAPE:
				addWord.setVisible(false);
			}
		}
	}
}
