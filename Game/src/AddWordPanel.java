import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private JList<String> wordList = new JList<String>();
	private Vector<String> word = new Vector<String>();
	private JScrollPane scrollPane = null;
	private Font font = new Font("Galmuri9", Font.BOLD, 20);
	private ImageIcon bgIcon = new ImageIcon("image/background.jpg");
	private Image bgImg = bgIcon.getImage();
	
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
		
		scrollPane = new JScrollPane(wordList);
		scrollPane.setBounds(100,200,300,400);
		
		add(label);
		add(wordField);
		add(addBtn);
		add(scrollPane);
		
		requestFocus();
		setVisible(true);

		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (wordField.getText().trim().length() > 0) {
					addWord();
					wordField.setText("");
				}
			}
		});

		makeAddWordDialog();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
	}

	public void addWord() {
		String newWord = wordField.getText().trim();
		word.add(newWord);
		writeFile(newWord);
		wordList.setListData(word);
		System.out.println(newWord +" 저장 완료");
	}

	public void writeFile(String word) {
		try {
			FileWriter fw = new FileWriter("file/text.txt", true);
			PrintWriter pw = new PrintWriter(fw);

			pw.println(word);

			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void makeAddWordDialog() {
		addWord = new JDialog();

		addWord.setTitle("단어추가");
		addWord.setSize(500, 700);

		addWord.setLocation(700, 150);
		addWord.setContentPane(this);
		addWord.setModal(true);
		addWord.setVisible(true);
	}
	
	private void loadWord() {
		try {
			FileReader fr = new FileReader("file/text.txt");
			BufferedReader br = new BufferedReader(fr);

			String line = "";
			while (true) {
				line = br.readLine();
				
				if (line == null)
					break;

				if (line.trim().length() > 0) {
					word.add(line.trim());
				}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
