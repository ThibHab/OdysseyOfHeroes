package info3.game.entity;

import animations.Action;
import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Direction;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class House extends DecorElement {
	public House(Location l) {
		super();
		this.name = "House";
		this.location = l;

		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		
		Action acts[] = new Action[] { Action.S };
		this.anim = new Animation(this,ImagesConst.HOUSE, null, acts);

		this.width = 3;
		this.height = 3;

		this.scale = EntitiesConst.HOUSE_SCALE;
	}
}
