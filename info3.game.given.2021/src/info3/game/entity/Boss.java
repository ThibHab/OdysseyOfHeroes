package info3.game.entity;

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
	
	public Boss(Location l) {
		super();
		this.name = "Boss";
		this.location = l;
		this.health = EntitiesConst.BOSS_HEALTH;
		this.weaponDamage = EntitiesConst.BOSS_BASE_DAMAGE;
		this.weaponRange = EntitiesConst.BOSS_RANGE;
		this.speed = EntitiesConst.BOSS_SPEED;
		this.direction = Aut_Direction.W;

		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		this.category = Aut_Category.A;
		
		Aut_Direction dirs[] = new Aut_Direction[] { Aut_Direction.N, Aut_Direction.S, Aut_Direction.E,
				Aut_Direction.W };
		Action acts[] = new Action[] { Action.H };
		this.anim = new Animation(this, ImagesConst.BOSS, dirs, acts);
		this.phase = 0;
		
		this.scale = EntitiesConst.BOSS_SCALE;
	}

	@Override
	public void Hit(Aut_Direction d) {
		if (d == null) {
			d = this.direction;
		}
		
		int bossPosX = (int) this.location.getX();
		Random random = new Random();
		for (int i = 0; i < EntitiesConst.BOSS_MOB_SPAWN_NUMBER; i++) {
			float randomPosX = random.nextInt(19);
			randomPosX += 3;
			float randomPosY = random.nextInt(12);
			randomPosY++;
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
		EntitiesConst.MAP_MATRIX[randomPosY - 1][projectileXPos].entity = new Projectile(this, d, firstProjectileLocation);
		//System.out.println("First projectile fired, X : " + projectileXPos + ", Y : " + firstProjectileLocation.getX());
		EntitiesConst.MAP_MATRIX[randomPosY][projectileXPos].entity = new Projectile(this, d, secondProjectileLocation);
		//System.out.println("Second projectile fired, X : " + projectileXPos + ", Y : " + secondProjectileLocation.getX());
		EntitiesConst.MAP_MATRIX[randomPosY + 1][projectileXPos].entity = new Projectile(this, d, thirdProjectileLocation);
		//System.out.println("Third projectile fired, X : " + projectileXPos + ", Y : " + thirdProjectileLocation.getX());
	}

	@Override
	public void Wizz(Aut_Direction d, Aut_Category c) {
		int projectileXPos = (int) this.location.getX() - 1;
		int[] yPosAlreadyUsed = new int[10];
		boolean yPosValid = false;
		Random random = new Random();
		for (int i = 0; i < EntitiesConst.BOSS_NUMBER_PROJECTILES_TO_BE_FIRED; i++) {
			int randomPosY = random.nextInt(12);
			randomPosY++;
			
			while (!yPosValid) {
				yPosValid = true;
				for (int j = 0; j < yPosAlreadyUsed.length; j++) {
					if (yPosAlreadyUsed[j] == randomPosY) {
						yPosValid = false;
					}
					
					randomPosY = random.nextInt(12);
					randomPosY++;
				}
			}
			
			Location projectileLocation = new Location(projectileXPos, randomPosY);
			EntitiesConst.MAP_MATRIX[randomPosY][projectileXPos].entity = new Projectile(this, d, projectileLocation);
		}
		
		for (int row = 1; row < 13; row++) {
			
			
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
}
