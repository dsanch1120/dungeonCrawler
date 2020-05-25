package dungeonCrawler;

public class GameEngine {
	Board board = Board.getBoard();
	
	public void beginGame() {
		board.init();
	}
	
	public static void main(String[] args) {
		GameEngine engine = new GameEngine();
		engine.beginGame();
	}
}
