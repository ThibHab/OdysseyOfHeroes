package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Turn extends Aut_Action {
	
	Aut_Direction dir;

	public Turn(Aut_Direction direction, int percent) {
		this.percent = percent;
		if (percent == -1) {
			this.percent = 100;
		}
		this.dir = direction;
	}

	@Override
	public void exec(Entity e, Game g) {
		e.Turn(dir);

	}

}
