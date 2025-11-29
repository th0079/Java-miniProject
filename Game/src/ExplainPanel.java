import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ExplainPanel extends JPanel{
	private ImageIcon bgIcon = new ImageIcon("image/background.jpg");
	private Image bgImg = bgIcon.getImage();
	private Font font = new Font("Galmuri9", Font.BOLD, 20);
	
	public ExplainPanel() {
		setLayout(new GridLayout(15,1));
		setSize(700,800);
		
		JLabel ex1 = new JLabel(" 푸른 소행성 : 기본 소행성");
		ex1.setFont(font);
		ex1.setForeground(Color.BLUE);
		ex1.setSize(400,50);
		add(ex1);
		JLabel ex2 = new JLabel(" >> 점수: 10, 피해량: 10, 속도: 5");
		ex2.setFont(font);
		ex2.setForeground(Color.WHITE);
		ex2.setSize(400,50);
		add(ex2);
		
		JLabel ex3 = new JLabel(" 붉은 소행성 : 속도가 빠른 소행성");
		ex3.setFont(font);
		ex3.setForeground(Color.RED);
		ex3.setSize(400,50);
		add(ex3);
		JLabel ex4 = new JLabel(" >> 점수: 30, 피해량: 20, 속도: 10");
		ex4.setFont(font);
		ex4.setForeground(Color.WHITE);
		ex4.setSize(400,50);
		add(ex4);
		
		JLabel ex5 = new JLabel(" 회색 소행성 : 느리지만 긴 단어만 나오는 소행성");
		ex5.setFont(font);
		ex5.setForeground(Color.GRAY);
		ex5.setSize(400,50);
		add(ex5);
		JLabel ex6 = new JLabel(" >> 점수: 50, 피해량: 30, 속도: 2");
		ex6.setFont(font);
		ex6.setForeground(Color.WHITE);
		ex6.setSize(700,50);
		add(ex6);
		
		JLabel ex7 = new JLabel(" 5 COMBO부터 점수 2배 획득!");
		ex7.setFont(font);
		ex7.setForeground(Color.ORANGE);
		ex7.setSize(400,50);
		add(ex7);
		
		JLabel ex8 = new JLabel(" 쉬움, 중간, 어려움, 3가지 단계가 있으며,");
		ex8.setFont(font);
		ex8.setForeground(Color.WHITE);
		ex8.setSize(400,50);
		add(ex8);
		setVisible(true);
		JLabel ex9 = new JLabel(" 난이도가 높아질수록 소행성 생성 속도가 빨라짐");
		ex9.setFont(font);
		ex9.setForeground(Color.WHITE);
		ex9.setSize(400,50);
		add(ex9);
		setVisible(true);
		
//		JLabel ex10 = new JLabel(" 시작버튼을 눌러 시작 및 재개를 할 수 있고");
//		ex10.setFont(font);
//		ex10.setForeground(Color.WHITE);
//		ex10.setSize(400,50);
//		add(ex10);
//		setVisible(true);
//		JLabel ex11 = new JLabel(" 일시정지 버튼으로 정지할 수 있음");
//		ex11.setFont(font);
//		ex11.setForeground(Color.WHITE);
//		ex11.setSize(400,50);
//		add(ex11);
//		setVisible(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
	}
}
