package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.automata.Category;

public abstract class DecorElement extends Entity {

	public DecorElement() {
		super();
		this.category = Category.O;
	}

	public void paint(Graphics g, int TileSize, float screenPosX, float screenPosY) {
		BufferedImage img = sprites[imageIndex];
		g.drawImage(img, (int) (screenPosX * TileSize), (int) (screenPosY * TileSize), (int) scale * TileSize, (int) scale * TileSize,
				null);
	}
}
