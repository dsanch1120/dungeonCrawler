package dungeonCrawler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Floor extends BoardCell{
	BufferedImage image;

	public Floor(int X, int Y, BufferedImage oImage) {
		super(oImage);
		icon = ' ';
		this.X = X;
		this.Y = Y;
		this.type = CellType.ROOM;
		this.oImage = oImage;
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
		if (visible) {
			if (this.hasEnemy()) {
				enemy.draw(cell);
			} else {
				cell.drawImage(image, X*15, Y*15, null);
			}

		} else {
			cell.setColor(Color.BLACK);
			cell.drawRect(X*15, Y*15, width, height);
			cell.setColor(Color.BLACK);
			cell.fillRect(X*15, Y*15, width, height);
		}
	}
}
