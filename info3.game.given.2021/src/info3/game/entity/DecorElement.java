package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.automata.*;
import info3.game.constants.EntitiesConst;

public abstract class DecorElement extends Entity {
	public int width, height;

	public DecorElement() {
		super();
		this.category = Aut_Category.O;
	}

	public void paint(Graphics g, int TileSize, float screenPosX, float screenPosY) {
		BufferedImage img = sprites[imageIndex];
		Location l = EntitiesConst.GAME.render.gridToPixel(location, true);
		g.drawImage(img, (int) (l.getX() - (((scale - 1) / 2 )* TileSize)), (int) (l.getY() - (((scale - 1) / 2 )* TileSize)), (int) (scale * TileSize * width), (int) (scale * TileSize * height),
				null);
	}
}
