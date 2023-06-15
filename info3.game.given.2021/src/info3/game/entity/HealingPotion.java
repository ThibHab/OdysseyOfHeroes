package info3.game.entity;

import java.awt.image.BufferedImage;
import info3.game.automata.Category;
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
