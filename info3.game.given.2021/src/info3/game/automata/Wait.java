package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Wait extends Aut_Action {
	
	int time;
	
	public Wait(int percent, int time) {
		this.percent = percent;
		if (percent == -1) {
			this.percent = 100;
		}
		if (time == 0)
			time = 1;
		this.time = time;
	}

	@Override
	public void exec(Entity e, Game g) {
		e.Wait(time);

	}

}
