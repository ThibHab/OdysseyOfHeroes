package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public abstract class Aut_Action {

	int percent;
	Aut_Direction dir;

	public Aut_Action(Aut_Direction direction, int percent) {
		this.percent = percent;
		if (percent == -1) {
			this.percent = 100;
		}

		dir = direction;
	}
	

	public abstract void exec(Entity e, Game g);
}
