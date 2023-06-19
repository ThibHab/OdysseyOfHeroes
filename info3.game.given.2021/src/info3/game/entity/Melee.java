package info3.game.entity;

import info3.game.automata.*;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Melee extends Hero {
	public Melee(String name, Location l) {
		super();
		this.name = name;
		this.location = l;
		this.weaponDamage = EntitiesConst.MELEE_DAMAGE;
		this.weaponRange = EntitiesConst.MELEE_RANGE;
		this.health = 12;
		this.maxHealth = this.health;

		// --- TODO manage automaton ---
		this.automaton = null;
		this.currentState = null;
		// -----------------------------

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.MELEE;
		this.imageIndex = 0;
		// -----------------------------------
	}

	@Override
	public void Hit(Aut_Direction d) {
		// TODO Auto-generated method stub
		super.Hit(d);
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
			idx += 28;
			break;
		case E:
			idx += 14;
			break;
		case W:
			idx += 42;
			break;
		default:
			break;
		}
		switch (this.action) {
		case M:
			idx+=1;
			break;
		case A:
			idx += 4;
			break;
		case H:
			idx += 8;
			break;
		case D:
			idx += 11;
			break;
		default :
			break;
		}
		return idx;
	}
}
