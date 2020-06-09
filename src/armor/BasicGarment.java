package armor;

import dungeonCrawler.Armor;

public class BasicGarment extends Armor{

	public BasicGarment(String name, int defense) {
		super(name, defense);
		this.description = "Though it allows for slightly easier movement, the basic garment does little more than protect one from the embarrasment of fighting naked";
	}

	@Override
	public void equip() {
		board.getPlayer().updateAGILITY(1);	
	}

	@Override
	public void unequip() {
		board.getPlayer().updateAGILITY(-1);
	}

}
