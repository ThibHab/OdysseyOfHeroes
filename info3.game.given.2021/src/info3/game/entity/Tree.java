package info3.game.entity;

import java.awt.image.BufferedImage;

import info3.game.constants.ImagesConst;

public class Tree extends DecorElement {
	public Tree(Location l) {
		super();
		this.name = "Tree";
		this.location = l;

		// --- TODO manage automaton ---
		this.automaton = null;
		this.currentState = null;
		// -----------------------------

		// --- TODO manage sprite properly ---
		this.sprites =  new BufferedImage[]{ImagesConst.tree};
		this.imageIndex = 0;
		// -----------------------------------
	}
}
