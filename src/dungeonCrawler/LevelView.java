package dungeonCrawler;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class LevelView extends JPanel {
	private JTextField level = new JTextField();
	private static LevelView instance = new LevelView();
	
	public LevelView() {
		level.setEditable(false);
		add(level);
	}
	
	public void updateLevel() {
		setVisible(false);
		level.setText("Level: " + Board.getBoard().getLevel());
		setVisible(true);
	}
	
	public static LevelView getInstance() {
		return instance;
	}
}
