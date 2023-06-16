package info3.game.entity;

import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Rock extends DecorElement {
	public Rock(Location l) {
		super();
		this.name = "Rock";
		this.location = l;

		// --- TODO manage automaton ---
		this.automaton = null;
		this.currentState = null;
		// -----------------------------

		// --- TODO manage sprite properly ---
		this.sprites =  ImagesConst.ROCK;
		this.imageIndex = 0;
		// -----------------------------------
		
		this.width = 1;
		this.height = 1;
		
		this.scale = EntitiesConst.ROCK_SCALE;
	}
}
