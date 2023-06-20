package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Wait extends Aut_Action {
	
	public Wait(int percent) {
		this.percent = percent;
		if (percent == -1) {
			this.percent = 100;
		}
	}

	@Override
	public void exec(Entity e, Game g) {
		e.Wait();

	}

}
