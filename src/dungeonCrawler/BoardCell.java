/*
 * Created by Daniel Sanchez
 * May 25, 2020
 * Each individual part of the board
 */
package dungeonCrawler;

import java.awt.Color;
import java.awt.Graphics;

public abstract class BoardCell {
	protected char icon;
	protected int X;
	protected int Y;
	protected final int width = 15;
	protected final int height = 15;
	protected CellType type;
	protected Enemy enemy;
	protected Player player;
	protected Color color;
	
	public CellType getType() {
		return type;
	}

	public abstract int behavior();

	public Color getColor() {
		return color;
	}
	
	public char getIcon() {
		return icon;
	}
	
	public abstract void draw(Graphics cell);
	public abstract boolean hasPlayer();
	public abstract boolean hasEnemy();
	
}
