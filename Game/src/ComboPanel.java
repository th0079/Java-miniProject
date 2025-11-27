import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ComboPanel extends JPanel {
	private int combo = 0;
	private boolean burning = false;
	private JLabel comboLabel = new JLabel("0",JLabel.CENTER);
	private JLabel text = new JLabel("COMBO!",JLabel.CENTER);
	
	public ComboPanel() {
		this.setBackground(Color.ORANGE);
		
		this.setLayout(new BorderLayout());
		
		text.setFont(new Font("NanumBarunGothic",Font.BOLD, 20));
		comboLabel.setFont(new Font("NanumBarunGothic",Font.BOLD, 40));
		add(text, BorderLayout.NORTH);
		add(comboLabel, BorderLayout.CENTER);
	}

	public void increaseCombo() {
		combo++;
		if (combo>=5) burning =true;
		updateCombo();
	}

	public void resetCombo() {
		combo = 0;
		burning = false;
		updateCombo();
	}

	public boolean isBurning() {
		return burning;
	}

	private void updateCombo() {
		comboLabel.setText(Integer.toString(combo));

		if (isBurning()) {
			comboLabel.setForeground(Color.RED);
		} else {
			comboLabel.setForeground(Color.BLACK);
		}
	}
}
