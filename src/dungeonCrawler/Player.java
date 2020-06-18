/*
 * Created by Daniel Sanchez
 * May 25, 2020
 * This class is for the player.
 */
package dungeonCrawler;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import armor.BasicGarment;
import potions.BasicHealthPotion;
import weapons.Sword;

public class Player {
	//Variables
	private Board board;
	private String name;
	private int ENDURANCE;
	private int STRENGTH;
	private int DEFENSE;
	private int AGILITY;
	private int PERCEPTION;
	private int INTELLIGENCE;
	private int CHARISMA;
	private int XP;
	private int xpThreshold;
	private int maxHP;
	private int HP;
	private int xCoordinate;
	private int yCoordinate;
	private final int WIDTH = 15;
	private final int HEIGHT = 15;
	private Purse purse;
	private Set<Item> inventory;
	private ArrayList<Weapon> weapons;
	private ArrayList<Armor> armor;
	private ArrayList<Potion> potions;
	private int level;
	private final char ICON = 'X';
	private BufferedImage image;
	private Weapon equippedWeapon;
	private Armor equippedArmor;

	//Constructor
	public Player() {
		//Allocates memory for item related arraylists
		this.inventory = new HashSet<Item>();
		this.weapons = new ArrayList<Weapon>();
		this.armor = new ArrayList<Armor>();
		this.potions = new ArrayList<Potion>();
		//Gets the current instance of board
		this.board = Board.getBoard();
		//Initializes purse with 0 gold
		this.purse = new Purse(0);
		//Initializes XP with 0
		this.XP = 0;
		this.xpThreshold = 25;
		//Loads player image
		try {
			image = ImageIO.read(new File("data/player.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		image = image.getSubimage(69, 89, 60, 70);
	}

	//Methods

	//Checks the player can level up and increases their level
	public void levelUp() {
		this.xpThreshold += xpThreshold * (board.getLevel() - 1);
		LevelUpView luView = new LevelUpView();
	}
	//Adds the starting items
	public void addStartingItems() {
		armor.add(new BasicGarment());
		inventory.add(armor.get(0));
		weapons.add(new Sword());
		inventory.add(weapons.get(0));
		potions.add(new BasicHealthPotion());
		inventory.add(potions.get(0));

		equippedWeapon = weapons.get(0);
		equippedArmor = armor.get(0);
	}
	//Equips the items
	public void equipItem(Item item) {
		if (item.getType() == ItemType.WEAPON) {
			this.equippedWeapon = (Weapon) item;
		}
		if (item.getType() == ItemType.ARMOR) {
			this.equippedArmor = (Armor) item;
		}
	}
	//Unequips the items
	public void unequipItem(Item item) {
		if (item.getType() == ItemType.WEAPON) {
			this.equippedWeapon = null;
		}
		if (item.getType() == ItemType.ARMOR) {
			this.equippedArmor = null;
		}
	}
	//Updates the inventory
	public void updateInventory() {
		this.inventory = new HashSet<Item>();
		for (int i = 0; i < this.armor.size(); i++) {
			this.inventory.add(armor.get(i));
		}
		for (int i = 0; i < this.weapons.size(); i++) {
			this.inventory.add(weapons.get(i));
		}
		for (int i = 0; i < this.potions.size(); i++) {
			this.inventory.add(potions.get(i));
		}
	}
	//Handles adding an item
	public void addItem(Item item) {
		this.inventory.add(item);
		if (item.getType() == ItemType.POTION) {
			this.potions.add((Potion) item);
		} else if (item.getType() == ItemType.ARMOR) {
			this.armor.add((Armor) item);
		} else if (item.getType() == ItemType.WEAPON){
			this.weapons.add((Weapon) item);
		} else {
			System.out.println("ERROR");
		}
	}
	//Handles the player's attacks
	public Integer attack() {
		//This needs to be further "flushed out"
		Random rando = new Random();
		if (this.equippedWeapon == null) {
			return rando.nextInt(this.STRENGTH) + 1;
		} else {
			return rando.nextInt(this.STRENGTH) + 1 + equippedWeapon.damage;
		}

	}
	//Handles the player's defense
	public Integer defend() {
		return this.DEFENSE;
	}
	//"Rolls" the player's agility during a battle
	public Integer agilityRoll() {
		Random rando = new Random();
		return rando.nextInt(this.AGILITY) + 1;
	}

	private BufferedImage resizeImage(BufferedImage originalImage, int width, int height, int type) {  
		BufferedImage resizedImage = new BufferedImage(width, height, type);  
		Graphics2D g = resizedImage.createGraphics();  
		g.drawImage(originalImage, 0, 0, width, height, null);  
		g.dispose();  
		return resizedImage;  
	}  

	//Draws the player on the board
	public void draw(Graphics cell) {
		image = resizeImage(image, 16, 16, 2);
		cell.drawImage(image, xCoordinate * 15, yCoordinate * 15, null);
	}
	//Checks if the player can move
	public boolean canMove(int movement, char direction) {
		if (direction == 'x' && board.getBoardArray()[xCoordinate + movement][yCoordinate].getType() == CellType.BORDER) {
			return false;
		} else if (direction == 'y' && board.getBoardArray()[xCoordinate][yCoordinate + movement].getType() == CellType.BORDER) {
			return false;
		} else {
			return true;
		}
	}

	//Getters and Setters
	public void updateXP(int XP) {
		this.XP += XP;
		if (this.XP >= this.xpThreshold) {
			levelUp();
		}
	}

	public Item getEquippedWeapon() {
		return this.equippedWeapon;
	}

	public void setEquippedWeapon(Weapon equippedWeapon) {
		this.equippedWeapon = equippedWeapon;
	}

	public void setEquippedArmor(Armor equippedArmor) {
		this.equippedArmor = equippedArmor;
	}

	public Item getEquippedArmor() {
		return this.equippedArmor;
	}

	public int getVisibility() {
		return PERCEPTION * 5;
	}

	public Set<Item> getInventory() {
		return inventory;
	}

	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}

	public ArrayList<Armor> getArmor() {
		return armor;
	}

	public ArrayList<Potion> getPotions() {
		return potions;
	}

	public char getIcon() {
		return ICON;
	}

	public void setLocation(int xCoordinate, int yCoordinate) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}

	public void setAttributes(int STRENGTH, int ENDURANCE, int DEFENSE, int AGILITY, int PERCEPTION, int INTELLIGENCE, int CHARISMA) {
		this.STRENGTH = STRENGTH;
		this.ENDURANCE = ENDURANCE;
		this.DEFENSE = DEFENSE;
		this.AGILITY = AGILITY;
		this.PERCEPTION = PERCEPTION;
		this.INTELLIGENCE = INTELLIGENCE;
		this.CHARISMA = CHARISMA;
		this.maxHP = ENDURANCE * 3;
		this.HP = this.maxHP;

	}

	public int getXP() {
		return this.XP;
	}

	public String getName() {
		return this.name;
	}

	public int getGold() {
		return purse.behavior();
	}

	public int getxCoordinate() {
		return xCoordinate;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHP() {
		return HP;
	}


	public int getyCoordinate() {
		return yCoordinate;
	}


	public int getCHARISMA() {
		return CHARISMA;
	}

	public int getENDURANCE() {
		return ENDURANCE;
	}

	public int getSTRENGTH() {
		return STRENGTH;
	}

	public int getDEFENSE() {
		return DEFENSE;
	}

	public int getAGILITY() {
		return AGILITY;
	}

	public int getPERCEPTION() {
		return PERCEPTION;
	}

	public int getINTELLIGENCE() {
		return INTELLIGENCE;
	}

	public void updateENDURANCE(int eNDURANCE) {
		ENDURANCE += eNDURANCE;
	}

	public void updateSTRENGTH(int sTRENGTH) {
		STRENGTH += sTRENGTH;
	}

	public void updateDEFENSE(int dEFENSE) {
		DEFENSE += dEFENSE;
	}

	public void updateAGILITY(int aGILITY) {
		AGILITY += aGILITY;
	}

	public void updatePERCEPTION(int pERCEPTION) {
		PERCEPTION += pERCEPTION;
	}

	public void updateINTELLIGENCE(int iNTELLIGENCE) {
		INTELLIGENCE += iNTELLIGENCE;
	}

	public void updateCHARISMA(int cHARISMA) {
		CHARISMA += cHARISMA;
	}

	public void updateHealth(int health) {
		if (this.HP + health <= this.maxHP) {
			this.HP += health;
		} else {
			this.HP = this.maxHP;
		}
	}

	public void moveX(int xCoordinate) {
		if (this.canMove(xCoordinate, 'x')) {
			this.xCoordinate += xCoordinate;
		}
	}


	public void moveY(int yCoordinate) {
		if (this.canMove(yCoordinate, 'y')) {
			this.yCoordinate += yCoordinate;
		}
	}


}
