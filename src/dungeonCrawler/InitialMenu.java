package dungeonCrawler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InitialMenu extends JFrame{
	private Board board;
	private StartButton startButton = new StartButton();
	private LoadGameButton loadGameButton = new LoadGameButton();

	public InitialMenu() {
		//Opens and initializes the board to be used throughout class
		board = Board.getBoard();
		board.init();

		setVisible(true);
		
		//Sets dimensions and title for the JFrame
		setSize(new Dimension (1000, 650));
		setTitle("Dungeon Crawler");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Adds Initial Menu
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,1));
		
		//Creates Title for Initial Menu
		JTextField title = new JTextField(15);
		title.setEditable(false);
		title.setText("Dungeon Crawler");
		panel.add(title, BorderLayout.CENTER);
		
		//Adds buttons to Initial Menu
		panel.add(startButton.getButton());
		panel.add(loadGameButton.getButton());
		
		add(panel, BorderLayout.CENTER);
	}
	
	private void newGame() {
		getContentPane().removeAll();
		if (board.isNewGame()) {
			System.out.println("New Game");
		}
	}

	public static void main(String[] args) {
		InitialMenu dc = new InitialMenu();
		dc.setVisible(true);
	}

	private class StartButton extends JPanel{
		private JButton button;
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
				newGame();
			}
			
		}
	}
	
	private class LoadGameButton extends JPanel{
		private JButton button;
		ButtonListener listener = new ButtonListener();
		
		public LoadGameButton() {
			this.button = new JButton("Load New Game");
			this.button.addActionListener(listener);
		}
		
		//Getter method for button
		public JButton getButton() {
			return button;
		}
		
		private class ButtonListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				//Ensures that the board object is current
				board = Board.getBoard();
				//Sets board's new game boolean to false
				board.setNewGame(false);
				newGame();
			}
			
		}
	}
}