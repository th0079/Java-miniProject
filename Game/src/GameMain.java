
public class GameMain {

	public static void main(String[] args) {
		LoginPanel loginPanel = new LoginPanel();
		while(true) {
			if (loginPanel.isInput()) break;
		}
		new GameFrame(loginPanel.getId());
	}

}
