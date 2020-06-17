/*
 * Created by Daniel Sanchez
 * May 25, 2020
 * Spider enemy
 */
package dungeonCrawler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Spider extends Enemy{

	public Spider(int xCoordinate, int yCoordinate) {
		super(xCoordinate, yCoordinate);
		Random rando = new Random();
		this.name = "Bat";
		this.icon = 's';
		this.ENDURANCE = 1 * this.currentLevel;
		this.STRENGTH = 1 * this.currentLevel;
		this.DEFENSE = 1 * this.currentLevel;
		this.AGILITY = 2 * this.currentLevel;
		this.INTELLIGENCE = 1 * this.currentLevel;
		this.CHARISMA = 0;
		this.spawnChance = 0.0005 * (3 / this.currentLevel);
		this.gold = rando.nextInt(2) + 1;
		this.XP = 3;
		this.maxHP = this.ENDURANCE * 5;
		this.HP = this.maxHP;
		try {
			image = ImageIO.read(new File("data/Dungeon_Tileset.png"));
			oImage = ImageIO.read(new File("data/bat.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		image = image.getSubimage(143, 111, 16, 16);
		oImage = oImage.getSubimage(0, 0, 16, 16);
		//ADD ITEMS TO SPIDER
		
		//ADD ABILITIES TO SPIDER
	}

	@Override
	public boolean spawn() {
		if (this.currentLevel > 3) {
			return false;
		}
		Random rando = new Random();
		int spawnCheck = rando.nextInt(100000) + 1;
		return ((this.spawnChance * 100000) >= spawnCheck);
	}

	@Override
	public Integer attack() {
		Random rando = new Random();
		return rando.nextInt(this.STRENGTH) + 1;
	}

	@Override
	public Integer defend() {
		return this.DEFENSE;
	}

	@Override
	public void draw(Graphics cell) {
		cell.drawImage(image, xCoordinate*15, yCoordinate*15, null);
		cell.drawImage(oImage, xCoordinate*15, yCoordinate*15, null);
	}
}
