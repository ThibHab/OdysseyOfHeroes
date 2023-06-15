package info3.game.entity;

import info3.game.automata.Category;
import info3.game.constants.ImagesConst;

public class Villager extends NPC {
	public Villager(Location l) {
		super();
		this.name = "Villager";
		this.location = l;

		// --- TODO manage automaton ---
		this.automaton = null;
		this.currentState = null;
		// -----------------------------
		this.category = Category.T;

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.VILLAGER;
		this.imageIndex = 0;
		// -----------------------------------
	}
}
