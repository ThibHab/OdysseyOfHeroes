package info3.game.entity;

import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Category;
import info3.game.constants.Action;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class BombRock extends DecorElement {
	public BombRock(Location l) {
		super();
		this.name = "BombRock";
		this.location = l;
		this.hitbox.update();
		
		this.health = 1;

		// --- TODO manage automaton ---
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		// -----------------------------

		Action acts[] = new Action[] { Action.S };
		this.anim = new Animation(this, ImagesConst.ROCK, null, acts);

		this.width = 1;
		this.height = 1;

		this.scale = EntitiesConst.ROCK_SCALE;

		this.category = Aut_Category.O;
		
	}

	@Override
	public void takeDamage(Entity attacker) {
		this.health = health - attacker.weaponDamage;
	}
}
