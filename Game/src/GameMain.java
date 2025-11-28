
public class GameMain {

	public static void main(String[] args) {
		LoginPanel loginPanel = new LoginPanel();
		
		if (loginPanel.isInput()) {
			new GameFrame(loginPanel.getId());
		}
		System.out.println(loginPanel.getId());
	}

}
