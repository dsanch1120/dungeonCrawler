package dungeonCrawler;

public abstract class Ability {
	protected AbilityType type;
	protected String description;
	protected String name;
	
	public Ability() {
		
	}
	//Abstract Methods
	public abstract Integer behavior();
	//Getters and Setters
	public AbilityType getType() {
		return type;
	}
	public String getDescription() {
		return description;
	}
	public String getName() {
		return name;
	}
}
