package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class NPC extends Entity {
	public NPC() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void paint(Graphics g, int TileSize) {
		BufferedImage img = sprites[imageIndex];
		g.drawImage(img, (int) location.getX(), (int) location.getY(), (int) scale*TileSize, (int) scale*TileSize, null);
	}
}
