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
		// Cas de la location en dehors de l'écran en X et en Y
		if (screenPosY < 0 && screenPosX < 0) {
			location.setY(location.getY() + (height - 1)); // Modifie sa localisation en Y pour que la maison soit dans l'écran
			location.setX(location.getX() + (width - 1)); // Modifie sa localisation en X pour que la maison soit dans l'écran
			l = EntitiesConst.GAME.render.gridToPixel(location, true);
			// Effectue le draw avec le bon décalage en X et en Y pour que la maison apparaisse à la bonne position
			g.drawImage(img, (int) (l.getX() - (width - 1) * TileSize - (((scale - 0.8) / 2 ) * TileSize)), (int) (l.getY() - (height - 1) * TileSize - (((scale - 0.8) / 2 )* TileSize)), (int) (scale * TileSize * width), (int) (scale * TileSize * height),
					null);
			// Modifie sa localisation en Y pour redonner ses coordonnées initiales.
			location.setY(location.getY() - (height - 1));
			location.setX(location.getX() - (width - 1));
		}
		// Cas de la location en dehors de l'écran en Y
		else if (screenPosY < 0) {
			location.setY(location.getY() + (height - 1));
			l = EntitiesConst.GAME.render.gridToPixel(location, true);
			g.drawImage(img, (int) (l.getX() - (((scale - 0.8) / 2 ) * TileSize)), (int) (l.getY() - (height - 1) * TileSize - (((scale - 0.8) / 2 )* TileSize)), (int) (scale * TileSize * width), (int) (scale * TileSize * height),
					null);
			location.setY(location.getY() - (height - 1));
		}
		// Cas de la location en dehors de l'écran en X 
		else if (screenPosX < 0) {
			location.setX(location.getX() + (width - 1));
			l = EntitiesConst.GAME.render.gridToPixel(location, true);
			g.drawImage(img, (int) (l.getX() - (width - 1) * TileSize - (((scale - 0.8) / 2 ) * TileSize)), (int) (l.getY() - (((scale - 0.8) / 2 )* TileSize)), (int) (scale * TileSize * width), (int) (scale * TileSize * height),
					null);
			location.setX(location.getX() - (width - 1));
		}
		else {
			g.drawImage(img, (int) (l.getX() - (((scale - 0.8) / 2 )* TileSize)), (int) (l.getY() - (((scale - 0.8) / 2 )* TileSize)), (int) (scale * TileSize * width), (int) (scale * TileSize * height),
					null);
		}
		System.out.println(screenPosX + " : " + screenPosY);
	}
}
