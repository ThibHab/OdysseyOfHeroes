package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Explode extends Aut_Action {

	public Explode(int percent) {
		super(null, percent);
	}

	@Override
	public void exec(Entity e, Game g) {
		e.Explode();

	}

}
