package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Cell extends Condition {

	Direction dir;
	Category cat;

	public Cell(Direction direction, Category cat) {
		this.dir = direction;
		this.cat = cat;
	}

	@Override
	public boolean eval(Entity e, Game g) {
		// TODO Waiting for attribute tile in Entity and Map pushed
		return false;
	}

}
