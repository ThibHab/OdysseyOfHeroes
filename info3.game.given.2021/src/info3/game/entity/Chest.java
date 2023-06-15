package info3.game.entity;

import info3.game.constants.ImagesConst;

public class Chest extends DecorElement {
	public boolean opened;

	public Chest(Location l) {
		super();
		this.name = "Chest";
		this.location = l;
		this.opened = false;

		// --- TODO manage automaton ---
		this.automaton = null;
		this.currentState = null;
		// -----------------------------

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.CHEST;
		this.imageIndex = 0;
		// -----------------------------------
		
		this.Width = 1;
		this.Height = 1;
	}
	// TODO is the default egg method sufficient ?
}
