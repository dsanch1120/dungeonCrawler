/*
 * Created by Daniel Sanchez
 * May 25, 2020
 * This class is for the player.
 */
package dungeonCrawler;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

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
	private int level;
	private final char ICON = 'X';

	//Constructor
	public Player() {
		this.board = Board.getBoard();
		this.purse = new Purse(0);
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
	//Draws the player on the board
	public void draw(Graphics cell) {
		// TODO Auto-generated method stub
		cell.setColor(Color.BLACK);
		cell.drawRect(xCoordinate*15, yCoordinate*15, WIDTH, HEIGHT);
		cell.setColor(Color.MAGENTA);
		cell.fillRect(xCoordinate*15, yCoordinate*15, WIDTH - 1, HEIGHT - 1);
		//cell.setColor(Color.BLACK);
		//cell.drawString("X", xCoordinate*15, (yCoordinate*15) + 15);
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
