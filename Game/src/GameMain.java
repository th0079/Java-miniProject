
public class GameMain {

	public static void main(String[] args) {
		LoginPanel loginPanel = new LoginPanel(); // 로그인 패널 생성
		
		if (loginPanel.isInput()) { // 입력된 id가 있을 경우
			new GameFrame(loginPanel.getId()); // GameFrame 객체 생성하면서 인자로 id 넣기
		}
		System.out.println(loginPanel.getId());
	}

}
