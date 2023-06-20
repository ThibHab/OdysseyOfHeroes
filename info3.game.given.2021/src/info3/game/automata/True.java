package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class True extends Aut_Condition {

	@Override
	public boolean eval(Entity e, Game g) {
		return true;
	}

}
