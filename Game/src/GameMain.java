
public class GameMain {

	public static void main(String[] args) {
		LoginPanel loginPanel = new LoginPanel();
		new GameFrame(loginPanel.getId());
	}

}
