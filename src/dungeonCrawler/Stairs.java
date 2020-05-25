package dungeonCrawler;

public class Stairs extends BoardCell{

	public Stairs(int X, int Y) {
		super();
		icon = '%';
		this.X = X;
		this.Y = Y;
		this.type = CellType.STAIRS;
	}
	
	@Override
	protected void behavior() {
		// TODO Auto-generated method stub
		
	}

}
