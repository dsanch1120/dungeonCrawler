package dungeonCrawler;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class InitialMenu extends JPanel {
	private Board board;
	private JTextField title = new JTextField(15);
	private StartButton startButton = new StartButton();
	private LoadGameButton loadGameButton = new LoadGameButton();
	
	public InitialMenu() {
		board = Board.getBoard();
		
		//Set dimensions
		setLayout(new GridLayout(3,1));
		
		//Creates Title
		title.setEditable(false);
		title.setText("Dungeon Crawler");
		add(title, BorderLayout.CENTER);
		
		//Adds start button
		add(startButton.getButton());
		
		//Adds load game button
		add(loadGameButton.getButton());
		
	
	}
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == startButton) {
				board.setNewGame(true);
			}
			if (e.getSource() == loadGameButton) {
				board.setNewGame(false);
			}
		}
		
	}
	
}
