package info3.game.entity;

import info3.game.automata.Category;
import info3.game.automata.Direction;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;
import info3.game.constants.StatesConst;

public class Melee extends Hero {
	public Melee(String name, Location l) {
		super();
		this.name = name;
		this.location = l;
		this.weaponDamage = EntitiesConst.MELEE_DAMAGE;
		this.weaponRange = EntitiesConst.MELEE_RANGE;
		this.health = EntitiesConst.MELEE_HEALTH;

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
	public void Hit(Direction d) {
		// TODO Auto-generated method stub
		super.Hit(d);
	}

	@Override
	public void Pop(Direction d, Category c) {
		// TODO Auto-generated method stub
		super.Pop(d, c);
	}

	@Override
	public void Wizz(Direction d, Category c) {
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
