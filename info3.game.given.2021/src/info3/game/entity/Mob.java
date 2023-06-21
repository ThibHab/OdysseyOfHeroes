package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Mob extends Entity {
	public Mob() {
		super();
	}
	
	public void paint(Graphics g, int TileSize) {
		BufferedImage img = anim.get_frame();
		g.drawImage(img, (int) location.getX(), (int) location.getY(), (int) scale*TileSize, (int) scale*TileSize, null);
	}
}
