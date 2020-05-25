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
	public void behavior() {
		// TODO Auto-generated method stub
		
	}

}
