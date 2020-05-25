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
	
	public CellType getType() {
		return type;
	}

	protected abstract void behavior();

	public char getIcon() {
		return icon;
	}
	
}
