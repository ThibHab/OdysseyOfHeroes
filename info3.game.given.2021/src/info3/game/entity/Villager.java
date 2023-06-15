package info3.game.entity;

import info3.game.automata.*;
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
		this.category = Aut_Category.T;

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.VILLAGER;
		this.imageIndex = 0;
		// -----------------------------------
	}
}
