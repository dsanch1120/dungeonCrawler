package dungeonCrawler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Path extends BoardCell{

	public Path(int X, int Y, BufferedImage oImage) {
		super(oImage);
		icon = ' ';
		this.X = X;
		this.Y = Y;
		this.type = CellType.PATH;
		this.oImage = oImage.getSubimage(96, 0, 16, 16);
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
		cell.drawImage(oImage, X*15, Y*15, null);
	}

	
}
