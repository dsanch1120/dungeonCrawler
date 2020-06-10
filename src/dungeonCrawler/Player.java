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
import java.util.Random;

import javax.imageio.ImageIO;

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
	private int maxHP;
	private int HP;
	private int xCoordinate;
	private int yCoordinate;
	private final int WIDTH = 15;
	private final int HEIGHT = 15;
	private Purse purse;
	private ArrayList<Item> inventory;
	private ArrayList<Weapon> weapons;
	private ArrayList<Armor> armor;
	private ArrayList<Potion> potions;
	private int level;
	private final char ICON = 'X';
	private BufferedImage image;

	//Constructor
	public Player() {
		//Allocates memory for item related arraylists
		this.inventory = new ArrayList<Item>();
		this.weapons = new ArrayList<Weapon>();
		this.armor = new ArrayList<Armor>();
		this.potions = new ArrayList<Potion>();
		//Gets the current instance of board
		this.board = Board.getBoard();
		//Initializes purse with 0 gold
		this.purse = new Purse(0);
		//Loads player image
		try {
			image = ImageIO.read(new File("data/player.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		image = image.getSubimage(69, 89, 60, 70);
		//Adds items to player's inventory
		armor.add(new BasicGarment());
		inventory.add(armor.get(0));
		weapons.add(new Sword());
		inventory.add(weapons.get(0));
		potions.add(new BasicHealthPotion());
		inventory.add(potions.get(0));
	}

	//Methods

	//Checks the player can level up and increases their level
	public void levelUp() {

	}

	//Handles the player's attacks
	public Integer attack() {
		//This needs to be further "flushed out"
		Random rando = new Random();
		return rando.nextInt(this.STRENGTH) + 1;
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
	public int getVisibility() {
		return PERCEPTION * 5;
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
