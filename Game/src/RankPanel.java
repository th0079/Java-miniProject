import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class RankPanel extends JPanel{
	private ImageIcon bgIcon = new ImageIcon("image/rankBg.png");
	private Image bgImg = bgIcon.getImage();
	private JButton rankBtn = new JButton("랭킹 보기");
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bgImg,0,0,getWidth(),getHeight(),this);
	}
	
	
	public RankPanel() {
		setLayout(null);
		setSize(275,200);
		rankBtn.setSize(100,50);
		int x = (int)(getWidth()-100)/2;
		rankBtn.setLocation(x,getHeight()/2);
		add(rankBtn);
	}
}
