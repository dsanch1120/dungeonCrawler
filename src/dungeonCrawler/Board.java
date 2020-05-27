/*
 * Created by Daniel Sanchez
 * May 25, 2020
 * Generates the "Board" of the game and handles similar functions
 */
package dungeonCrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Board {
	//Variables to be used throughout the class
	private BoardCell[][] board;
	private int level;
	private static Board theInstance = new Board();
	ArrayList<Room> rooms;
	ArrayList<Corridor> corridors;
	ArrayList<Enemy> possibleEnemies;
	ArrayList<BoardCell[][]> levels = new ArrayList<BoardCell[][]>();
	private final int MAX_HEIGHT = 50;
	private final int MAX_WIDTH = 150;
	private final int MAX_ROOM_SIZE = 25;
	private final int MIN_ROOM_SIZE = 8;
	private int NUM_ROOMS;
	private Player player;

	//Methods

	public void init() {
		theInstance.level = -1;
		generateBoard();
	}

	//Plays the Game
	public void playGame() {
		while (true) {
			clearTerminal();
			generateBoard();
			displayBoard();
			raw();
			while (true) {
				cook();
				raw();
				boolean battleHappened = false;
				if (theInstance.board[theInstance.player.getxCoordinate()][theInstance.player.getyCoordinate()].hasEnemy()) {
					if (battle(theInstance.board[theInstance.player.getxCoordinate()][theInstance.player.getyCoordinate()].enemy)) {
						System.out.println("You have won the battle");
						theInstance.board[theInstance.player.getxCoordinate()][theInstance.player.getyCoordinate()].enemy = null;
						battleHappened = true;
						continue;
					} else {
						System.out.println("You have lost the battle");
						battleHappened = true;
						break;
					}
				}
				if (!battleHappened) {
					try {
						char tmp = (char) System.in.read();
						theInstance.board[theInstance.player.getxCoordinate()][theInstance.player.getyCoordinate()].player = null;
						move(tmp);
						theInstance.board[theInstance.player.getxCoordinate()][theInstance.player.getyCoordinate()].player = theInstance.player;
						if (tmp == 'q') {
							break;
						} else {
							clearTerminal();
							displayBoard();
							continue;
						}
					} catch (IOException e1) {
						System.out.println("IO Exception");
					}
				}
//				clearTerminal();
//				displayBoard();
			}
			cook();
			break;
		}
		cook();
	}
	//Move the player
	private void move(char tmp) {
		if (tmp == 'w') {
			if (canMove('x', -1)) {
				theInstance.player.moveX(theInstance.player.getxCoordinate() - 1);
			}
		}
		if (tmp == 'a') {
			if (canMove('y', -1)) {
				theInstance.player.moveY(theInstance.player.getyCoordinate() - 1);
			}
		}
		if (tmp == 's') {
			if (canMove('x', 1)) {
				theInstance.player.moveX(theInstance.player.getxCoordinate() + 1);
			}
		}
		if (tmp == 'd') {
			if (canMove('y', 1)) {
				theInstance.player.moveY(theInstance.player.getyCoordinate() + 1);
			}
		}
	}

	//Makes terminal "raw"
	private void raw() {
		String[] raw = {"/bin/sh", "-c", "stty raw </dev/tty"};
		try {
			Runtime.getRuntime().exec(raw).waitFor();
		} catch (InterruptedException e) {
			System.out.println("Interrupted Exception");
		} catch (IOException e) {
			System.out.println("IO Exception");
		}
	}

	//Makes terminal "cooked"
	private void cook() {
		String[] cooked = {"/bin/sh", "-c", "stty cooked </dev/tty"};
		try {
			Runtime.getRuntime().exec(cooked).waitFor();
		} catch (InterruptedException e) {
			System.out.println("Interrupted Exception");
		} catch (IOException e) {
			System.out.println("IO Exception");
		}
	}

	//Handles a battle
	private boolean battle(Enemy enemy) {
		cook();
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
		while (theInstance.player.getHP() > 0 && enemy.HP > 0) {
			//*UNCOMMENT THIS FOR JAR FILE*
			//clearTerminal();
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
				raw();
				return true;
			}
			if (theInstance.player.getHP() < 1) {
				scan.close();
				raw();
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

	//Clears terminal
	private void clearTerminal() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	//Adds all possible enemies to the ArrayList "possibleEnemies"
	public void generatePossibleEnemies(int i, int j) {
		theInstance.possibleEnemies = new ArrayList<Enemy>();
		theInstance.possibleEnemies.add(new Skeleton(i, j));
		theInstance.possibleEnemies.add(new Spider(i, j));
	}

	public void generateBoard() {
		/*
		 * Plan for generating board
		 * Within an embedded for loop
		 */
		//Generates a new board whenever the method is called.
		theInstance.board = new BoardCell[MAX_HEIGHT][MAX_WIDTH];
		Random rando = new Random();

		theInstance.level++;

		int xStair = rando.nextInt(MAX_HEIGHT - 10) + 5;
		int yStair = rando.nextInt(MAX_WIDTH - 10) + 5;
		NUM_ROOMS = rando.nextInt(6) + 4;

		//Generate the rooms
		generateRooms();

		//Place Borders
		for (int i = 0; i < MAX_HEIGHT; i++) {
			for (int j = 0; j < MAX_WIDTH; j++) {
				if (i == 0 || i == MAX_HEIGHT - 1 || j == 0 || j == MAX_WIDTH - 1) {
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
		theInstance.board[theInstance.rooms.get(0).getyStair()][theInstance.rooms.get(0).getxStair()] = new Stairs(theInstance.rooms.get(0).getyStair(), theInstance.rooms.get(0).getxStair());
		if (theInstance.level > 1) {
			theInstance.board[theInstance.rooms.get(1).getyStair()][theInstance.rooms.get(1).getxStair()] = new Stairs(theInstance.rooms.get(1).getyStair(), theInstance.rooms.get(1).getxStair());
			theInstance.player.moveY(theInstance.rooms.get(1).getxStair());
			theInstance.player.moveX(theInstance.rooms.get(1).getyStair());
		} else {
			player = new Player(theInstance.rooms.get(1).getyStair(), theInstance.rooms.get(1).getxStair());
		}
		theInstance.levels.add(theInstance.board);

		//Place the Enemies
		placeEnemies();
	}

	public void placeEnemies() {
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
							break;
						}
					}
				}
			}
		}
	}

	public void placeRooms() {
		for (int var = 0; var < theInstance.rooms.size(); var++) {
			for (int i = theInstance.rooms.get(var).getY1(); i < theInstance.rooms.get(var).getY2(); i++) {
				for (int j = theInstance.rooms.get(var).getX1(); j < theInstance.rooms.get(var).getX2(); j++) {
					if (i == theInstance.rooms.get(var).getY1() || i == theInstance.rooms.get(var).getY2() - 1 || j == theInstance.rooms.get(var).getX1() || j == theInstance.rooms.get(var).getX2() - 1) {
						theInstance.board[i][j] = new Border(i,j);
					} else {
						theInstance.board[i][j] = new Floor(i,j);
					}
				}
			}
		}
	}

	public void placeCorridors() {
		for (int i = 0; i < theInstance.corridors.size(); i++) {
			for (int j = 0; j < theInstance.corridors.get(i).getCorridor().size(); j++) {
				theInstance.board[theInstance.corridors.get(i).getCorridor().get(j).Y][theInstance.corridors.get(i).getCorridor().get(j).X] = theInstance.corridors.get(i).getCorridor().get(j);
			}
		}
	}

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

	public void displayBoard() {
		for (int i = 0; i < theInstance.board.length; i++) {
			for (int j = 0; j < theInstance.board[i].length; j++) {
				if (theInstance.player.getxCoordinate() == i && theInstance.player.getyCoordinate() == j) {
					System.out.print(theInstance.player.getIcon());
				} else if (theInstance.board[i][j].hasEnemy()) {
					System.out.print(theInstance.board[i][j].enemy.getIcon());
				} else {
					System.out.print(theInstance.board[i][j].getIcon());
				}
			}
			System.out.println();
		}
	}

	public int getLevel() {
		return theInstance.level;
	}

	public static Board getBoard() {
		return theInstance;
	}

	public ArrayList<Room> getRooms() {
		return theInstance.rooms;
	}
}
