package info3.game.entity;

import info3.game.automata.*;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;
import info3.game.constants.StatesConst;

public class Range extends Hero {
	public Range(String name, Location l) {
		super();
		this.name = name;
		this.location = l;
		this.weaponDamage = EntitiesConst.RANGE_DAMAGE;
		this.weaponRange = EntitiesConst.RANGE_RANGE;
		this.health = EntitiesConst.RANGE_HEALTH;

		// --- TODO manage automaton ---
		this.automaton = null;
		this.currentState = null;
		// -----------------------------

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.RANGE;
		this.imageIndex = 0;
		// -----------------------------------
	}

	@Override
	public void Hit(Aut_Direction d) {
		if (!this.hitFrozen) {
			this.hitFrozen = true;
			Projectile p = new Projectile(this, this.direction);
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
	
	public int getSpriteIndex() {
		int idx = this.imageIndex;
		switch (this.direction) {
		case N:
			idx += 26;
			break;
		case E:
			idx += 13;
			break;
		case W:
			idx += 39;
			break;
		default:
			break;
		}
		switch (this.currentState.toString()) {
		case StatesConst.MOVE:
			idx+=1;
			break;
		case StatesConst.ATTACK:
			idx += 4;
			break;
		case StatesConst.HIT:
			idx += 8;
			break;
		case StatesConst.DIE:
			idx += 11;
			break;
		}
		return idx;
	}
}
