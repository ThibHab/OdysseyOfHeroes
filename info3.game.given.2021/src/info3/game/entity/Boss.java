package info3.game.entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Category;
import info3.game.automata.Aut_Direction;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;
import info3.game.map.Tile;

public class Boss extends Mob {
	public int phase;
	public static String n;
	public static int h;
	public int width, height;
	
	public Boss(Location l) {
		super();
		Boss.n = "Boss";
		this.name = "Boss";
		this.location = l;
		Boss.h = EntitiesConst.BOSS_HEALTH;
		this.health = EntitiesConst.BOSS_HEALTH;
		this.weaponDamage = EntitiesConst.BOSS_BASE_DAMAGE;
		this.weaponRange = EntitiesConst.BOSS_RANGE;
		this.direction = Aut_Direction.W;
		this.frozen = true;
		this.range = 18;
		this.width = 3;
		this.height = 3;
		EntitiesConst.MAP_MATRIX[(int)l.getX()-1][(int)l.getY()].entity = this;
		EntitiesConst.MAP_MATRIX[(int)l.getX()-2][(int)l.getY()].entity = this;
		EntitiesConst.MAP_MATRIX[(int)l.getX()-1][(int)l.getY()+1].entity = this;
		EntitiesConst.MAP_MATRIX[(int)l.getX()-2][(int)l.getY()+1].entity = this;
		EntitiesConst.MAP_MATRIX[(int)l.getX()-1][(int)l.getY()-1].entity = this;
		EntitiesConst.MAP_MATRIX[(int)l.getX()-2][(int)l.getY()-1].entity = this;
		EntitiesConst.MAP_MATRIX[(int)l.getX()][(int)l.getY()-1].entity = this;
		EntitiesConst.MAP_MATRIX[(int)l.getX()][(int)l.getY()+1].entity = this;

		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		this.category = Aut_Category.A;
		
		Aut_Direction dirs[] = new Aut_Direction[] { Aut_Direction.N, Aut_Direction.E, Aut_Direction.S,
				Aut_Direction.W };
		Action acts[] = new Action[] { Action.S };
		this.anim = new Animation(this, ImagesConst.BOSS, dirs, acts);
		this.phase = 0;
		this.action = Action.S;
		
		this.scale = EntitiesConst.BOSS_SCALE;
		this.hitbox = new Hitbox(this, (float) 2.0, (float) 2.0);
	}
	
	public void paint(Graphics g, int tileSize, float screenPosX, float screenPosY) {
		BufferedImage img = anim.getFrame();
		Location pixel = EntitiesConst.GAME.render.gridToPixel(location, true);
		int dimension = (int) (scale * tileSize * width);
		int positionX = (int) (pixel.getX() - 3*tileSize);
		int positionY = (int) (pixel.getY() - 3*tileSize + tileSize/2);
		g.drawImage(img, positionX, positionY, dimension, dimension, null);
		if (EntitiesConst.GAME.debug) {
			g.setColor(Color.blue);
			g.drawRect((int) pixel.getX(), (int) pixel.getY(), (int) (tileSize),
					(int) (tileSize));
		}
	}
	
	@Override
	public void takeDamage(Entity attacker) {
		this.health -= attacker.weaponDamage;
		Boss.h = this.health;
		if (this.health < 0 ) {
			this.frozen = false;
		}
	}

	@Override
	public void Hit(Aut_Direction d) {
		if (d == null) {
			d = this.direction;
		}
		
		int bossPosX = (int) this.location.getX();
		Random random = new Random();
		for (int i = 0; i < EntitiesConst.BOSS_MOB_SPAWN_NUMBER; i++) {
			float randomPosX = random.nextInt(18)+3;
			float randomPosY = random.nextInt(12)+1;
			Location mobLocation = new Location(randomPosX, randomPosY);
			while(!this.checkEntitiesAround(mobLocation)) {
				randomPosX = random.nextInt(19);
				randomPosX += 3;
				randomPosY = random.nextInt(12);
				randomPosY++;
				mobLocation = new Location(randomPosX, randomPosY);
			}

			boolean randomMob = random.nextBoolean();
			Tile tile = EntitiesConst.MAP_MATRIX[(int) mobLocation.getX()][(int) mobLocation.getY()];
			if (randomMob) {
				tile.entity = new Skeleton(mobLocation);
			} else {
				tile.entity = new Goblin(mobLocation);
			}
		}
	}
	
	public boolean checkEntitiesAround(Location location) {
		int locationXPos = (int) location.getX();
		int locationYPos = (int) location.getY();
		
		if (EntitiesConst.MAP_MATRIX[locationXPos][locationYPos].entity != null) {
			return false;
		}
		
		for (int row = locationXPos - EntitiesConst.BOSS_MOB_SPAWN_RANGE; row < locationXPos + EntitiesConst.BOSS_MOB_SPAWN_RANGE; row++) {
			for (int column = locationYPos - EntitiesConst.BOSS_MOB_SPAWN_RANGE; column < locationYPos + EntitiesConst.BOSS_MOB_SPAWN_RANGE; column++) {
				Entity entity = EntitiesConst.MAP_MATRIX[row][column].entity;
				if (entity != null && entity.category == Aut_Category.AT) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	@Override
	public void Pop(Aut_Direction d, Aut_Category c) {
		Random random = new Random();
		int randomPosY = random.nextInt(12);
		randomPosY++;
		while (randomPosY == 1 || randomPosY == 12) {
			randomPosY = random.nextInt(12);
			randomPosY++;
		}
		
		int projectileXPos = (int) this.location.getX() - 1;
		Location firstProjectileLocation = new Location(projectileXPos, randomPosY - 1);
		Location secondProjectileLocation = new Location(projectileXPos, randomPosY);
		Location thirdProjectileLocation = new Location(projectileXPos, randomPosY + 1);
//		EntitiesConst.MAP_MATRIX[randomPosY - 1][projectileXPos].entity = new Projectile(this, d, firstProjectileLocation);
		new Projectile(this, d, firstProjectileLocation);
		//System.out.println("First projectile fired, X : " + projectileXPos + ", Y : " + firstProjectileLocation.getX());
//		EntitiesConst.MAP_MATRIX[randomPosY][projectileXPos].entity = new Projectile(this, d, secondProjectileLocation);
		new Projectile(this, d, secondProjectileLocation);
		//System.out.println("Second projectile fired, X : " + projectileXPos + ", Y : " + secondProjectileLocation.getX());
//		EntitiesConst.MAP_MATRIX[randomPosY + 1][projectileXPos].entity = new Projectile(this, d, thirdProjectileLocation);
		new Projectile(this, d, thirdProjectileLocation);
		//System.out.println("Third projectile fired, X : " + projectileXPos + ", Y : " + thirdProjectileLocation.getX());
	}

	@Override
	public void Wizz(Aut_Direction d, Aut_Category c) {
		int projectileXPos = (int) this.location.getX() - 1;
		int[] yPosAlreadyUsed = new int[EntitiesConst.BOSS_NUMBER_PROJECTILES_TO_BE_FIRED];
		boolean yPosValid = false;
		Random random = new Random();
		for (int i = 0; i < EntitiesConst.BOSS_NUMBER_PROJECTILES_TO_BE_FIRED; i++) {
			int randomPosY = 0;
			yPosValid = false;
			while (!yPosValid) {
				randomPosY = random.nextInt(11) + 1;
				yPosValid = true;
				for (int j = 0; j < i; j++) {
					if (yPosAlreadyUsed[j] == randomPosY) {
						yPosValid = false;
					}
				}
			}
			
			Location projectileLocation = new Location(projectileXPos, randomPosY);
			new Projectile(this, d, projectileLocation);
			yPosAlreadyUsed[i] = randomPosY;
		}
	}

	@Override
	public int getNbActionSprite(Action a) {
		switch (a) {
		case M:
			return AnimConst.BOSS_M;
		case H:
			return AnimConst.BOSS_H;
		case T:
			return AnimConst.BOSS_T;
		case D:
			return AnimConst.BOSS_D;
		case S:
			return AnimConst.BOSS_S;
		default:
			return 0;
		}
	}

	@Override
	public int totSrpitePerDir() {
		return AnimConst.BOSS_TOT;
	}
	
	public boolean isFinished() {
		switch (this.action) {
		case S:
			return this.actionIndex >= EntitiesConst.STAND_INDEX_MAX;
		case M:
			return this.actionIndex >= EntitiesConst.MOUVEMENT_INDEX_MAX;
		case H:
			return this.actionIndex >= EntitiesConst.HIT_INDEX_MAX;
		case D:
			return this.actionIndex >= EntitiesConst.DIE_INDEX_MAX;
		case T:
			return this.actionIndex >= EntitiesConst.TOUCHED_INDEX_MAX;
		case I:
			return this.actionIndex >= EntitiesConst.INTERACT_INDEX_MAX;
		default:
			return true;
		}
	}
}
