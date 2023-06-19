package info3.game.entity;

import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Bush extends DecorElement {
	public static int BushHealth = 1;
	// TODO put static into constants file

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
		this.imageIndex = 1;
		// -----------------------------------
		
		this.width = 1;
		this.height = 1;
		
		this.scale = EntitiesConst.BUSH_SCALE;
	}

	// TODO is the default egg method sufficient ?
}
