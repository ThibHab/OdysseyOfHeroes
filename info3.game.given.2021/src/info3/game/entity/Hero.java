package info3.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.automata.*;
import info3.game.constants.EntitiesConst;

public abstract class Hero extends Entity {
	public int maxHealth;
	
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
	
	public void paint(Graphics g, int width, int height) {
		BufferedImage img = sprites[imageIndex];
		Location pixel=EntitiesConst.GAME.render.gridToPixel(location,true);
		g.drawImage(img, (int)pixel.getX(), (int)pixel.getY(), EntitiesConst.GAME.render.tileSize, EntitiesConst.GAME.render.tileSize, null);
		g.setColor(Color.blue);
		Location l = EntitiesConst.GAME.render.gridToPixel(this.hitBoxLocation, true);
		g.drawRect((int)l.getX(), (int)l.getY(), (int) (EntitiesConst.GAME.render.tileSize * this.ratioHitBoxX), (int) (EntitiesConst.GAME.render.tileSize * this.ratioHitBoxY));
		//g.drawRect((int)pixel.getX(), (int)pixel.getY(), game.render.tileSize, game.render.tileSize);
	}
	
	@Override
	public void Power() {
		if (this.healingPotions > 0) {
			this.health = this.maxHealth;
			this.healingPotions--;
		}
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
