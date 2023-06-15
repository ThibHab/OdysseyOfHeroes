package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Egg extends Aut_Action {
	
	Aut_Direction dir;
	Aut_Category c;
	
	public Egg(Aut_Direction direction, Aut_Category category) {
		dir = direction;
		c = category;
	}

	@Override
	public void exec(Entity e, Game g) {
		e.Egg(dir, c);
	}

}
