package info3.game.entity;

import animations.Action;
import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Direction;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Bush extends DecorElement {
	public static int BushHealth = 1;

	public Bush(Location l) {
		super();
		this.name = "Bush";
		this.location = l;
		this.health = Bush.BushHealth;

		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;

		Action acts[] = new Action[] { Action.S, Action.H };
		this.anim = new Animation(this,ImagesConst.BUSH, null, acts);

		this.width = 1;
		this.height = 1;

		if (this.location != null) {
			this.hitbox = new Hitbox(this, (float) 0.90, (float) 0.90);
		}

		this.scale = EntitiesConst.BUSH_SCALE;
	}
	
	@Override
	public int getNbActionSprite(Action a) {
		//TODO
		switch (a) {
		case M:
			return 0;
		case H:
			return AnimConst.BUSH_H;
		case T:
			return 0;
		case D:
			return 0;
		case S:
			return AnimConst.BUSH_S;
		default:
			return 0;
		}
	}

	@Override
	public int totSrpitePerDir() {
		//TODO
		return AnimConst.BUSH_TOT;
	}
}
