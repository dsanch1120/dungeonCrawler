package dungeonCrawler;

import java.awt.Color;
import java.awt.Graphics;

public class Border extends BoardCell{

	public Border(int X, int Y) {
		super();
		icon = '+';
		this.X = X;
		this.Y = Y;
		this.type = CellType.BORDER;
	}

	@Override
	public boolean hasPlayer() {
		return false;
	}

	@Override
	public boolean hasEnemy() {
		return false;
	}

	@Override
	public int behavior() {
		return -1;		
	}

	@Override
	public void draw(Graphics cell) {
		cell.setColor(Color.BLACK);
		cell.drawRect(X*15, Y*15, width, height);
		cell.setColor(Color.DARK_GRAY);
		cell.fillRect(X*15, Y*15, width-1, height-1);
	}


}
