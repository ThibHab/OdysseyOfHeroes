package info3.game.entity;

import info3.game.constants.ImagesConst;

public class House extends DecorElement {
	public House(Location l) {
		this.name = "House";
		this.location = l;

		// --- TODO manage automaton ---
		this.automaton = null;
		this.currentState = null;
		// -----------------------------

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.HOUSE;
		this.imageIndex = 0;
		// -----------------------------------
		
		this.width = 2;
		this.height = 2;
	}
}
