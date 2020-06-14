package dungeonCrawler;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class Health extends JPanel{
	private JTextField health = new JTextField();
	private Board board = Board.getBoard();
	private static Health instance = new Health();
	
	public Health() {
		health.setEditable(false);
		add(health);
	}
	
	public void updateHealth() {
		setVisible(false);
		health.setText("Health: " + board.getPlayer().getHP());
		setVisible(true);
	}
	
	public static Health getInstance() {
		return instance;
	}
}
