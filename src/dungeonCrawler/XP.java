package dungeonCrawler;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class XP extends JPanel{
	private JTextField xp = new JTextField();
	private Board board = Board.getBoard();
	private static XP instance = new XP();
	
	public XP() {
		xp.setEditable(false);
		add(xp);
	}
	
	public void updateXP() {
		setVisible(false);
		xp.setText("XP: " + board.getPlayer().getXP());
		setVisible(true);
	}
	
	public static XP getInstance() {
		return instance;
	}
}
