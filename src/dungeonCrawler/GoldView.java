package dungeonCrawler;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class GoldView extends JPanel {
	private JTextField gold = new JTextField();
	private static GoldView instance = new GoldView();
	
	public GoldView() {
		gold.setEditable(false);
		add(gold);
	}
	
	public void updateGold() {
		setVisible(false);
		gold.setText("Gold: " + Board.getBoard().getPlayer().getPurse().behavior());
		setVisible(true);
	}
	
	public static GoldView getInstance() {
		return instance;
	}
}
