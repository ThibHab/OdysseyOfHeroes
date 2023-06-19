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

	public void paint(Graphics g, int tileSize, float screenPosX, float screenPosY) {
		BufferedImage img = sprites[0];
		float shiftImage = ((scale - 0.8f) / 2) * tileSize;
		int sizeWidth = (int) (scale * tileSize * width);
		int sizeHeight = (int) (scale * tileSize * height);
		int imageHeight = height - 1;
		int imageWidth = width - 1;
		Location l = EntitiesConst.GAME.render.gridToPixel(location, true);
		// Cas de la location en dehors de l'écran en X et en Y
		if (screenPosY < 0 && screenPosX < 0) {
			location.setY(location.getY() + imageHeight);
			location.setX(location.getX() + imageWidth);
			l = EntitiesConst.GAME.render.gridToPixel(location, true);
			g.drawImage(img, (int) (l.getX() - imageWidth * tileSize - shiftImage),
					(int) (l.getY() - imageHeight * tileSize - shiftImage), sizeWidth, sizeHeight, null);
			location.setY(location.getY() - imageHeight);
			location.setX(location.getX() - imageWidth);
		}
		// Cas de la location en dehors de l'écran en Y
		else if (screenPosY < 0) {
			location.setY(location.getY() + imageHeight);
			l = EntitiesConst.GAME.render.gridToPixel(location, true);
			g.drawImage(img, (int) (l.getX() - shiftImage), (int) (l.getY() - imageHeight * tileSize - shiftImage),
					sizeWidth, sizeHeight, null);
			location.setY(location.getY() - imageHeight);
		}
		// Cas de la location en dehors de l'écran en X
		else if (screenPosX < 0) {
			location.setX(location.getX() + imageWidth);
			l = EntitiesConst.GAME.render.gridToPixel(location, true);
			g.drawImage(img, (int) (l.getX() - imageWidth * tileSize - shiftImage), (int) (l.getY() - shiftImage),
					sizeWidth, sizeHeight, null);
			location.setX(location.getX() - imageWidth);
		} else {
			g.drawImage(img, (int) (l.getX() - shiftImage), (int) (l.getY() - shiftImage), sizeWidth, sizeHeight, null);
		}
	}
}
