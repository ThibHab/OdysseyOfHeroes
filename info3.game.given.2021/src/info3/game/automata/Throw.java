package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Throw extends Action {
	
	Direction dir;
	Category cat;

	public Throw(Direction direction) {
		this.dir = direction;
	}

	@Override
	public void exec(Entity e, Game g) {
		e.Throw(dir, cat);
	}

}
