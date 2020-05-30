/*
 * Created by Daniel Sanchez
 * May 30, 2020
 * Temporary menu that determines if a new or old game will be loaded
 */
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

public class DungeonCrawler extends JFrame{
	private Board board;
	private StartButton startButton = new StartButton();
	private LoadGameButton loadGameButton = new LoadGameButton();
	private PlayerButton playerButton = new PlayerButton();
	private InventoryButton inventoryButton = new InventoryButton();
	private WeaponButton weaponButton = new WeaponButton();
	private ArmorButton armorButton = new ArmorButton();
	private JPanel initialMenu = new JPanel();
	private boolean visible = true;

	public void dungeonCrawler() {
		this.remove(initialMenu);
		setVisible(false);
		
		//Updates the board
		board = Board.getBoard();
		board.init();
		board.generateBoard();
		
		//Sets dimensions and title of the JFrame
		setSize(new Dimension(1250, 650));
		setTitle("Dungeon Crawler");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Sets up Board
		//Creates northern panel
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(1,1));
		
		JTextField playerHealth = new JTextField();
		playerHealth.setEditable(false);
		playerHealth.setText("Heath: " + board.getPlayer().getHP());
	
		northPanel.add(playerHealth);
		add(northPanel, BorderLayout.NORTH);
		
		//Creates Southern Panel
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(2,3));
		southPanel.add(inventoryButton.getButton());
		southPanel.add(playerButton.getButton());
		southPanel.add(weaponButton.getButton());
		southPanel.add(armorButton.getButton());
		
		add(southPanel, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
	public void initialMenu() {
		//Opens and initializes the board to be used throughout class
		board = Board.getBoard();
		board.init();

		//Sets dimensions and title for the JFrame
		setSize(new Dimension (500, 500));
		setTitle("Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Adds Initial Menu
		initialMenu.setLayout(new GridLayout(3,1));
		
		//Creates Title for Initial Menu
		JTextField title = new JTextField(15);
		title.setEditable(false);
		title.setText("Dungeon Crawler");
		initialMenu.add(title, BorderLayout.CENTER);
		
		//Adds buttons to Initial Menu
		initialMenu.add(startButton.getButton());
		initialMenu.add(loadGameButton.getButton());
		
		add(initialMenu, BorderLayout.CENTER);
		setVisible(true);
	}
	
	/*
	private void newGame() {
		setVisible(false);
	}
	*/
	
	private class PlayerButton extends JPanel {
		private JButton button;
		ButtonListener listener = new ButtonListener();
		
		public PlayerButton() {
			this.button = new JButton("Player");
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
				//ADD FUNCTIONALITY LATER
				//Displays player information
			}	
		}
	}
	
	private class ArmorButton extends JPanel {
		private JButton button;
		ButtonListener listener = new ButtonListener();
		
		public ArmorButton() {
			this.button = new JButton("Change Armor");
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
				//ADD FUNCTIONALITY LATER
				//Changes player's armor
			}	
		}
	}
	
	private class WeaponButton extends JPanel {
		private JButton button;
		ButtonListener listener = new ButtonListener();
		
		public WeaponButton() {
			this.button = new JButton("Change Weapon");
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
				//ADD FUNCTIONALITY LATER
				//Changes Player's Weapon
			}	
		}
	}
	
	private class InventoryButton extends JPanel {
		private JButton button;
		ButtonListener listener = new ButtonListener();
		
		public InventoryButton() {
			this.button = new JButton("Inventory");
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
				//ADD FUNCTIONALITY LATER
				//Displays player's inventory
			}	
		}
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
				dungeonCrawler();
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
				dungeonCrawler();
			}
		}
	}

	public static void main(String[] args) {
		DungeonCrawler dc = new DungeonCrawler();
		dc.initialMenu();
	}
	
	//Getters and Setters
	public boolean isVisible() {
		return visible;
	}
}