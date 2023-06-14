package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public abstract class Action {

	public abstract void exec(Entity e, Game g);
}
