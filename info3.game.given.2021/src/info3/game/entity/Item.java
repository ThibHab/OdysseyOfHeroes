package info3.game.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import info3.game.automata.Aut_Category;
import info3.game.constants.EntitiesConst;

public abstract class Item extends Entity {
	protected Image image;

	public Item() {
		super();
		this.category = Aut_Category.P;
	}

	public void paint(Graphics g, int tileSize, float screenPosX, float screenPosY) {
		BufferedImage img = sprites[imageIndex];
		Location pixel = EntitiesConst.GAME.render.gridToPixel(location, true);
		int dimension = (int) (scale * tileSize);
		float shiftXY = ((scale - 1) / 2) * tileSize;
		int positionX = (int) (pixel.getX() - shiftXY);
		int positionY = (int) (pixel.getY() - shiftXY);
		g.drawImage(img, positionX, positionY, dimension, dimension, null);
	}

	@Override
	public void updateSpriteIndex() {
		imageIndex ++;
		if (imageIndex >= this.getStandNbSprite()) {
			imageIndex = 0;
		}
	}
}
