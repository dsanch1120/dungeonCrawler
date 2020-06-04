package dungeonCrawler;

import java.awt.Color;
import java.awt.Graphics;

public class Stairs extends BoardCell{
	private int stairType;
	
	public Stairs(int X, int Y, int stairType) {
		super();
		icon = '%';
		this.X = X;
		this.Y = Y;
		this.type = CellType.STAIRS;
		this.stairType = stairType;
	}
	
	@Override
	public boolean hasPlayer() {
		//FIXME
		//Stair should be able to have the player, but not an enemy.
		return false;
	}
	
	@Override
	public boolean hasEnemy() {
		return false;
	}
	
	@Override
	public int behavior() {
		return stairType;
	}

	@Override
	public void draw(Graphics cell) {
		// TODO Auto-generated method stub
		cell.setColor(Color.BLACK);
		cell.drawRect(X*15, Y*15, width, height);
		cell.setColor(Color.CYAN);
		cell.fillRect(X*15, Y*15, width - 1, height - 1);
//		cell.setColor(Color.BLACK);
//		cell.drawString("%", X*15, Y*15);
	}

}
