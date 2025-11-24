import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ComboPanel extends JPanel {
	private int combo = 0;
	private boolean burning = false;

	private JLabel comboText = new JLabel("0");

	public ComboPanel() {
		this.setBackground(Color.ORANGE);
		add(new JLabel("COMBO"));
		add(comboText);
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
		comboText.setText(Integer.toString(combo));

		if (isBurning()) {
			comboText.setForeground(Color.RED);
		} else {
			comboText.setForeground(Color.BLACK);
		}
	}
}
