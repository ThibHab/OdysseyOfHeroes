package info3.game.entity;

import java.awt.Color;
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

	public void paint(Graphics g, int tileSize) {
		BufferedImage img = sprites[imageIndex];
		Location pixel = EntitiesConst.GAME.render.gridToPixel(location, true);
		g.drawImage(img, (int) pixel.getX(), (int) pixel.getY(), tileSize, tileSize, null);
		g.setColor(Color.blue);
		Location l = EntitiesConst.GAME.render.gridToPixel(this.hitbox.location, true);
		g.drawRect((int) l.getX(), (int) l.getY(), (int) (tileSize * this.hitbox.width),
				(int) (tileSize * this.hitbox.height));
		// g.drawRect((int)pixel.getX(), (int)pixel.getY(), game.render.tileSize,
		// game.render.tileSize);
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
