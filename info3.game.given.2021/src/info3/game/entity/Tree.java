package info3.game.entity;

import animations.Action;
import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Direction;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Tree extends DecorElement {
	public Tree(Location l) {
		super();
		this.name = "Tree";
		this.location = l;

		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;

		Action acts[] = new Action[] { Action.S };
		this.anim = new Animation(this,ImagesConst.TREE, null, acts);
		this.width = 2;
		this.height = 2;

		this.scale = EntitiesConst.TREE_SCALE;
	}
}
