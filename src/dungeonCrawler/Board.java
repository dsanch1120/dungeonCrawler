/*
 * Created by Daniel Sanchez
 * May 25, 2020
 * Generates the "Board" of the game and handles similar functions
 */
package dungeonCrawler;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Board extends JPanel{
	//Variables to be used throughout the class
	private BoardCell[][] board;
	private int level;
	private static Board theInstance = new Board();
	ArrayList<Room> rooms;
	ArrayList<Corridor> corridors;
	ArrayList<Enemy> possibleEnemies;
	ArrayList<Enemy> enemies;
	ArrayList<BoardCell[][]> levels = new ArrayList<BoardCell[][]>();
	ArrayList<Integer> playerAttributes = new ArrayList<Integer>();
	Map<Integer, ArrayList<Room>> levelRooms = new HashMap<Integer, ArrayList<Room>>();
	private String playerName;
	private final int MAX_HEIGHT = 60;
	private final int MAX_WIDTH = 100;
	private final int MAX_ROOM_SIZE = 25;
	private final int MIN_ROOM_SIZE = 8;
	private int NUM_ROOMS;
	private static Player player;
	private boolean newGame;
	private BufferedImage image;

	//Methods
	public void init() {
		theInstance.level = -1;
		try {
			image = ImageIO.read(new File("data/Dungeon_Tileset.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Plays the Game
	public void playGame() {
		generateBoard();
	}
	
	//Checks the player's location after moving and handles any necessary events
	public void checkLocation() {
		//Check if player should battle an opponent
		if (theInstance.board[player.getxCoordinate()][player.getyCoordinate()].hasEnemy()) {
			//FIXME Add functionality for battle later
		}
		if (theInstance.board[player.getxCoordinate()][player.getyCoordinate()].getType() == CellType.STAIRS) {
			switch (theInstance.board[player.getxCoordinate()][player.getyCoordinate()].behavior()) {
			case (0):
				try {
					theInstance.level++;
					theInstance.board = theInstance.levels.get(theInstance.level);
					theInstance.player.setLocation(theInstance.levelRooms.get(theInstance.level).get(1).getxStair(), (theInstance.levelRooms.get(theInstance.level).get(1).getyStair()));
				} catch(NullPointerException e) {
					theInstance.level--;
					generateBoard();
				} catch(IndexOutOfBoundsException e) {
					theInstance.level--;
					generateBoard();
				}
				break;
			case (1):
				theInstance.level--;
				theInstance.board = levels.get(theInstance.level);
				theInstance.player.setLocation(levelRooms.get(theInstance.level).get(0).getxStair(), levelRooms.get(theInstance.level).get(0).getyStair());
				break;
			default:
				System.out.println("ERROR");
				System.exit(0);
			}
		}
	}

	//Handles switching of levels

	//Adds all possible enemies to the ArrayList "possibleEnemies"
	public void generatePossibleEnemies(int i, int j) {
		theInstance.possibleEnemies = new ArrayList<Enemy>();
		theInstance.possibleEnemies.add(new Skeleton(i, j));
		theInstance.possibleEnemies.add(new Spider(i, j));
	}
	//Handles the creation of the board by calling various methods
	public void generateBoard() {

		//Generates a new board whenever the method is called.
		theInstance.board = new BoardCell[MAX_WIDTH][MAX_HEIGHT];
		Random rando = new Random();

		theInstance.level++;

		NUM_ROOMS = rando.nextInt(6) + 4;

		//Generate the rooms
		generateRooms();
		theInstance.levelRooms.put(theInstance.level, theInstance.rooms);
		//Place Borders
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (i == 0 || i == board.length - 1 || j == 0 || j == board[i].length - 1) {
					theInstance.board[i][j] = new Border(i, j, 8, image);
				}
				else {
					theInstance.board[i][j] = new Border(i, j, 8, image);
				}
			}
		}

		//Generate Corridors
		generateCorridors();

		//Place the Rooms and Corridors
		//placeCorridors();
		placeRooms();
		placeCorridors();

		//Place the Stairs
		Collections.shuffle(theInstance.rooms);
		theInstance.board[theInstance.rooms.get(0).getxStair()][theInstance.rooms.get(0).getyStair()] = new Stairs(theInstance.rooms.get(0).getxStair(), theInstance.rooms.get(0).getyStair(), 0, image);
		if (theInstance.level > 0) {
			theInstance.board[theInstance.rooms.get(1).getxStair()][theInstance.rooms.get(1).getyStair()] = new Stairs(theInstance.rooms.get(1).getxStair(), theInstance.rooms.get(1).getyStair(), 1, image);
			theInstance.player.setLocation(theInstance.rooms.get(1).getxStair(), theInstance.rooms.get(1).getyStair());
		}
		player.setLocation(theInstance.rooms.get(1).getxStair(), theInstance.rooms.get(1).getyStair());


		//Place the Enemies
		placeEnemies();
		theInstance.levels.add(theInstance.board);
	}
	//Places the randomly genereated enemies
	public void placeEnemies() {
		enemies = new ArrayList<Enemy>();
		for (int i = 0; i < theInstance.board.length; i++) {
			for (int j = 0; j < theInstance.board[i].length; j++) {
				generatePossibleEnemies(i,j);
				Collections.shuffle(theInstance.possibleEnemies);
				if (theInstance.board[i][j].getType() != CellType.ROOM && theInstance.board[i][j].getType() != CellType.PATH) {
					continue;
				} else {
					for (int k = 0; k < theInstance.possibleEnemies.size(); k++) {
						if (theInstance.possibleEnemies.get(k).spawn()) {
							theInstance.board[i][j].enemy = theInstance.possibleEnemies.get(k);
							enemies.add(theInstance.board[i][j].enemy);
							break;
						}
					}
				}
			}
		}
	}
	//Places the already generated rooms
	public void placeRooms() {
		for (int var = 0; var < theInstance.rooms.size(); var++) {
			for (int i = theInstance.rooms.get(var).getX1() - 1; i <= theInstance.rooms.get(var).getX2(); i++) {
				for (int j = theInstance.rooms.get(var).getY1() - 1; j <= theInstance.rooms.get(var).getY2(); j++) {
					if (theInstance.board[i][j].getType() != CellType.PATH && (j == theInstance.rooms.get(var).getY1() - 1 || j == theInstance.rooms.get(var).getY2() || i == theInstance.rooms.get(var).getX1() - 1 || i == theInstance.rooms.get(var).getX2())) {
						if (j == theInstance.rooms.get(var).getY1() - 1 && i == theInstance.rooms.get(var).getX1() - 1) {
							theInstance.board[i][j] = new Border(i, j, 0, image);
						}
						else if (j == theInstance.rooms.get(var).getY1() - 1 && i == theInstance.rooms.get(var).getX2()) {
							theInstance.board[i][j] = new Border(i, j, 1, image);
						}
						else if (j == theInstance.rooms.get(var).getY2() && i == theInstance.rooms.get(var).getX1() - 1) {
							theInstance.board[i][j] = new Border(i, j, 2, image);
						}
						else if (j == theInstance.rooms.get(var).getY2() && i == theInstance.rooms.get(var).getX2()) {
							theInstance.board[i][j] = new Border(i, j, 3, image);
						}
						else if (j == theInstance.rooms.get(var).getY1() - 1) {
							theInstance.board[i][j] = new Border(i, j, 4, image);
						}
						else if (j == theInstance.rooms.get(var).getY2()) {
							theInstance.board[i][j] = new Border(i, j, 5, image);
						}
						else if (i == theInstance.rooms.get(var).getX1() - 1) {
							theInstance.board[i][j] = new Border(i, j, 6, image);
						}
						else if (i == theInstance.rooms.get(var).getX2()) {
							theInstance.board[i][j] = new Border(i, j, 7, image);
						} else {
							theInstance.board[i][j] = new Border(i, j, 8, image);
						}
					} else {
						theInstance.board[i][j] = new Floor(i,j, image);
					}
				}
			}
		}
	}
	//Places the already generated corridors
	public void placeCorridors() {
		for (int i = 0; i < theInstance.corridors.size(); i++) {
			for (int j = 0; j < theInstance.corridors.get(i).getCorridor().size(); j++) {
				theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X][theInstance.corridors.get(i).getCorridor().get(j).Y] = theInstance.corridors.get(i).getCorridor().get(j);
				if (theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X + 1][theInstance.corridors.get(i).getCorridor().get(j).Y].getType() == CellType.BORDER) {
					theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X + 1][theInstance.corridors.get(i).getCorridor().get(j).Y] = new Border(theInstance.corridors.get(i).getCorridor().get(j).X + 1, theInstance.corridors.get(i).getCorridor().get(j).Y, 7, image);
					theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X + 1][theInstance.corridors.get(i).getCorridor().get(j).Y].setEditable(false);
				} 
				if (theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X - 1][theInstance.corridors.get(i).getCorridor().get(j).Y].getType() == CellType.BORDER) {
					theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X - 1][theInstance.corridors.get(i).getCorridor().get(j).Y] = new Border(theInstance.corridors.get(i).getCorridor().get(j).X - 1, theInstance.corridors.get(i).getCorridor().get(j).Y, 6, image);
					theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X - 1][theInstance.corridors.get(i).getCorridor().get(j).Y].setEditable(false);
				}
				if (theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X][theInstance.corridors.get(i).getCorridor().get(j).Y + 1].getType() == CellType.BORDER) {
					theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X][theInstance.corridors.get(i).getCorridor().get(j).Y + 1] = new Border(theInstance.corridors.get(i).getCorridor().get(j).X, theInstance.corridors.get(i).getCorridor().get(j).Y + 1, 5, image);	
					theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X][theInstance.corridors.get(i).getCorridor().get(j).Y + 1].setEditable(false);
				}
				if (theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X][theInstance.corridors.get(i).getCorridor().get(j).Y - 1].getType() == CellType.BORDER) {
					theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X][theInstance.corridors.get(i).getCorridor().get(j).Y - 1] = new Border(theInstance.corridors.get(i).getCorridor().get(j).X, theInstance.corridors.get(i).getCorridor().get(j).Y - 1, 4, image);	
					theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X][theInstance.corridors.get(i).getCorridor().get(j).Y - 1].setEditable(false);
				}
				if (theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X - 1][theInstance.corridors.get(i).getCorridor().get(j).Y - 1].getType() == CellType.BORDER && theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X - 1][theInstance.corridors.get(i).getCorridor().get(j).Y - 1].isEditable()) {
					theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X - 1][theInstance.corridors.get(i).getCorridor().get(j).Y - 1] = new Border(theInstance.corridors.get(i).getCorridor().get(j).X - 1, theInstance.corridors.get(i).getCorridor().get(j).Y - 1, 0, image);	
					theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X - 1][theInstance.corridors.get(i).getCorridor().get(j).Y - 1].setEditable(false);
				}
				if (theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X + 1][theInstance.corridors.get(i).getCorridor().get(j).Y - 1].getType() == CellType.BORDER && theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X + 1][theInstance.corridors.get(i).getCorridor().get(j).Y - 1].isEditable()) {
					theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X + 1][theInstance.corridors.get(i).getCorridor().get(j).Y - 1] = new Border(theInstance.corridors.get(i).getCorridor().get(j).X + 1, theInstance.corridors.get(i).getCorridor().get(j).Y - 1, 1, image);	
					theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X + 1][theInstance.corridors.get(i).getCorridor().get(j).Y - 1].setEditable(false);
				}
				if (theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X - 1][theInstance.corridors.get(i).getCorridor().get(j).Y + 1].getType() == CellType.BORDER && theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X - 1][theInstance.corridors.get(i).getCorridor().get(j).Y + 1].isEditable()) {
					theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X - 1][theInstance.corridors.get(i).getCorridor().get(j).Y + 1] = new Border(theInstance.corridors.get(i).getCorridor().get(j).X - 1, theInstance.corridors.get(i).getCorridor().get(j).Y + 1, 2, image);
					theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X - 1][theInstance.corridors.get(i).getCorridor().get(j).Y + 1].setEditable(false);
				}
				if (theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X + 1][theInstance.corridors.get(i).getCorridor().get(j).Y + 1].getType() == CellType.BORDER && theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X + 1][theInstance.corridors.get(i).getCorridor().get(j).Y + 1].isEditable()) {
					theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X + 1][theInstance.corridors.get(i).getCorridor().get(j).Y + 1] = new Border(theInstance.corridors.get(i).getCorridor().get(j).X + 1, theInstance.corridors.get(i).getCorridor().get(j).Y + 1, 3, image);	
					theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).X + 1][theInstance.corridors.get(i).getCorridor().get(j).Y + 1].setEditable(false);
				}
			}
		}
	}
	//Generates corridors to connect the rooms
	public void generateCorridors() {
		corridors = new ArrayList<Corridor>();

		for (int i = 0; i < theInstance.rooms.size(); i++) {
			if (i != theInstance.rooms.size() - 1) {
				theInstance.corridors.add(new Corridor(theInstance.rooms.get(i), theInstance.rooms.get(i + 1), image));
			} else {
				theInstance.corridors.add(new Corridor(theInstance.rooms.get(i), theInstance.rooms.get(0), image));
			}
		}
	}
	//Generates a list of rooms, destroys a newly created room if it intersects with an already created one
	public void generateRooms() {
		theInstance.rooms = new ArrayList<Room>();
		Random rando = new Random();
		for (int i = 0; i < NUM_ROOMS; i++) {
			int w = rando.nextInt(MAX_ROOM_SIZE - MIN_ROOM_SIZE + 1) + MIN_ROOM_SIZE;
			int h = rando.nextInt(MAX_ROOM_SIZE - MIN_ROOM_SIZE + 1) + MIN_ROOM_SIZE;
			int x = rando.nextInt(MAX_WIDTH - w - 1) + 1;
			int y = rando.nextInt(MAX_HEIGHT - h - 1) + 1;


			Room newRoom = new Room(x, y, w, h);
			boolean failed = false;
			for (Room otherRoom : theInstance.rooms) {
				if (newRoom.intersects(otherRoom)) {
					failed = true;
					break;
				}
			}
			if (!failed) {
				theInstance.rooms.add(newRoom);
			}
		}
	}
	//Paints the board
	public void paintComponent(Graphics cell) {
		super.paintComponent(cell);
		BoardCell[][] tempBoard = theInstance.levels.get(theInstance.level);
		for (int i = 0; i < tempBoard.length; i++) {
			for (int j = 0; j < tempBoard[i].length; j++) {
				BoardCell boardCell = tempBoard[i][j];
				boardCell.draw(cell);
			}
		}

		theInstance.player.draw(cell);

	}

	private Board() {

	}

	//Getters and Setters


	public BoardCell[][] getBoardArray() {
		return theInstance.board;
	}
	public static Player getPlayer() {
		if (Board.player == null) {
			player = new Player();
		}
		return Board.player;
	}

	public int getLevel() {
		return theInstance.level;
	}

	public boolean isNewGame() {
		return newGame;
	}

	public void setNewGame(boolean newGame) {
		this.newGame = newGame;
	}

	public static Board getBoard() {
		return theInstance;
	}

	public ArrayList<Room> getRooms() {
		return theInstance.rooms;
	}
}
