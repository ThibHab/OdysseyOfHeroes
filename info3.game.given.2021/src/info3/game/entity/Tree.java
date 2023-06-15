package info3.game.entity;

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
		this.sprites =  ImagesConst.TREE;
		this.imageIndex = 0;
		// -----------------------------------
	}
}
