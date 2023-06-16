package info3.game.entity;

import java.util.Random;

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
		switch (this.phase) {
		case 0:
			this.frontPawAttack(d);
			break;
		case 1:
			randomAttack = random.nextInt(2);
			if (randomAttack == 0) {
				this.frontPawAttack(d);
			} else {
				this.flameAttack(d);
			}
			break;			
		case 2:
			randomAttack = random.nextInt(3);
			switch (randomAttack) {
			case 0:
				this.frontPawAttack(d);
				break;
			case 1:
				this.flameAttack(d);
				break;
			case 2:
				// TODO add 3rd attack
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
	}
	
	public void frontPawAttack(Aut_Direction d) {
		switch (d) {
		case N:
			
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
	
	public void flameAttack(Aut_Direction d) {
		int mapHeight = EntitiesConst.MAP.lenY;
		int mapWidth = EntitiesConst.MAP.lenX;
		int mapHalfHeight = 0, mapHalfWidth = 0;
		switch (d) {
		case N:
			for (int row = 0; row < mapHeight / 2; row++) {
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
			
			for (int row = mapHeight; row > mapHalfHeight; row--) {
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
			for (int column = 0; column < mapWidth / 2; column++) {
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
			
			for (int column = mapWidth; column > mapHalfWidth; column--) {
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
