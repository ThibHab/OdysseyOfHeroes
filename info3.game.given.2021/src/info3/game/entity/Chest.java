package info3.game.entity;

import animations.Action;
import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Direction;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
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

		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;

		Action acts[] = new Action[] { Action.S,  Action.H};
		this.anim = new Animation(this,ImagesConst.CHEST, null, acts);
		
		this.width = 1;
		this.height = 1;
	}
	
	@Override
	public int getNbActionSprite(Action a) {
		switch (a) {
		case M:
			return 0;
		case H:
			return AnimConst.CHEST_H;
		case T:
			return 0;
		case D:
			return 0;
		case S:
			return AnimConst.CHEST_S;
		default:
			return 0;
		}
	}

	@Override
	public int totSrpitePerDir() {
		return AnimConst.CHEST_TOT;
	}
}
