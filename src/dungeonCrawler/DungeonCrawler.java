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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DungeonCrawler extends JFrame{
	private Board board;
	private Player player = Board.getPlayer();
	private StartButton startButton = new StartButton();
	private LoadGameButton loadGameButton = new LoadGameButton();
	private PlayerButton playerButton = new PlayerButton();
	private InventoryButton inventoryButton = new InventoryButton();
	private WeaponButton weaponButton = new WeaponButton();
	private ArmorButton armorButton = new ArmorButton();
	private PotionButton potionButton = new PotionButton();
	private SettingsButton settingsButton = new SettingsButton();
	private InitialSubmitButton iSButton = new InitialSubmitButton();
	private JPanel initialMenu = new JPanel();
	private JTextField userName = new JTextField(30);
	private JTextField pointsDisplay = new JTextField();
	private JTextField strengthPoints = new JTextField();
	private JTextField endurancePoints = new JTextField();
	private JTextField defensePoints = new JTextField();
	private JTextField agilityPoints = new JTextField();
	private JTextField perceptionPoints = new JTextField();
	private JTextField intelligencePoints = new JTextField();
	private JTextField charismaPoints = new JTextField();
	private int availablePoints = 8;
	private AttributeSlider strengthSlider = new AttributeSlider("Strength", 0);
	private AttributeSlider enduranceSlider = new AttributeSlider("Endurance", 1);
	private AttributeSlider defenseSlider = new AttributeSlider("Defense", 2);
	private AttributeSlider agilitySlider = new AttributeSlider("Agility", 3);
	private AttributeSlider perceptionSlider = new AttributeSlider("Perception", 4);
	private AttributeSlider intelligenceSlider = new AttributeSlider("Intelligence", 5);
	private AttributeSlider charismaSlider = new AttributeSlider("Charisma", 6);
	private ArrayList<JTextField> attributePointTitles = new ArrayList<JTextField>();

	//GUI for the main game
	public void dungeonCrawler() {
		this.getContentPane().removeAll();
		setVisible(false);
		
		//Updates the board
		board = Board.getBoard();
		board.init();
		board.generateBoard();

		//Sets dimensions and title of the JFrame
		setSize(new Dimension(1500, 1010));
		setTitle("Dungeon Crawler");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Sets up Board
		//Creates northern panel
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(1,3));

		JTextField playerHealth = new JTextField();
		playerHealth.setEditable(false);
		playerHealth.setText("Heath: " + board.getPlayer().getHP());

		JTextField playerName = new JTextField();
		playerName.setEditable(false);
		playerName.setText(board.getPlayer().getName());

		JTextField playerXP = new JTextField();
		playerXP.setEditable(false);
		playerXP.setText("XP: " + board.getPlayer().getXP());

		northPanel.add(playerHealth);
		northPanel.add(playerName);
		northPanel.add(playerXP);
		add(northPanel, BorderLayout.NORTH);

		//Creates and adds Southern Panel
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(2,3));
		southPanel.add(inventoryButton.getButton());
		southPanel.add(playerButton.getButton());
		southPanel.add(settingsButton.getButton());
		southPanel.add(weaponButton.getButton());
		southPanel.add(potionButton.getButton());
		southPanel.add(armorButton.getButton());

		add(southPanel, BorderLayout.SOUTH);

		//Adds Board to JFrame
		add(board, BorderLayout.CENTER);

		//Adds key bindings
		InputMap im = board.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		//Handles moving up
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "Move up");
		ActionMap am = board.getActionMap();
		am.put("Move up", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				board.getPlayer().moveY(-1);
				board.checkLocation();
				repaint();
			}
		});
		//Handles moving left
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "Move left");
		am.put("Move left", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				board.getPlayer().moveX(-1);
				board.checkLocation();
				repaint();
			}
		});
		//Handles moving right
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "Move right");
		am.put("Move right", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				board.getPlayer().moveX(1);
				board.checkLocation();
				repaint();
			}
		});
		//Handles moving down
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "Move down");
		am.put("Move down", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				board.getPlayer().moveY(1);
				board.checkLocation();
				repaint();
			}
		});
		setVisible(true);
		
		JOptionPane.showMessageDialog(this, "You are " + board.getPlayer().getName() + ". \n Survive the Dungeon");
	}
	//GUI for the initial menu
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
	//GUI for choosing a new player
	public void newPlayer() {
		//Variables
		final int MIN_POINTS = 1;
		final int MAX_POINTS = 5;

		attributePointTitles.add(strengthPoints);
		attributePointTitles.add(endurancePoints);
		attributePointTitles.add(defensePoints);
		attributePointTitles.add(agilityPoints);
		attributePointTitles.add(perceptionPoints);
		attributePointTitles.add(intelligencePoints);
		attributePointTitles.add(charismaPoints);

		//Clears the JFrame
		this.remove(initialMenu);
		setVisible(false);

		//Modifies Size and title
		setSize(new Dimension(600, 750));
		setTitle("Create Character");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Components of new player
		pointsDisplay.setEditable(false);
		pointsDisplay.setText("Remaining Points: " + availablePoints);
		add(pointsDisplay, BorderLayout.NORTH);
		//Creates panel
		JPanel panel = characterSliders();
		add(panel, BorderLayout.CENTER);

		add(iSButton.getButton(), BorderLayout.SOUTH);

		setVisible(true);
	}

	private JPanel characterSliders() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(15,1));
		//User's name
		JPanel namePanel = new JPanel();
		namePanel.setBorder(new TitledBorder(new EtchedBorder(), "Input Name"));
		userName.setEditable(true);
		namePanel.add(userName);
		panel.add(namePanel);
		//Strength title and Slider
		strengthPoints.setEditable(false);
		strengthPoints.setText("Strength: 1");
		panel.add(strengthPoints);
		panel.add(strengthSlider.getSlider());
		//Endurance title and Slider
		endurancePoints.setEditable(false);
		endurancePoints.setText("Endurance: 1");
		panel.add(endurancePoints);
		panel.add(enduranceSlider.getSlider());
		//Defense title and Slider
		defensePoints.setEditable(false);
		defensePoints.setText("Defense: 1");
		panel.add(defensePoints);
		panel.add(defenseSlider.getSlider());
		//Agility title and Slider
		agilityPoints.setEditable(false);
		agilityPoints.setText("Agility: 1");
		panel.add(agilityPoints);
		panel.add(agilitySlider.getSlider());
		//Perception title and Slider
		perceptionPoints.setEditable(false);
		perceptionPoints.setText("Perception: 1");
		panel.add(perceptionPoints);
		panel.add(perceptionSlider.getSlider());
		//Intelligence title and Slider
		intelligencePoints.setEditable(false);
		intelligencePoints.setText("Intelligence: 1");
		panel.add(intelligencePoints);
		panel.add(intelligenceSlider.getSlider());
		//Charisma title and Slider
		charismaPoints.setEditable(false);
		charismaPoints.setText("Charisma: 1");
		panel.add(charismaPoints);
		panel.add(charismaSlider.getSlider());

		return panel;
	}

	//Classes for the various Components
	private class AttributeSlider extends JSlider {
		private JSlider slider;
		private SliderListener listener = new SliderListener();
		private String type;
		private int typeIndex;
		private int previousValue = 1;

		public AttributeSlider(String type, int typeIndex) {
			this.slider = new JSlider(JSlider.HORIZONTAL, 1, 5, 1);
			this.slider.setMajorTickSpacing(1);
			this.slider.setPaintTicks(true);
			this.slider.addChangeListener(listener);
			this.type = type;
			this.typeIndex = typeIndex;
		}

		public JSlider getSlider() {
			return slider;
		}

		private class SliderListener implements ChangeListener {

			@Override
			public void stateChanged(ChangeEvent e) {
				int value = slider.getValue();
				if (previousValue - value > 0) {
					availablePoints++;
				}
				if (previousValue - value < 0) {
					availablePoints--;
				}
				previousValue = value;
				pointsDisplay.setEditable(true);
				pointsDisplay.setText("Remaining Points: " + availablePoints);
				pointsDisplay.setEditable(false);
				attributePointTitles.get(typeIndex).setEditable(true);
				attributePointTitles.get(typeIndex).setText(type + ": " + slider.getValue()); 
				attributePointTitles.get(typeIndex).setEditable(false);

			}
		}
	}

	private class InitialSubmitButton extends JPanel {
		private JButton button;
		ButtonListener listener = new ButtonListener();

		public InitialSubmitButton() {
			this.button = new JButton("Submit");
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
				//Creates player according to user's decisions and starts a new game
				if (availablePoints == 0) {
					board.getPlayer().setAttributes(strengthSlider.getSlider().getValue(), enduranceSlider.getSlider().getValue(), defenseSlider.getSlider().getValue(), agilitySlider.getSlider().getValue(), perceptionSlider.getSlider().getValue(), intelligenceSlider.getSlider().getValue(), charismaSlider.getSlider().getValue());
					board.getPlayer().setName(userName.getText());
					board.getPlayer().addStartingItems();
					dungeonCrawler();
				}
				else {
					if (availablePoints > 0) {
						JOptionPane.showMessageDialog(getTopLevelAncestor(), "You still have points to spend!");
					} else {
						JOptionPane.showMessageDialog(getTopLevelAncestor(), "You've spent too many points!");
					}
				}
			}	
		}
	}

	private class PotionButton extends JPanel {
		private JButton button;
		ButtonListener listener = new ButtonListener();

		public PotionButton() {
			this.button = new JButton("Potions");
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
				//Lets user drink a potion
				PotionView potionView = new PotionView();
			}	
		}
	}

	private class SettingsButton extends JPanel {
		private JButton button;
		ButtonListener listener = new ButtonListener();

		public SettingsButton() {
			this.button = new JButton("Settings");
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
				//Displays settings
			}	
		}
	}

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
				
				PlayerView playerView = new PlayerView();
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
				//Changes player's armor
				ArmorView armorView = new ArmorView();
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
				//Changes Player's Weapon
				WeaponView weaponView = new WeaponView();
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
				
				InventoryView inventoryView = new InventoryView();
			}	
		}
	}

	private class StartButton extends JPanel{
		private JButton button;
		ButtonListener listener = new ButtonListener();

		public StartButton() {
			this.button = new JButton("Start New Game");
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
				newPlayer();
			}
		}
	}

	private class LoadGameButton extends JPanel{
		private JButton button;
		ButtonListener listener = new ButtonListener();

		public LoadGameButton() {
			this.button = new JButton("Load Game");
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

}