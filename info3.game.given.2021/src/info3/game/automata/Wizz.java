package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Wizz extends Action {

	Direction dir;
	Category cat;

	public Wizz(Direction direction, Category category) {
		this.dir = direction;
		this.cat = category;
	}

	@Override
	public void exec(Entity e, Game g) {
		e.Wizz(dir, cat);

	}

}
