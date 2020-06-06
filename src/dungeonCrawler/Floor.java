package dungeonCrawler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Floor extends BoardCell{
	BufferedImage image;
	
	public Floor(int X, int Y) {
		super();
		icon = ' ';
		this.X = X;
		this.Y = Y;
		this.type = CellType.ROOM;
		try {
			oImage = ImageIO.read(new File("data/Dungeon_Tileset.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		image = oImage.getSubimage(96, 0, 16, 16);
	}

	@Override
	public boolean hasPlayer() {
		return(!(player == null));
	}

	@Override
	public boolean hasEnemy() {
		return (!(enemy == null));
	}

	@Override
	public int behavior() {
		return -1;	
	}

	@Override
	public void draw(Graphics cell) {
		if (this.hasEnemy()) {
			enemy.draw(cell);
		} else {
			cell.drawImage(image, X*15, Y*15, null);
		}
	}
}

