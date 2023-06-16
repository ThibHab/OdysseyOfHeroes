package info3.game.entity;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import info3.game.automata.*;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

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
		// --- TODO manage automaton ---
		this.automaton = null;
		this.currentState = null;
		// -----------------------------
		this.category = Aut_Category.A;
		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.BOSS;
		this.imageIndex = 0;
		// -----------------------------------
		this.phase = 0;
	}

	@Override
	public void Hit(Aut_Direction d) {
		if (d == null) {
			d = this.direction;
		}
		
		Random random = new Random();
		int randomAttack = 0;
		if (this.phase == 0) {
			randomAttack = random.nextInt(2);
			if (randomAttack == 0) {
				this.fireballAttack(d);
			} else {
				this.spawnMobs();
			}
		} else {
			randomAttack = random.nextInt(3);
			switch (randomAttack) {
			case 0:
				this.fireballAttack(d);
				break;
			case 1:
				this.spawnMobs();
				break;
			case 2:
				this.flameBreathAttack(d);
				break;
			default:
				break;
			}
		}
	}
	
	public void spawnMobs() {
		int bossPosX = (int) this.location.getX();
		int bossPosY = (int) this.location.getY();
		Random random = new Random();
		for (int i = 0; i < EntitiesConst.BOSS_MOB_SPAWN_NUMBER; i++) {
			float randomPosX = ThreadLocalRandom.current().nextInt(bossPosX - EntitiesConst.BOSS_MOB_SPAWN_RANGE, bossPosX + EntitiesConst.BOSS_MOB_SPAWN_RANGE);
			float randomPosY = ThreadLocalRandom.current().nextInt(bossPosY - EntitiesConst.BOSS_MOB_SPAWN_RANGE, bossPosY + EntitiesConst.BOSS_MOB_SPAWN_RANGE);
			Location mobLocation = new Location(randomPosX, randomPosY);
			
			int randomMob = random.nextInt(2);
			if (randomMob == 0) {
				new Skeleton(mobLocation);
			} else {
				new Goblin(mobLocation);
			}
		}
	}
	
	public void fireballAttack(Aut_Direction d) {
		int mapHeight = EntitiesConst.MAP.lenY;
		int mapWidth = EntitiesConst.MAP.lenX;
		int dragonTopPosHeight = mapHeight / 2 + 1;
		int dragonSubPosHeight = mapHeight / 2 - 2;
		int dragonTopPosWidth = mapWidth / 2 + 1;
		int dragonSubPosWidth = mapWidth / 2 - 2;
		Random random = new Random();
		switch (d) {
		case N:
			int randomNumberOfFireballs = random.nextInt(EntitiesConst.BOSS_FIREBALL_NUMBER_MAX);
			int[] eliminatedPos = new int[randomNumberOfFireballs];
			for (int i = 0; i < randomNumberOfFireballs; i++) {
				int randomPos = random.nextInt(mapWidth);
				boolean isPosAlreadyUsed = false;
				for (int j = 0; j < eliminatedPos.length; j++) {
					if (randomPos == eliminatedPos[j]) {
						isPosAlreadyUsed = true;
					}
				}
				
				if (!isPosAlreadyUsed) {
					// TODO add direction and location in constructor
					new Projectile(this);
					eliminatedPos[i] = randomPos;
				}
			}
			break;
		case S:
			
			break;
		case W:
			
			break;
		case E:
			
			break;
		default:
			break;
		}
	}
	
	public void flameBreathAttack(Aut_Direction d) {
		// TODO position calculation is not good
		int mapHeight = EntitiesConst.MAP.lenY;
		int mapWidth = EntitiesConst.MAP.lenX;
		int mapHalfHeight = 0, mapHalfWidth = 0;
		switch (d) {
		case N:
			for (int row = 0; row < mapHeight / 2 - 2; row++) {
				for (int column = row; column < mapWidth - row; column++) {
					Entity entity = EntitiesConst.MAP_MATRIX[row][column].entity;
					if (entity != null) {
						entity.health -= EntitiesConst.BOSS_FLAME_ATTACK_DAMAGE;
					}
					// TODO animation for the flame breath
				}
			}
			break;
		case S:
			if (mapHeight % 2 != 0) {
				mapHalfHeight = mapHeight / 2 + 1;
			} else {
				mapHalfHeight = mapHeight / 2;
			}
			
			for (int row = mapHeight; row > mapHalfHeight + 1; row--) {
				int shiftIndex = mapHeight - row;
				for (int column = shiftIndex; column < mapWidth - shiftIndex; column++) {
					Entity entity = EntitiesConst.MAP_MATRIX[row][column].entity;
					if (entity != null) {
						entity.health -= EntitiesConst.BOSS_FLAME_ATTACK_DAMAGE;
					}
					// TODO animation for the flame breath
				}
			}
			break;
		case W:
			for (int column = 0; column < mapWidth / 2 - 2; column++) {
				for (int row = column; row < mapHeight - column; row++) {
					Entity entity = EntitiesConst.MAP_MATRIX[row][column].entity;
					if (entity != null) {
						entity.health -= EntitiesConst.BOSS_FLAME_ATTACK_DAMAGE;
					}
					// TODO animation for the flame breath
				}
			}
			break;
		case E:
			if (mapWidth % 2 != 0) {
				mapHalfWidth = mapWidth / 2 + 1;
			} else {
				mapHalfWidth = mapWidth / 2;
			}
			
			for (int column = mapWidth; column > mapHalfWidth + 1; column--) {
				int shiftIndex = mapWidth - column;
				for (int row = shiftIndex; row < mapWidth - shiftIndex; row++) {
					Entity entity = EntitiesConst.MAP_MATRIX[column][row].entity;
					if (entity != null) {
						entity.health -= EntitiesConst.BOSS_FLAME_ATTACK_DAMAGE;
					}
					// TODO animation for the flame breath
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void Pop(Aut_Direction d, Aut_Category c) {
		// TODO Auto-generated method stub
		super.Pop(d, c);
	}

	@Override
	public void Wizz(Aut_Direction d, Aut_Category c) {
		// TODO Auto-generated method stub
		super.Wizz(d, c);
	}

	@Override
	public void Power() {
		// TODO Auto-generated method stub
		super.Power();
	}

	@Override
	public void Throw(Aut_Direction d, Aut_Category category) {
		// TODO Auto-generated method stub
		super.Throw(d, category);
	}

}
