package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Throw extends Aut_Action {

	Aut_Category cat;

	public Throw(Aut_Direction direction, Aut_Category category, int percent) {
		super(direction, percent);
		this.cat = category;
	}

	@Override
	public void exec(Entity e, Game g) {
		Aut_Direction dir = this.dir.rightDirection(e);
		e.Throw(dir, cat);
	}

}
