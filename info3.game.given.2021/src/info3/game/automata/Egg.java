package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Egg extends Action {
	
	Direction dir;
	Category c;
	
	public Egg(Direction direction, Category category) {
		dir = direction;
		c = category;
	}

	@Override
	public void exec(Entity e, Game g) {
		e.Egg(dir, c);
	}

}
