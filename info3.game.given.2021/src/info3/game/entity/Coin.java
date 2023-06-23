package info3.game.entity;

import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Coin extends Item {
	static int CoinValue = 1;

	public Coin(Location l) {
		super();
		this.name = "Coin";
		this.location = l;

		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;

		Action acts[] = new Action[] { Action.S };
		this.anim = new Animation(this,ImagesConst.COIN, null, acts);

		this.scale = 1;
	}

	@Override
	public int getNbActionSprite(Action a) {
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
			return AnimConst.COIN_S;
		default:
			return 0;
		}
	}

	@Override
	public int totSrpitePerDir() {
		return AnimConst.COIN_S;
	}
}
