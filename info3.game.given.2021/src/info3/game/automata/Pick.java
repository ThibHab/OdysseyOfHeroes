package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Pick extends Action {
	
	Direction dir;

	public Pick(Direction direction) {
		this.dir = direction;
	}

	@Override
	public void exec(Entity e, Game g) {
		e.Pick(dir);
	}

}
