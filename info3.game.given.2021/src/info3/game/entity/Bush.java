package info3.game.entity;

import info3.game.automata.Aut_Automaton;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Bush extends DecorElement {
	public Bush(Location l) {
		super();
		this.name = "Bush";
		this.location = l;
		this.health = EntitiesConst.BUSH_HEALTH;

		// --- TODO manage automaton ---
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		// -----------------------------

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.BUSH;
		this.imageIndex = 1;
		// -----------------------------------
		
		this.width = 1;
		this.height = 1;
		
		if(this.location != null) {
			this.hitbox = new Hitbox(this, (float)0.90, (float)0.90);
		}
		
		this.scale = EntitiesConst.BUSH_SCALE;
	}
}
