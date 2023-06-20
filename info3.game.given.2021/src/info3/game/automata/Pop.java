package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Pop extends Aut_Action {
	
	Aut_Direction dir;
	Aut_Category cat;

	public Pop(Aut_Direction direction, Aut_Category category, int percent) {
		this.percent = percent;
		if (percent == -1) {
			this.percent = 100;
		}
		this.dir = direction;
		this.cat = category;
	}

	@Override
	public void exec(Entity e, Game g) {
		e.Pop(dir);

	}

}
