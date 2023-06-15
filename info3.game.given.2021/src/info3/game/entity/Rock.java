package info3.game.entity;

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
		
		this.Width = 1;
		this.Height = 1;
	}
}
