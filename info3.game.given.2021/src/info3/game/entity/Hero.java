package info3.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import info3.game.automata.Aut_Category;
import info3.game.automata.Aut_Direction;
import info3.game.constants.Action;
import info3.game.constants.EntitiesConst;
import info3.game.map.Tile;

public abstract class Hero extends Entity {
	public static int coins, level, levelUp, experience;
	public int healingPotions, strengthPotions;
	
	public Hero() {
		super();
		this.speed = EntitiesConst.HERO_SPEED;
		this.category = Aut_Category.AT;
		this.scale = EntitiesConst.HEROES_SCALE;
		Hero.coins = EntitiesConst.COINS;
		Hero.level = EntitiesConst.LEVEL;
		Hero.levelUp = EntitiesConst.LEVEL_UP;
		Hero.experience = EntitiesConst.EXPERIENCE;
	}

	public void paint(Graphics g, int tileSize) {
		BufferedImage img = anim.getFrame();
		Location pixel = EntitiesConst.GAME.render.gridToPixel(location, true);
		int dimension = (int) (scale * tileSize);
		float shiftXY = ((scale - 1) / 2) * tileSize;
		int positionX = (int) (pixel.getX() - shiftXY);
		int positionY = (int) (pixel.getY() - shiftXY);
		g.drawImage(img, positionX, positionY, dimension, dimension, null);
		if (EntitiesConst.GAME.debug) {
			g.setColor(Color.blue);
			Location l = EntitiesConst.GAME.render.gridToPixel(this.hitbox.location, true);
			g.drawRect((int) l.getX(), (int) l.getY(), (int) (tileSize * this.hitbox.width),
					(int) (tileSize * this.hitbox.height));
		}
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
		Entity entity = EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity;
		if (entity.category == Aut_Category.P) {
			if (entity instanceof Coin)
				this.coins ++;
			else if (entity instanceof HealingPotion)
				this.healingPotions++;
			else if (entity instanceof StrengthPotion)
				this.strengthPotions++;
			
			EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity = null;
		}
	}

	public static void addExperience(Entity attacker) {
		Hero.experience += EntitiesConst.DEATH_EXPERIENCE_GIVEN;
		if (Hero.experience >= Hero.levelUp) {
			Hero.level++;
			Hero.experience = 0;
			Hero.levelUp = Hero.levelUp * 2;
			
			EntitiesConst.GAME.player1.updateStats();
			EntitiesConst.GAME.player2.updateStats();
		}
		
	}
	
	@Override
	public void takeDamage(Entity attacker) {
		this.health -= attacker.weaponDamage;
		if (this.health <= 0) {
			this.die();
		}
	}
	
	@Override
	public void die() {
		if (this.action != Action.D) {
			this.action = Action.D;
			this.anim.changeAction(action);
			this.frozen = true;

			if (EntitiesConst.GAME.debug) {
				System.out.println(this.name + " has died");
			}
		}
	}
}
