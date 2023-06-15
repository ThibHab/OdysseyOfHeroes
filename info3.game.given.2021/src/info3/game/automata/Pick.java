package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Pick extends Aut_Action {
	
	Aut_Direction dir;

	public Pick(Aut_Direction direction) {
		this.dir = direction;
	}

	@Override
	public void exec(Entity e, Game g) {
		e.Pick(dir);
	}

}
