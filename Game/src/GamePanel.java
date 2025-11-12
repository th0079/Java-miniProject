import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePanel extends JPanel{
	private JLabel textLabel = new JLabel("Hello");
	private GroundPanel groundPanel = new GroundPanel();
	private JTextField inputField = new JTextField(20);
	private InputPanel inputPanel = new InputPanel();
	private ScorePanel scorePanel = null;
	private TextStore tStore = null;
	
	public GamePanel(ScorePanel scorePanel, TextStore tStore) {
		this.setLayout(new BorderLayout());
		this.scorePanel = scorePanel;
		this.tStore = tStore;
		add(groundPanel,BorderLayout.CENTER);
		add(inputPanel,BorderLayout.SOUTH);
	}
	
	class GroundPanel extends JPanel {
		public GroundPanel() {
			this.setBackground(Color.white);
			this.setLayout(null);
			textLabel.setLocation(100,100);
			textLabel.setSize(200,20);
			add(textLabel);
		}
	}
	
	class InputPanel extends JPanel{
		public InputPanel() {
			this.setBackground(Color.gray);
			add(inputField);
			inputField.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JTextField input = (JTextField)e.getSource();
					if (input.getText().equals(textLabel.getText())) {
						scorePanel.increse(20);
						String text = tStore.get();
						textLabel.setText(text);
					}
				}
			});
		}
	}
}
