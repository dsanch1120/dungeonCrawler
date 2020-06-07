package dungeonCrawler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Border extends BoardCell{
	int bType;
	BufferedImage image;

	public Border(int X, int Y, int bType, BufferedImage oImage) {
		super(oImage);
		icon = '+';
		this.X = X;
		this.Y = Y;
		this.type = CellType.BORDER;
		this.bType = bType;
		this.oImage = oImage;
		loadImage();
	}

	private void loadImage() {
		switch (bType) {
		case 0:	//Top left corner
			image = oImage.getSubimage(0, 0, 16, 16);
			break;
		case 1:	//Top right corner
			image = oImage.getSubimage(79, 0, 16, 16);
			break;
		case 2:	//Bottom left corner
			image = oImage.getSubimage(0, 63, 16, 16);
			break;
		case 3: //Bottom right corner
			image = oImage.getSubimage(79, 63, 16, 16);
			break;
		case 4:	//Top Side Border
			image = oImage.getSubimage(15, 0, 16, 16);
			break;
		case 5:	//Bottom Side Border
			image = oImage.getSubimage(15, 63, 16, 16);
			break;
		case 6:	//Left side Border
			image = oImage.getSubimage(0, 15, 16, 16);
			break;
		case 7:	//Right side Border
			image = oImage.getSubimage(79, 15, 16, 16);
			break;
		case 8:	//Basic Border Type
			image = oImage.getSubimage(127, 111, 16, 16);
			break;
		default:
			System.out.println("Error. This should not be accessed");
		}
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
		return -1;		
	}

	@Override
	public void draw(Graphics cell) {
		if (visible) {
			cell.drawImage(image, X*15, Y*15, null);
		} else {
			cell.setColor(Color.BLACK);
			cell.drawRect(X*15, Y*15, width, height);
			cell.setColor(Color.BLACK);
			cell.fillRect(X*15, Y*15, width, height);
		}
	}
}
