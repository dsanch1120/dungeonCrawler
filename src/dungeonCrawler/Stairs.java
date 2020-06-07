package dungeonCrawler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Stairs extends BoardCell{
	private int stairType;
	private BufferedImage image;

	public Stairs(int X, int Y, int stairType, BufferedImage oImage) {
		super(oImage);
		icon = '%';
		this.X = X;
		this.Y = Y;
		this.type = CellType.STAIRS;
		this.stairType = stairType;
		this.oImage = oImage;
		try {
			image = ImageIO.read(new File("data/Dungeon_Tileset.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		image = image.getSubimage(143, 47, 16, 16);
		this.oImage = oImage.getSubimage(143, 111, 16, 16);
	}

	@Override
	public boolean hasPlayer() {
		return false;
	}

	@Override
	public boolean hasEnemy() {
		return false;
	}

	@Override
	public int behavior() {
		return stairType;
	}

	@Override
	public void draw(Graphics cell) {
		if (visible) {
			cell.drawImage(oImage, X*15, Y*15, null);
			cell.drawImage(image, X*15, Y*15, null);
		} else {
			cell.setColor(Color.BLACK);
			cell.drawRect(X*15, Y*15, width, height);
			cell.setColor(Color.BLACK);
			cell.fillRect(X*15, Y*15, width, height);
		}
	}

}
