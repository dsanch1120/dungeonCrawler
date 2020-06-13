package armor;

import dungeonCrawler.Armor;
import dungeonCrawler.ItemType;

public class BasicGarment extends Armor{

	public BasicGarment(String name, int defense) {
		super(name, defense);
		this.description = "Though it allows for slightly easier movement," 
				+ "\n" + "the basic garment does little more than protect"
				+ "\n" + "one from the embarrasment of fighting naked";
		this.weight = 2;
		this.type = ItemType.ARMOR;
	}

	public BasicGarment() {
		super();
		this.name = "Level " + board.getLevel() + " Basic Garment";
		this.defense = 0;
		this.description = "Though it allows for slightly easier movement," 
				+ "\n" + "the basic garment does little more than protect"
				+ "\n" + "one from the embarrasment of fighting naked";		
		this.weight = 2;
		this.type = ItemType.ARMOR;
	}
	
	@Override
	public void equip() {
		board.getPlayer().updateAGILITY(1);	
	}

	@Override
	public void unequip() {
		board.getPlayer().updateAGILITY(-1);
	}

	@Override
	public boolean spawn() {
		if (board.getLevel() > 2) {
			return false;
		} else {
			if ((rando.nextInt(100) + 1) <= 5) {
				return true;
			} else {
				return false;
			}
		}
	}

}
