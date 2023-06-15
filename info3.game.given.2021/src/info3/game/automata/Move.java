package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Move extends Aut_Action {
	
	Aut_Direction dir;
	
	public Move(Aut_Direction direction) {
		dir = direction;
	}

	@Override
	public void exec(Entity e, Game g) {
		e.Move(dir);
	}

}
