/*
 * Created by Daniel Sanchez
 * May 25, 2020
 * Abstract class for the various items throughout the game.
 */
package dungeonCrawler;

public abstract class Item {
	//Variables
	protected String name;
	protected char icon;
	protected Board board = Board.getBoard();
	protected ItemType type;
	
	//Methods
	public abstract int behavior();
}
