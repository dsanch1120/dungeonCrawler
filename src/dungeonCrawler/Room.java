/*
 * Created by Daniel Sanchez
 * May 25, 2020
 * This class creates the procedurally generated rooms.
 */

package dungeonCrawler;

import java.util.Random;

public class Room {
	private int x1;
	private int x2;
	private int y1;
	private int y2;
	private int w;
	private int h;
	private int xStair;
	private int yStair;

	public Room(int x1, int y1, int w, int h) {
		super();
		
		Random rando = new Random();
		this.x1 = x1;
		this.y1 = y1;
		this.w = w;
		this.h = h;
		this.x2 = x1 + w;
		this.y2 = y1 + h;
		this.xStair = rando.nextInt(w - 2) + x1 + 1;
		this.yStair = rando.nextInt(h - 2) + y1 + 1;
	}
	
	public boolean intersects (Room room) {
		return (x1 <= room.getX2() && x2 >= room.getX1() && y1 <= room.getY2() && y2 >= room.getY1());
	}
	
	public int getxStair() {
		return xStair;
	}

	public int getyStair() {
		return yStair;
	}
	
	public int getX1() {
		return x1;
	}

	public int getX2() {
		return x2;
	}

	public int getY1() {
		return y1;
	}

	public int getY2() {
		return y2;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

}
