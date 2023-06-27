package info3.game.entity;

import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.constants.Action;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Statue extends TransparentDecorElement {
	public Statue(Location l) {
		super(15);
		this.name = "Statue";
		this.location = l;

		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;

		Action acts[] = new Action[] { Action.S };
		this.anim = new Animation(this, ImagesConst.STATUE, null, acts);

		this.width = 5;
		this.height = 5;

		this.scale = EntitiesConst.STATUE_SCALE;
	}
}
