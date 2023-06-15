package info3.game.entity;

import java.awt.image.BufferedImage;

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
		this.sprites =  new BufferedImage[]{ImagesConst.rock};
		this.imageIndex = 0;
		// -----------------------------------
	}
}
