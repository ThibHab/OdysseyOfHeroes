package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Move extends Action {
	
	Direction dir;
	
	public Move(Direction direction) {
		dir = direction;
	}

	@Override
	public void exec(Entity e, Game g) {
		e.Move(dir);
	}

}
