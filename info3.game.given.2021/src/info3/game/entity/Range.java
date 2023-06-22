package info3.game.entity;

import info3.game.Game;
import info3.game.automata.*;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Range extends Hero {
	public Range(String name, Game g) {
		super();
		this.name = name;
		this.weaponDamage = EntitiesConst.RANGE_DAMAGE;
		this.weaponRange = EntitiesConst.RANGE_RANGE;
		this.health = 8;
		this.maxHealth = this.health;
		this.range = 3;
        
		for (Aut_Automaton next : g.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;

		this.sprites = ImagesConst.RANGE;
		this.imageIndex = 0;
		this.hitbox = new Hitbox(this, (float)0.50, (float)0.60);
	}
	
	
	@Override
	public void Hit(Aut_Direction d) {
		if (!this.hitFrozen) {
			this.frozen = true;
			if (this.action != Action.H) {
				System.out.println(this.name + " hits");
				this.imageIndex = this.sprites.length;
				this.action = Action.H;
				this.updateSpriteIndex();
			}
			this.hitFrozen = true;
			if(d != null) {
				Projectile p = new Projectile(this, d);
			}else {
				Projectile p = new Projectile(this, this.direction);
			}
			
		}
	}
	public int getHitNbSprite() {
		return AnimConst.RANGE_H;
	}
	
	@Override
	public int getMvmtNbSprite() {
		return AnimConst.RANGE_M;
	}
	
	@Override
	public int getStandNbSprite() {
		return AnimConst.RANGE_S;
	}
	
	@Override
	public int getDieNbSprite() {
		return AnimConst.RANGE_D;
	}
	
	@Override
	public int getTouchedNbSprite() {
		return AnimConst.RANGE_T;
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
	public void updateSpriteIndex() {
		int idx = 0;
		switch (this.direction) {
		case S:
			break;
		case E:
			idx += (1 * AnimConst.RANGE_TOT);
			break;
		case N:
			idx += (2 * AnimConst.RANGE_TOT);
			break;
		case W:
			idx += (3 * AnimConst.RANGE_TOT);
			break;
		default:
			break;
		}
		if (this.action == Action.S) {
			if (this.imageIndex + 1 < idx + AnimConst.RANGE_S) {
				this.imageIndex = this.imageIndex + 1;
				return;
			}
			this.imageIndex = idx;
			return;
		}
		idx += AnimConst.RANGE_S;
		if (this.action == Action.M) {
			if (this.imageIndex + 1 < idx + AnimConst.RANGE_M) {
				this.imageIndex = this.imageIndex + 1;
				return;
			}
			this.imageIndex = idx;
			return;
		}
		idx += AnimConst.RANGE_M;
		if (this.action == Action.H) {
			if (this.imageIndex + 1 < idx + AnimConst.RANGE_H) {
				this.imageIndex = this.imageIndex + 1;
				return;
			}
			this.imageIndex = idx;
			return;
		}
		idx += AnimConst.RANGE_H;
		if (this.action == Action.T) {
			if (this.imageIndex + 1 < idx + AnimConst.RANGE_T) {
				this.imageIndex = this.imageIndex + 1;
				return;
			}
			this.imageIndex = idx;
			return;
		}
		idx += AnimConst.RANGE_T;
		if (this.imageIndex + 1 < idx + AnimConst.RANGE_D) {
			this.imageIndex = this.imageIndex + 1;
			return;
		}
		this.imageIndex = idx;
		return;
	}
	
	@Override
	public void updateStats() {
		this.weaponDamage += 2;
		
		if (Hero.level % 2 == 0) {
			this.maxHealth += 1;
		}
		
		this.health = this.maxHealth;
	}
}
