package dungeonCrawler;

import java.awt.Color;
import java.awt.Graphics;

public class Path extends BoardCell{

	public Path(int X, int Y) {
		super();
		icon = ' ';
		this.X = X;
		this.Y = Y;
		this.type = CellType.PATH;
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
	public void behavior() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics cell) {
		cell.setColor(Color.BLACK);
		cell.drawRect(X*15, Y*15, width, height);
		cell.setColor(Color.MAGENTA);
		cell.fillRect(X*15, Y*15, width - 1, height - 1);
	}

	
}
