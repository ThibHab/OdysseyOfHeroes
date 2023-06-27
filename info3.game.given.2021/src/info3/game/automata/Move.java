package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Move extends Aut_Action {
		
	public Move(Aut_Direction direction, int percent) {
		super(direction, percent);
	}

	@Override
	public void exec(Entity e, Game g) {
		Aut_Direction dir = this.dir.rightDirection(e);
		e.Move(dir);
	}

}
