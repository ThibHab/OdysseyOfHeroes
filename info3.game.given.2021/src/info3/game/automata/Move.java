package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Move extends Aut_Action {
	
	Aut_Direction dir;
	
	public Move(Aut_Direction direction, int percent) {
		this.percent = percent;
		if (percent == -1) {
			this.percent = 100;
		}
		dir = direction;
	}

	@Override
	public void exec(Entity e, Game g) {
		e.Move(dir);
	}

}
