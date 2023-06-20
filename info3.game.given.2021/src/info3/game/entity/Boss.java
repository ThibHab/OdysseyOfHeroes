package info3.game.entity;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import info3.game.automata.*;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;
import info3.game.map.Tile;

public class Boss extends Mob {
	public int phase;
	public int mapHeight;
	public int mapWidth;
	
	public Boss(Location l) {
		super();
		this.name = "Boss";
		this.location = l;
		this.health = EntitiesConst.BOSS_HEALTH;
		this.weaponDamage = EntitiesConst.BOSS_BASE_DAMAGE;
		this.weaponRange = EntitiesConst.BOSS_RANGE;
		this.speed = EntitiesConst.BOSS_SPEED;

		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				this.automaton = next;
		}
		this.currentState = automaton.initial;
		// -----------------------------
		this.category = Aut_Category.A;
		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.BOSS;
		this.imageIndex = 0;
		// -----------------------------------
		this.phase = 0;
		
		this.mapHeight = EntitiesConst.MAP.lenY;
		this.mapWidth = EntitiesConst.MAP.lenX;
	}

	@Override
	public void Hit(Aut_Direction d) {
		if (d == null) {
			d = this.direction;
		}
		
		int bossPosX = (int) this.location.getX();
		Random random = new Random();
		for (int i = 0; i < EntitiesConst.BOSS_MOB_SPAWN_NUMBER; i++) {
			float randomPosX = random.nextInt(bossPosX);
			float randomPosY = random.nextInt(this.mapHeight);
			Location mobLocation = new Location(randomPosX, randomPosY);
			while(!this.checkEntitiesAround(mobLocation)) {
				randomPosX = random.nextInt(bossPosX);
				randomPosY = random.nextInt(this.mapHeight);
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
	public void Pop(Aut_Direction d) {
		Random random = new Random();
		int randomPosY = random.nextInt(mapHeight);
		while (randomPosY == 0 || randomPosY == this.mapHeight) {
			randomPosY = random.nextInt(mapHeight);
		}
		
		EntitiesConst.MAP_MATRIX[(int) this.location.getX()][randomPosY - 1].entity = new Projectile(this, d);
		EntitiesConst.MAP_MATRIX[(int) this.location.getX()][randomPosY].entity = new Projectile(this, d);
		EntitiesConst.MAP_MATRIX[(int) this.location.getX()][randomPosY + 1].entity = new Projectile(this, d);
	}

	@Override
	public void Wizz(Aut_Direction d, Aut_Category c) {
//		boolean endReached = false;
//		int attackPosX = (int) this.location.getX() - 1;
//		while (!endReached) {
//			for (int column = attackPosX; column < EntitiesConst.BOSS_FLAME_ATTACK_SIZE; column--) {
//				for (int row = 0; row < this.mapHeight; row++) {
//					
//				}
//			}
//		}
	}

	@Override
	public void Power() {
		this.phase++;
	}
}
