package info3.game.entity;

import java.awt.Image;
import info3.game.automata.Category;
import info3.game.automata.Direction;

public abstract class Item extends Entity {
	protected Image image;

	public Item() {
		super();
		// TODO give automata and currentState when Items Automata is implemented
		this.automaton = null;
		this.currentState = null;
		this.speed = 0;
	}

	@Override
	public void Egg(Direction d, Category c) {
		// TODO Auto-generated method stub
		super.Egg(d, c);
	}

	@Override
	public void Pick(Direction d) {
		// TODO Auto-generated method stub
		super.Pick(d);
	}
}
