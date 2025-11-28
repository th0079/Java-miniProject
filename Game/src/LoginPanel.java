import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {
		private JLabel label = new JLabel("이름을 입력해주세요!");
		private JTextField id = new JTextField(20);
		private JButton loginBtn = new JButton("로그인");
		private ImageIcon bgIcon = new ImageIcon("image/background.jpg");
		private Image bgImg = bgIcon.getImage();
		private boolean inputCheck = false;
		private JDialog login = null;
		private Font font = new Font("Galmuri9",Font.BOLD,20);
		
		public LoginPanel() {
			setLayout(null);
			setSize(400,300);
			
			label.setFont(font);
			label.setBounds(100,50,300,50);
			label.setForeground(Color.WHITE);
			id.setBounds(100,100,200,30);
			id.setFont(font);
			loginBtn.setBounds(150,150,100,50);
			loginBtn.setFont(font);
			
			add(label);
			add(id);
			add(loginBtn);
			requestFocus();
			setVisible(true);
			
			loginBtn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					login.setVisible(false);
					inputCheck = true;
				}
			});
			
			makeLoginDialog();
		}
		public boolean isInput() {
			return inputCheck;
		}
		
		public String getId() {
			if(id.getText().trim().length() ==0) return "Unknown";
			else return id.getText().trim();
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(bgImg,0,0,getWidth(),getHeight(),this);
		}
		
		private void makeLoginDialog() {
			login = new JDialog();
			
			login.setTitle("로그인창");
			login.setSize(400,300);
			
			login.setLocation(700,300);
			login.setContentPane(this);
			login.setModal(true);
			login.setVisible(true);
		}
		
		class LoginThread extends Thread {
			
		}
}
