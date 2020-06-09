/*
 * Created by Daniel Sanchez
 * June 8, 2020
 */
package dungeonCrawler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Treasure extends BoardCell {
	BufferedImage image;
	
	public Treasure(int X, int Y, BufferedImage oImage) {
		super(oImage);
		this.X = X;
		this.Y = Y;
		this.type = CellType.TREASURE;
		try {
			image = ImageIO.read(new File("data/Dungeon_Tileset.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		image = image.getSubimage(15, 15, 16, 16);
		this.oImage = oImage.getSubimage(63, 127, 16, 16);
	}
	
	@Override
	public void draw(Graphics cell) {
		if (visible) {
		cell.drawImage(image, X*15, Y*15, null);
		cell.drawImage(oImage, X*15, Y*15, null);
	} else {
		cell.setColor(Color.BLACK);
		cell.drawRect(X*15, Y*15, width, height);
		cell.setColor(Color.BLACK);
		cell.fillRect(X*15, Y*15, width, height);
	}
	}
	@Override
	public int behavior() {
		return -1;
	}
	@Override
	public boolean hasPlayer() {
		return false;
	}
	@Override
	public boolean hasEnemy() {
		return false;
	}
}
