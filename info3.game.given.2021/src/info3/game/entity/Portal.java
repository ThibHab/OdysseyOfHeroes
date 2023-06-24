package info3.game.entity;

import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Category;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Portal extends DecorElement {

	public Portal(Location l) {
		super();
		this.name = "Portal";
		this.location = l;
		this.health = Integer.MAX_VALUE;
		this.category = Aut_Category.O;

		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;

		Action acts[] = new Action[] { Action.S };
		this.anim = new Animation(this, ImagesConst.PORTAL, null, acts);
	}
//	
//	@Override
//	public int getNbActionSprite(Action a) {
//		switch (a) {
//		case M:
//			return 0;
//		case H:
//			return 0;
//		case T:
//			return 0;
//		case D:
//			return 0;
//		case S:
//			return AnimConst.PORTAL_S;
//		default:
//			return 0;
//		}
//	}
//
//	@Override
//	public int totSrpitePerDir() {
//		return AnimConst.PORTAL_S;
//	}

}
