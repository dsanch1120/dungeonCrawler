/*
 * Created by Daniel Sanchez
 * May 25, 2020
 * Each individual part of the board
 */
package dungeonCrawler;

public abstract class BoardCell {
	protected char icon;
	protected int X;
	protected int Y;
	protected CellType type;
	protected Enemy enemy;
	protected Player player;
	
	public CellType getType() {
		return type;
	}

	public abstract void behavior();

	public char getIcon() {
		return icon;
	}
	
	public abstract boolean hasPlayer();
	public abstract boolean hasEnemy();
	
}
