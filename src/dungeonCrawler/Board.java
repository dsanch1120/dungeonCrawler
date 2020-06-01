/*
 * Created by Daniel Sanchez
 * May 25, 2020
 * Generates the "Board" of the game and handles similar functions
 */
package dungeonCrawler;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

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
	private final int MAX_HEIGHT = 60;
	private final int MAX_WIDTH = 100;
	private final int MAX_ROOM_SIZE = 25;
	private final int MIN_ROOM_SIZE = 8;
	private int NUM_ROOMS;
	private static Player player;
	private boolean newGame;

	//Methods
	public void init() {
		theInstance.level = -1;
	}

	//Plays the Game
	public void playGame() {
		generateBoard();
	}
	//Move the player
	private void move() {
	
	}

	//Handles a battle
	private boolean battle(Enemy enemy) {
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
		while (theInstance.player.getHP() > 0 && enemy.HP > 0) {
			System.out.println("Player Health: " + theInstance.player.getHP());
			System.out.println("Enemy Health: " + enemy.HP);
			System.out.println("Press anything to attack");
			String input = scan.nextLine();
			int playerAttack = theInstance.player.attack();
			int enemyAttack = enemy.attack();
			playerAttack -= enemy.defend();
			enemyAttack -= theInstance.player.defend();
			if (enemy.agilityRoll() < theInstance.player.agilityRoll()) {
				enemyAttack = 0;
			}
			if (theInstance.player.agilityRoll() < enemy.agilityRoll()) {
				playerAttack = 0;
			}
			enemy.HP -= playerAttack;
			theInstance.player.setHP(theInstance.player.getHP() - enemyAttack);
			if (enemy.HP < 1) {
				scan.close();
				return true;
			}
			if (theInstance.player.getHP() < 1) {
				scan.close();
				return false;
			}
		}
		scan.close();
		return false;
	}

	//Keeps the player from moving into a wall
	private boolean canMove(char dim, int movement) {
		switch (dim) {
		case 'x':
			return (!(theInstance.board[theInstance.player.getxCoordinate() + movement][theInstance.player.getyCoordinate()].getType() == CellType.BORDER));
		case 'y':
			return (!(theInstance.board[theInstance.player.getxCoordinate()][theInstance.player.getyCoordinate() + movement].getType() == CellType.BORDER));
		default:
			System.out.println("Error. This should not be happening");
			break;	
		}
		return false;
	}

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

		int xStair = rando.nextInt(MAX_HEIGHT - 10) + 5;
		int yStair = rando.nextInt(MAX_WIDTH - 10) + 5;
		NUM_ROOMS = rando.nextInt(6) + 4;

		//Generate the rooms
		generateRooms();

		//Place Borders
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (i == 0 || i == board.length - 1 || j == 0 || j == board[i].length - 1) {
					theInstance.board[i][j] = new Border(i, j);
				}
				else {
					theInstance.board[i][j] = new Border(i, j);
				}
			}
		}

		//Generate Corridors
		generateCorridors();

		//Place the Rooms and Corridors
		placeRooms();
		placeCorridors();

		//Place the Stairs
		Collections.shuffle(theInstance.rooms);
		theInstance.board[theInstance.rooms.get(0).getxStair()][theInstance.rooms.get(0).getyStair()] = new Stairs(theInstance.rooms.get(0).getxStair(), theInstance.rooms.get(0).getyStair());
		if (theInstance.level > 1) {
			theInstance.board[theInstance.rooms.get(1).getxStair()][theInstance.rooms.get(1).getyStair()] = new Stairs(theInstance.rooms.get(1).getxStair(), theInstance.rooms.get(1).getyStair());
			theInstance.player.moveX(theInstance.rooms.get(1).getxStair());
			theInstance.player.moveY(theInstance.rooms.get(1).getyStair());
		} else {
			player = new Player(theInstance.rooms.get(1).getxStair(), theInstance.rooms.get(1).getyStair());
		}
		theInstance.levels.add(theInstance.board);

		//Place the Enemies
		placeEnemies();
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
			for (int i = theInstance.rooms.get(var).getX1(); i < theInstance.rooms.get(var).getX2(); i++) {
				for (int j = theInstance.rooms.get(var).getY1(); j < theInstance.rooms.get(var).getY2(); j++) {
					if (j == theInstance.rooms.get(var).getY1() || j == theInstance.rooms.get(var).getY2() - 1 || i == theInstance.rooms.get(var).getX1() || i == theInstance.rooms.get(var).getX2() - 1) {
						theInstance.board[i][j] = new Border(i,j);
					} else {
						theInstance.board[i][j] = new Floor(i,j);
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
			}
		}
	}
	//Generates corridors to connect the rooms
	public void generateCorridors() {
		corridors = new ArrayList<Corridor>();

		for (int i = 0; i < theInstance.rooms.size(); i++) {
			if (i != theInstance.rooms.size() - 1) {
				theInstance.corridors.add(new Corridor(theInstance.rooms.get(i), theInstance.rooms.get(i + 1)));
			} else {
				theInstance.corridors.add(new Corridor(theInstance.rooms.get(i), theInstance.rooms.get(0)));
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
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				BoardCell boardCell = theInstance.board[i][j];
				boardCell.draw(cell);
			}
		}
		
		theInstance.player.draw(cell);
		
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(cell);
		}
	}
	
	private Board() {
		
	}
	
	//Getters and Setters
	
	
	public BoardCell[][] getBoardArray() {
		return theInstance.board;
	}
	public static Player getPlayer() {
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
