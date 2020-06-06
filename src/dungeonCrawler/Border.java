package dungeonCrawler;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Border extends BoardCell{
	int bType;

	public Border(int X, int Y, int bType) {
		super();
		icon = '+';
		this.X = X;
		this.Y = Y;
		this.type = CellType.BORDER;
		this.bType = bType;
		try {
			image = ImageIO.read(new File("data/Dungeon_Tileset.png"));
		} catch (IOException e) {
			e.printStackTrace();
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
		switch (bType) {
		case 0:	//Top left corner
			image = image.getSubimage(0, 0, 16, 16);
			cell.drawImage(image, X*15, Y*15, null);
			break;
		case 1:	//Top right corner
			image = image.getSubimage(79, 0, 16, 16);
			cell.drawImage(image, X*15, Y*15, null);
			break;
		case 2:	//Bottom left corner
			image = image.getSubimage(0, 63, 16, 16);
			cell.drawImage(image, X*15, Y*15, null);
			break;
		case 3: //Bottom right corner
			image = image.getSubimage(79, 63, 16, 16);
			cell.drawImage(image, X*15, Y*15, null);
			break;
		case 4:	//Top Side Border
			image = image.getSubimage(15, 0, 16, 16);
			cell.drawImage(image, X*15, Y*15, null);
			break;
		case 5:	//Bottom Side Border
			image = image.getSubimage(15, 63, 16, 16);
			cell.drawImage(image, X*15, Y*15, null);
			break;
		case 6:	//Left side Border
			image = image.getSubimage(0, 15, 16, 16);
			cell.drawImage(image, X*15, Y*15, null);
			break;
		case 7:	//Right side Border
			image = image.getSubimage(79, 15, 16, 16);
			cell.drawImage(image, X*15, Y*15, null);
			break;
		case 8:	//Basic Border Type
			image = image.getSubimage(127, 111, 16, 16);
			cell.drawImage(image, X*15, Y*15, null);
			break;
		default:
			System.out.println("Error. This should not be accessed");
		}

	}
}
