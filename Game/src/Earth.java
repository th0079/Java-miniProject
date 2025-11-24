
public class Earth {
	private int health = 100;
	private boolean gameOver = false;
	
	public void damaged(int damage) {
		health -= damage;
		if (health <= 0) {
			health =0;
			gameOver = true;
		}
	}
	public boolean gameOver() {
		return gameOver;
	}
}
