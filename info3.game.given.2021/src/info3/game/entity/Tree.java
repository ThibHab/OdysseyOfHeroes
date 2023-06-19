package info3.game.entity;

import info3.game.automata.Aut_Automaton;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Tree extends DecorElement {
	public Tree(Location l) {
		super();
		this.name = "Tree";
		this.location = l;

		// --- TODO manage automaton ---
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		// -----------------------------

		// --- TODO manage sprite properly ---
		this.sprites =  ImagesConst.TREE;
		this.imageIndex = 0;
		// -----------------------------------
		
		this.width = 2;
		this.height = 2;
		
		this.scale = EntitiesConst.TREE_SCALE;
	}
}
