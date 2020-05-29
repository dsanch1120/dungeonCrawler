package dungeonCrawler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class StartButton extends JPanel{
	private JButton button;
	Board board;
	ButtonListener listener = new ButtonListener();
	
	public StartButton() {
		this.button = new JButton("Start Game");
		this.button.addActionListener(listener);
	}
	
	//Getter method
	public JButton getButton() {
		return button;
	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//Ensures that the board object is current
			board = Board.getBoard();
			//Sets board newGame boolean to true
			board.setNewGame(true);
		}
		
	}
}
