package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public abstract class Condition {

	public abstract boolean eval(Entity e, Game g);
	
}
