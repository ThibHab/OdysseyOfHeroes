package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class MyDir extends Aut_Condition {

	Aut_Direction dir;

	public MyDir(Aut_Direction direction) {
		this.dir = direction;
	}

	@Override
	public boolean eval(Entity e, Game g) {
		return (e.direction == this.dir);
	}

}
