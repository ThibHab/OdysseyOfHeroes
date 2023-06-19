package info3.game.entity;

import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Category;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Chest extends DecorElement {
	public boolean opened;

	public Chest(Location l) {
		super();
		this.name = "Chest";
		this.location = l;
		this.opened = false;
		this.category = Aut_Category.P;

		// --- TODO manage automaton ---
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		// -----------------------------

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.CHEST;
		this.imageIndex = 0;
		// -----------------------------------
		
		this.width = 1;
		this.height = 1;
	}
}
