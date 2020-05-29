package dungeonCrawler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class dungeonCrawler extends JFrame{
	private Board board;
	private StartButton startButton = new StartButton();
	private LoadGameButton loadGameButton = new LoadGameButton();
	private InitialMenuListener iMListener = new InitialMenuListener();

	public dungeonCrawler() {
		//Opens and initializes the board to be used throughout class
		board = Board.getBoard();
		board.init();

		//Sets dimensions and title for the JFrame
		setSize(new Dimension (1000, 650));
		setTitle("Dungeon Crawler");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Adds Initial Menu
		add(initialMenu(), BorderLayout.CENTER);

		if (board.isNewGame()) {
			System.out.println("New Game!");
		}
	}

	public JPanel initialMenu() {
		JPanel panel = new JPanel();
		JTextField title = new JTextField(15);
		
		//Set dimensions
		panel.setLayout(new GridLayout(3,1));

		//Creates Title
		title.setEditable(false);
		title.setText("Dungeon Crawler");
		panel.add(title, BorderLayout.CENTER);

		//Adds start button
		panel.add(startButton.getButton());

		//Adds load game button
		panel.add(loadGameButton.getButton());
		
		panel.addComponentListener(iMListener);
		
		return panel;
	}

	public static void main(String[] args) {
		dungeonCrawler dc = new dungeonCrawler();
		dc.setVisible(true);
	}

	private class InitialMenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == startButton) {
				getContentPane().removeAll();
				board.setNewGame(true);
			}
			if (e.getSource() == loadGameButton) {
				getContentPane().removeAll();
				board.setNewGame(false);
			}
		}

	}
}
