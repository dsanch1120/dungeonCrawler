package dungeonCrawler;

import java.awt.Color;
import java.awt.Graphics;

public class Floor extends BoardCell{

	public Floor(int X, int Y) {
		super();
		icon = ' ';
		this.X = X;
		this.Y = Y;
		this.type = CellType.ROOM;
	}

	@Override
	public boolean hasPlayer() {
		return(!(player == null));
	}

	@Override
	public boolean hasEnemy() {
		return (!(enemy == null));
	}

	@Override
	public int behavior() {
		return -1;	
	}

	@Override
	public void draw(Graphics cell) {
		if (this.hasEnemy()) {
			enemy.draw(cell);
		} else {
			cell.setColor(Color.BLACK);
			cell.drawRect(X*15, Y*15, width, height);
			cell.setColor(Color.LIGHT_GRAY);
			cell.fillRect(X*15, Y*15, width - 1, height - 1);
		}
	}

}
