package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Wizz extends Aut_Action {

	Aut_Direction dir;
	Aut_Category cat;

	public Wizz(Aut_Direction direction, Aut_Category category) {
		this.dir = direction;
		this.cat = category;
	}

	@Override
	public void exec(Entity e, Game g) {
		e.Wizz(dir, cat);

	}

}
