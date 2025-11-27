import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePanel extends JPanel {
	private GroundPanel groundPanel;
	private JTextField inputField = new JTextField(20);
	private InputPanel inputPanel = new InputPanel();
	private ScorePanel scorePanel = null;
	private ComboPanel comboPanel = null;
	private Vector<Asteroid> asteroids = new Vector<Asteroid>();
	private SetAsteroid set = null;

	public GamePanel(ScorePanel scorePanel, ComboPanel comboPanel, TextStore tStore) {
		this.setLayout(new BorderLayout());
		this.scorePanel = scorePanel;
		this.comboPanel = comboPanel;
		groundPanel = new GroundPanel();
		add(groundPanel, BorderLayout.CENTER);
		add(inputPanel, BorderLayout.SOUTH);
		set = new SetAsteroid(groundPanel, tStore, asteroids);
	}
	
	public void startGame() {
		set.startGame();
	}
	public void stopGame() {
		set.stopGame();
	}
	public void resumeGame() {
		set.resumeGame();
	}

	class GroundPanel extends JPanel {
		private ImageIcon bgIcon = new ImageIcon("image/background.jpg");
		private Image bgImg = bgIcon.getImage();
		
		public GroundPanel() {
			this.setLayout(null);
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(bgImg,0,0,getWidth(),getHeight(),this);
		}
		public SetAsteroid getSetAsteroid() {
			return set;
		}
	}

	class InputPanel extends JPanel {
		public InputPanel() {
			this.setBackground(Color.gray);
			add(inputField);

			inputField.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JTextField input = (JTextField) e.getSource();
					String userText = input.getText();
					boolean correct = false;
					for (int i = 0; i < asteroids.size(); i++) {
						Asteroid a = asteroids.get(i);

						if (userText.equals(a.getText())) {
							correct = true;
							comboPanel.increaseCombo();

							int score = a.getScore();
							if (comboPanel.isBurning()) {
								score *= 2;
							}
							scorePanel.increse(score);

							groundPanel.remove(a.getLabel());
							groundPanel.remove(a.getImageLabel());
							groundPanel.repaint();

							asteroids.remove(i);

							inputField.setText(""); // 입력창 비우기
							break; // 하나 맞췄으니 루프 종료
						}
					}
					if (!correct) {
						comboPanel.resetCombo(); // 콤보 초기화
						inputField.setText(""); // 입력창 비우기
					}
				}
			});
		}
	}
}
