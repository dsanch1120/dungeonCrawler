/*
 * Created by Daniel Sanchez
 * May 25, 2020
 * Generates the "Board" of the game and handles similar functions
 */
package dungeonCrawler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

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
		theInstance.level = 0;
		generatePossibleEnemies();
		generateBoard();
		displayBoard();
	}
	//Adds all possible enemies to the ArrayList "possibleEnemies"
	public void generatePossibleEnemies() {
		theInstance.possibleEnemies = new ArrayList<Enemy>();
		theInstance.possibleEnemies.add(new Skeleton(-1, -1));
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
				if (theInstance.board[i][j].getType() != CellType.ROOM && theInstance.board[i][j].getType() != CellType.PATH) {
					continue;
				} else {
					for (int k = 0; k < theInstance.possibleEnemies.size(); k++) {
						if (theInstance.possibleEnemies.get(k).spawn()) {
							switch (k) {
							case 0:
								theInstance.board[i][j].enemy = new Skeleton(i,j);
								break;
							default:
								break;
							}
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

	public static void main(String[] args) {
		Board instance = Board.getBoard();
		instance.init();
	}
}
