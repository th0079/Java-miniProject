import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditPanel extends JPanel{
	//private JTextField textField = new JTextField(20);
	private JButton addButton = new JButton("랭킹 보기");
	public EditPanel() {
		this.setBackground(Color.cyan);
		//add(textField);
		add(addButton);
	}
}
