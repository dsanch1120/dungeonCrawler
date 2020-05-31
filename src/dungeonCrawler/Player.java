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
	public Player(int xCoordinate, int yCoordinate) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.HP = 1;
		
		//FIXME
		this.ENDURANCE = 3;
		this.STRENGTH = 3;
		this.DEFENSE = 1;
		this.AGILITY = 3;
		this.PERCEPTION = 3;
		this.INTELLIGENCE = 3;
		this.CHARISMA = 3;
		this.HP = this.ENDURANCE * 5;
		this.name = "";
		
		this.purse = new Purse(0);
	}
	
	
	//Methods
	
	//Checks the player can level up and increases their level
	public void levelUp() {
		
	}
	public char getIcon() {
		return ICON;
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
	public Integer agilityRoll() {
		Random rando = new Random();
		return rando.nextInt(this.AGILITY) + 1;
	}
	
	public void draw(Graphics cell) {
		// TODO Auto-generated method stub
		cell.setColor(Color.BLACK);
		cell.drawRect(xCoordinate*15, yCoordinate*15, WIDTH, HEIGHT);
		cell.setColor(Color.LIGHT_GRAY);
		cell.fillRect(xCoordinate*15, yCoordinate*15, WIDTH - 1, HEIGHT - 1);
		cell.setColor(Color.BLACK);
		cell.drawString("X", xCoordinate*15, yCoordinate*15);
	}
	
	//Getters and Setters
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


	public int getHP() {
		return HP;
	}


	public int getyCoordinate() {
		return yCoordinate;
	}


	public void moveX(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}


	public void moveY(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	
	
}
