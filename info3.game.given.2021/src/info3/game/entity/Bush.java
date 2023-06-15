package info3.game.entity;

import info3.game.constants.ImagesConst;

public class Bush extends DecorElement {
	public static int BushHealth = 1;

	public Bush(Location l) {
		super();
		this.name = "Bush";
		this.location = l;
		this.health = Bush.BushHealth;

		// --- TODO manage automaton ---
		this.automaton = null;
		this.currentState = null;
		// -----------------------------

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.BUSH;
		this.imageIndex = 0;
		// -----------------------------------
	}

	// TODO is the default egg method sufficient ?
}
