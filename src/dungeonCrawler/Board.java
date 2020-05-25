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
	private final int MAX_HEIGHT = 50;
	private final int MAX_WIDTH = 150;
	private final int MAX_ROOM_SIZE = 25;
	private final int MIN_ROOM_SIZE = 8;
	private int NUM_ROOMS;
	
	//Methods
	public void generateBoard() {
		/*
		 * Plan for generating board
		 * Within an embedded for loop
		 */
		//Generates a new board whenever the method is called.
		theInstance.board = new BoardCell[MAX_HEIGHT][MAX_WIDTH];
		Random rando = new Random();
		
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
					theInstance.board[i][j] = new Floor(i, j);
				}
			}
		}
		
		//Place the Rooms
		placeRooms();
		
		//Place the Stairs
		Collections.shuffle(theInstance.rooms);
		theInstance.board[theInstance.rooms.get(0).getyStair()][theInstance.rooms.get(0).getxStair()] = new Stairs(theInstance.rooms.get(0).getyStair(), theInstance.rooms.get(0).getxStair());
		theInstance.board[theInstance.rooms.get(1).getyStair()][theInstance.rooms.get(1).getxStair()] = new Stairs(theInstance.rooms.get(1).getyStair(), theInstance.rooms.get(1).getxStair());
		
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
				System.out.print(theInstance.board[i][j].getIcon());
			}
			System.out.println();
		}
	}
	
	public static Board getBoard() {
		return theInstance;
	}
	
	public static void main(String[] args) {
		Board instance = Board.getBoard();
		instance.generateBoard();
		instance.displayBoard();
	}
}
