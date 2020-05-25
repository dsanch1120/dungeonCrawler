/*
 * Created by Daniel Sanchez
 * May 25, 2020
 * Creates Corridor object that allow the player to traverse through the different rooms
 */
package dungeonCrawler;

import java.util.ArrayList;

public class Corridor {
	//Variables to be used throughout
	private ArrayList<Path> corridor;
	private Room room1;
	private Room room2;
	private ArrayList<Path> vPath;
	private Board board = Board.getBoard();
	private int x;
	private int y;
	
	public Corridor(Room room1, Room room2) {
		corridor = new ArrayList<Path>();
		generateHPath(room1);
		generateVPath(room2);
		this.room1 = room1;
		this.room2 = room2;
		generatePath();
	}
	
	//Methods
	private void generateHPath(Room room) {
		x = room.getxStair();
	}
	
	private void generateVPath(Room room) {
		y = room.getyStair();
	}
	
	private void generatePath() {
		//Adds the corner to the corridor
		corridor.add(new Path(x, y));
		
		//Adds the horizontal to the corridor
		if (this.x > room2.getxStair()) {
			for (int i = room2.getxStair(); i < this.x; i++) {
				corridor.add(new Path(i, y));
			}
		} else {
			for (int i = this.x; i < room2.getxStair(); i++) {
				corridor.add(new Path(i, y));
			}
		}
		
		//Adds the vertical to the corridor
		if (this.y > room1.getyStair()) {
			for (int i = room1.getyStair(); i < this.y; i++) {
				corridor.add(new Path(x, i));
			}
		} else {
			for (int i = this.y; i < room1.getyStair(); i++) {
				corridor.add(new Path(x, i));
			}
		}
		
	}

	public ArrayList<Path> getCorridor() {
		return corridor;
	}
	
	
}
