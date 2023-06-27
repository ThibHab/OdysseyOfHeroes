package info3.game.entity;

import info3.game.automata.Aut_Category;

public abstract class DecorElement extends Entity {
	public int width, height;

	public DecorElement() {
		super();
		this.category = Aut_Category.O;
	}
}
