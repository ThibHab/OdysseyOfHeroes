package info3.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import info3.game.automata.*;
import info3.game.constants.EntitiesConst;
import info3.game.map.Tile;

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
		g.drawImage(img, (int) (pixel.getX() - (((scale - 1) / 2) * tileSize)), (int) (pixel.getY() - (((scale - 1) / 2) * tileSize)),(int) (tileSize * scale), (int) (tileSize * scale), null);
		g.setColor(Color.blue);
		Location l = EntitiesConst.GAME.render.gridToPixel(this.hitbox.location, true);

		if (EntitiesConst.GAME.debug) {
			g.drawRect((int) l.getX(), (int) l.getY(), (int) (tileSize * this.hitbox.width),
					(int) (tileSize * this.hitbox.height));
		}
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
		if (d == null) {
			d = this.direction;
		}

		Location location = frontTileLocation(d);
		Tile tile = EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()];
		if (tile.entity.category == Aut_Category.P) {
			if (tile.entity instanceof Chest) {
				Random random = new Random();
				boolean randomLoot = random.nextBoolean();
				if (randomLoot) {
					System.out.println("Looted coins");
					EntitiesConst.COINS += 5;
				} else {
					System.out.println("Looted healing potion");
					this.healingPotions++;
				}
				
				tile.entity = null;
			}
		}
	}
}
