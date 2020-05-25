package dungeonCrawler;

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
	public void behavior() {
		// TODO Auto-generated method stub
		
	}

}
