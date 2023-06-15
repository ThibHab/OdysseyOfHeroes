package info3.game.entity;

import info3.game.constants.ImagesConst;

public class HealingPotion extends Item{

	public HealingPotion(Location l) {
		super();
		this.name = "Heal";
		this.location = l;

		// --- TODO manage automaton ---
		this.automaton = null;
		this.currentState = null;
		// -----------------------------

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.HEALING_POTION;
		this.imageIndex = 0;
		// -----------------------------------
	}
}
