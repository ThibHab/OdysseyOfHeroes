package info3.game.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import info3.game.automata.Aut_Category;

public abstract class Item extends Entity {
	protected Image image;

	public Item() {
		super();
		this.category = Category.P;
	}
	
	public void paint(Graphics g, int TileSize) {
		BufferedImage img = sprites[imageIndex];
		g.drawImage(img, (int) location.getX(), (int) location.getY(), (int) scale*TileSize, (int) scale*TileSize, null);
	}
}
