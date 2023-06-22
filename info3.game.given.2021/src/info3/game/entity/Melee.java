package info3.game.entity;

import info3.game.Game;
import info3.game.automata.*;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Melee extends Hero {
	public Melee(String name, Game g) {
		super();
		this.name = name;
		this.weaponDamage = EntitiesConst.MELEE_DAMAGE;
		this.weaponRange = EntitiesConst.MELEE_RANGE;
		this.health = 12;
		this.maxHealth = this.health;

		for (Aut_Automaton next : g.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;

		this.sprites = ImagesConst.MELEE;
		this.imageIndex = 0;
		this.hitbox = new Hitbox(this, (float)0.50, (float)0.65);
	}
	
	@Override
	public int getHitNbSprite() {
		return AnimConst.MELEE_H;
	}
	
	@Override
	public int getMvmtNbSprite() {
		return AnimConst.MELEE_M;
	}
	
	@Override
	public int getStandNbSprite() {
		return AnimConst.MELEE_S;
	}
	
	@Override
	public int getDieNbSprite() {
		return AnimConst.MELEE_D;
	}
	
	@Override
	public int getTouchedNbSprite() {
		return AnimConst.MELEE_T;
	}

	@Override
	public void updateSpriteIndex() {
		int idx = 0;
		switch (this.direction) {
		case S:
			break;
		case E:
			idx += (1 * AnimConst.MELEE_TOT);
			break;
		case N:
			idx += (2 * AnimConst.MELEE_TOT);
			break;
		case W:
			idx += (3 * AnimConst.MELEE_TOT);
			break;
		default:
			break;
		}
		if (this.action == Action.S) {
			if (this.imageIndex + 1 < idx + AnimConst.MELEE_S) {
				this.imageIndex = this.imageIndex + 1;
				return;
			}
			this.imageIndex = idx;
			return;
		}
		idx += AnimConst.MELEE_S;
		if (this.action == Action.M) {
			if (this.imageIndex + 1 < idx + AnimConst.MELEE_M) {
				this.imageIndex = this.imageIndex + 1;
				return;
			}
			this.imageIndex = idx;
			return;
		}
		idx += AnimConst.MELEE_M;
		if (this.action == Action.H) {
			if (this.imageIndex + 1 < idx + AnimConst.MELEE_H) {
				this.imageIndex = this.imageIndex + 1;
				return;
			}
			this.imageIndex = idx;
			return;
		}
		idx += AnimConst.MELEE_H;
		if (this.action == Action.T) {
			if (this.imageIndex + 1 < idx + AnimConst.MELEE_T) {
				this.imageIndex = this.imageIndex + 1;
				return;
			}
			this.imageIndex = idx;
			return;
		}
		idx += AnimConst.MELEE_T;
		if (this.imageIndex + 1 < idx + AnimConst.MELEE_D) {
			this.imageIndex = this.imageIndex + 1;
			return;
		}
		this.imageIndex = idx;
		return;
	}

	@Override
	public void updateStats() {
		this.weaponDamage++;
		this.maxHealth += 1;
		this.health = this.maxHealth;
	}
	
	
    // function called only in the dungeon map
	public void lightAround() {
		EntitiesConst.MAP_MATRIX[(int) this.location.getX()][(int) this.location.getY()].opacity = 0f;
		
		int x = (int) this.location.getX() + 1;
		int y = (int) this.location.getY() - 1;
		
		EntitiesConst.MAP_MATRIX[x][y].opacity = 0.5f;
		x--;
		EntitiesConst.MAP_MATRIX[x][y].opacity = 0f;
		x--;
		EntitiesConst.MAP_MATRIX[x][y].opacity = 0.5f;
		y++;
		EntitiesConst.MAP_MATRIX[x][y].opacity = 0f;
		y++;
		EntitiesConst.MAP_MATRIX[x][y].opacity = 0.5f;
		x++;
		EntitiesConst.MAP_MATRIX[x][y].opacity = 0f;
		x++;
		EntitiesConst.MAP_MATRIX[x][y].opacity = 0.5f;
		y--;
		EntitiesConst.MAP_MATRIX[x][y].opacity = 0f;
	}
}
