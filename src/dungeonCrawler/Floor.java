package dungeonCrawler;

public class Floor extends BoardCell{

	public Floor(int X, int Y) {
		super();
		icon = ' ';
		this.X = X;
		this.Y = Y;
		this.type = CellType.ROOM;
	}
	
	@Override
	protected void behavior() {
		// TODO Auto-generated method stub
		
	}

}
