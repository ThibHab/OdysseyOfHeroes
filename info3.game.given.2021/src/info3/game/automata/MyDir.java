package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class MyDir extends Condition {
	
	Direction dir;

	public MyDir(Direction direction) {
		this.dir = direction;
	}

	@Override
	public boolean eval(Entity e, Game g) {
		return (e.direction == this.dir);
	}

}
