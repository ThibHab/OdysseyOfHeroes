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
	
	public void paint(Graphics g, int TileSize, float screenPosX, float screenPosY) {
		BufferedImage img = sprites[imageIndex];
		Location l = EntitiesConst.GAME.render.gridToPixel(location, true);
		g.drawImage(img, (int) (l.getX() - (((scale - 0.8) / 2 )* TileSize)), (int) (l.getY() - (((scale - 0.8) / 2 )* TileSize)), (int) (scale * TileSize * 1), (int) (scale * TileSize * 1),
				null);
	}
	
	@Override
	public void updateSpriteIndex() {
		imageIndex ++;
		if (imageIndex >= this.getStandNbSprite()) {
			imageIndex = 0;
		}
	}
}
