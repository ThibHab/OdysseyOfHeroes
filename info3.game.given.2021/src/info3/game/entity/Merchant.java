package info3.game.entity;

import info3.game.automata.Category;
import info3.game.constants.ImagesConst;

public class Merchant extends NPC {
	public Merchant(Location l) {
		super();
		this.name = "Merchant";
		this.location = l;

		// --- TODO manage automaton ---
		this.automaton = null;
		this.currentState = null;
		// -----------------------------
		this.category = Category.T;

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.merchant;
		this.imageIndex = 0;
		// -----------------------------------
	}
}
