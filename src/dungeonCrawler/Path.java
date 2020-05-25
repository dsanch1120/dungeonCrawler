package dungeonCrawler;

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

}
