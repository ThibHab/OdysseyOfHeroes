package info3.game.entity;

import info3.game.automata.Aut_Automaton;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class VillagerGirl extends Villager {

	public VillagerGirl(Location l) {
		super(l);
		this.name = "Villager";
		this.dialogs.add("Tue 50 orcs et je t'offirai 50 pièces \nd'or");
		// TODO Auto-generated constructor stub
		this.sprites = ImagesConst.VILLAGERGIRL;
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		
	}

}
