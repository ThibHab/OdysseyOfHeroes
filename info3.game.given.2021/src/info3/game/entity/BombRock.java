package info3.game.entity;

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

		// --- TODO manage automaton ---
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		// -----------------------------

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.ROCK;
		this.imageIndex = 0;
		// -----------------------------------

		this.width = 1;
		this.height = 1;

		this.scale = EntitiesConst.ROCK_SCALE;
		
		this.category=Aut_Category.O;
	}
	
	public void takeDamage(int dmg) {
		System.out.println("HEHO CA FAIT MALEUH");
		if (this.health - dmg > 0) {
			this.health -= dmg;
		} else {
			this.health = 0;
			this.die();
		}
	}
	
	
}
