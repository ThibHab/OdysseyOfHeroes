package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.automata.*;
import info3.game.constants.EntitiesConst;

public abstract class Hero extends Entity {
	public Hero() {
		super();
		this.speed = EntitiesConst.HERO_SPEED;

		this.category = Aut_Category.AT;
		
		this.scale = EntitiesConst.HEROES_SCALE;
	}

	public void paint(Graphics g, int TileSize) {
		BufferedImage img = sprites[imageIndex];
		g.drawImage(img, (int) location.getX(), (int) location.getY(), (int) scale * TileSize, (int) scale * TileSize,
				null);
	}

	@Override
	public void Pick(Aut_Direction d) {
		// TODO Auto-generated method stub
		super.Pick(d);
	}

	@Override
	public void Store(Aut_Category c) {
		// TODO Auto-generated method stub
		super.Store(c);
	}
	
	
}
