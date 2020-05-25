package dungeonCrawler;

public class Border extends BoardCell{

	
	
	public Border(int X, int Y) {
		super();
		icon = '#';
		this.X = X;
		this.Y = Y;
		this.type = CellType.BORDER;
	}

	@Override
	protected void behavior() {
		// TODO Auto-generated method stub
		
	}

}
