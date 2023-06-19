package info3.game.entity;

import info3.game.automata.*;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Goblin extends Mob {
	public Goblin(Location l) {
		super();
		this.name = "Goblin";
		this.location = l;
		this.health = EntitiesConst.GOBLIN_HEALTH;
		this.weaponDamage = EntitiesConst.GOBLIN_DAMAGE;
		this.weaponRange = EntitiesConst.GOBLINE_RANGE;
		this.speed = EntitiesConst.GOBLIN_SPEED;

		// --- TODO manage automaton ---
		this.automaton = null;
		this.currentState = null;
		// -----------------------------
		this.category = Aut_Category.A;

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.GOBLIN;
		this.imageIndex = 0;
		// -----------------------------------
	}

	@Override
	public void Hit(Aut_Direction d) {
		// TODO Auto-generated method stub
		super.Hit(d);
	}
}
