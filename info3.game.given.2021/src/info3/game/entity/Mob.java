package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.constants.EntitiesConst;

public abstract class Mob extends Entity {
	public Mob() {
		super();
		this.scale = 1.3f;
	}
	
	public void paint(Graphics g, int tileSize, float screenPosX, float screenPosY) {
		BufferedImage img = sprites[0];
//		Location pixel = EntitiesConst.GAME.render.gridToPixel(location, true);
//		g.drawImage(img, (int) (pixel.getX() - (scale * tileSize / 2)), (int) (pixel.getY()), (int) (scale * tileSize), (int) (scale * tileSize),
//				null);
		Location pixel = EntitiesConst.GAME.render.gridToPixel(location, true);
		g.drawImage(img, (int) (pixel.getX() - (((scale - 1) / 2) * tileSize)), (int) (pixel.getY() - (((scale - 1) / 2) * tileSize)), (int) (tileSize * scale), (int) (tileSize * scale), null);
	}
}
