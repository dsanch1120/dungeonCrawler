package dungeonCrawler;

public class Path extends BoardCell{

	public Path(int X, int Y) {
		super();
		icon = '#';
		this.X = X;
		this.Y = Y;
		this.type = CellType.PATH;
	}
	
	@Override
	protected void behavior() {
		// TODO Auto-generated method stub
		
	}

}
