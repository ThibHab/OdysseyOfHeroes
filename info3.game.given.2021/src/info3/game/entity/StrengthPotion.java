package info3.game.entity;

import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class StrengthPotion extends Item {

	public StrengthPotion(Location l) {
		super();
		this.name = "Strength";
		this.location = l;

		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals("Coin"))
				automaton = next;
		}
		this.currentState = automaton.initial;

		Action acts[] = new Action[] { Action.S };
		this.anim = new Animation(this, ImagesConst.STRENGTH_POTION, null, acts);
		this.scale = 0.3f;
	}

	@Override
	public int getNbActionSprite(Action a) {
		switch (a) {
		case S:
			return AnimConst.STRENGTH_POTION_S;
		default:
			return 0;
		}
	}

	@Override
	public int totSrpitePerDir() {
		return AnimConst.STRENGTH_POTION_S;
	}
}
