package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.automata.Category;
import info3.game.constants.EntitiesConst;

public abstract class DecorElement extends Entity {
	public int Width, Height;

	public DecorElement() {
		super();
		this.category = Category.O;
	}

	public void paint(Graphics g, int TileSize, float screenPosX, float screenPosY) {
		BufferedImage img = sprites[imageIndex];
		Location l = EntitiesConst.GAME.render.gridToPixel(location, true);
		g.drawImage(img, (int) l.getX(), (int) l.getY(), (int) scale * TileSize * Width, (int) scale * TileSize * Height,
				null);
	}
}
