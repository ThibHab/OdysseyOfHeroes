package info3.game.entity;

import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Direction;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class HealingPotion extends Item {

	public HealingPotion(Location l) {
		super();
		this.name = "Heal";
		this.location = l;

		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals("Coin"))
				automaton = next;
		}
		this.currentState = automaton.initial;

		Action acts[] = new Action[] { Action.S };
		this.anim = new Animation(this,ImagesConst.HEALING_POTION, null, acts);
	}

	@Override
	public int getNbActionSprite(Action a) {
		// TODO
		switch (a) {
		case M:
			return 0;
		case H:
			return 0;
		case T:
			return 0;
		case D:
			return 0;
		case S:
			return AnimConst.HEALING_POTION_S;
		default:
			return 0;
		}
	}

	@Override
	public int totSrpitePerDir() {
		return AnimConst.HEALING_POTION_S;
	}
}
