package info3.game.entity;

import java.awt.image.BufferedImage;

import info3.game.constants.ImagesConst;

public class StrengthPotion extends Item {

	public StrengthPotion(Location l) {
		super();
		this.name = "Strength";
		this.location = l;

		// --- TODO manage automaton ---
		this.automaton = null;
		this.currentState = null;
		// -----------------------------

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.STRENGTH_POTION;
		this.imageIndex = 0;
		// -----------------------------------
	}
}
