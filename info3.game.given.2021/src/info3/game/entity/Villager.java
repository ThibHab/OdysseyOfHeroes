package info3.game.entity;

import animations.Action;
import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Category;
import info3.game.automata.Aut_Direction;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Villager extends NPC {
	public Villager(Location l) {
		super();
		this.name = "Villager";
		this.location = l;

		this.category = Aut_Category.T;

		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals("Coin"))
				automaton = next;
		}
		this.currentState = automaton.initial;

		Aut_Direction dirs[] = new Aut_Direction[] {};
		Action acts[] = new Action[] {};
		this.anim = new Animation(this,ImagesConst.VILLAGER, dirs, acts);
	}
}
