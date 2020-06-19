package abilities;

import dungeonCrawler.Ability;
import dungeonCrawler.AbilityType;
import dungeonCrawler.Board;

public class BasicFireball extends Ability{

	public BasicFireball() {
		this.type = AbilityType.ATTACK;
		this.description = "Conjure a small fireball that does minimal damage";
	}
	
	@Override
	public Integer behavior() {
		return (1 * (Board.getPlayer().getINTELLIGENCE() / 2));
	}

}
