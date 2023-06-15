package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import info3.game.automata.Category;
import info3.game.automata.Direction;

public abstract class Hero extends Entity {
	public static int HERO_WIDTH = 2, HERO_HEIGHT = 2, HERO_SPEED = 2;

	public Hero() {
		super();
		this.width = Hero.HERO_WIDTH;
		this.height = Hero.HERO_HEIGHT;
		this.speed = Hero.HERO_SPEED;

		this.category = Category.AT;
	}

	public void paint(Graphics g, int TileSize) {
		BufferedImage img = sprites[imageIndex];
		g.drawImage(img, (int) location.getX(), (int) location.getY(), (int) scale * TileSize, (int) scale * TileSize,
				null);
	}

	@Override
	public void Pick(Direction d) {
		// TODO Auto-generated method stub
		super.Pick(d);
	}

	@Override
	public void Store(Category c) {
		// TODO Auto-generated method stub
		super.Store(c);
	}
	
	
}
