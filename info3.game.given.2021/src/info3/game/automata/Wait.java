package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Wait extends Aut_Action {

	int time;

	public Wait(int time, int percent) {
		super(null, percent);
		this.time = time;
	}

	@Override
	public void exec(Entity e, Game g) {
		e.Wait(time);

	}

}
