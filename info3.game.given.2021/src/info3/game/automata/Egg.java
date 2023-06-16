package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Egg extends Aut_Action {
	
	Aut_Direction dir;
	Aut_Category c;
	int id;
	
	public Egg(Aut_Direction direction, Aut_Category category, int percent, int id) {
		this.percent = percent;
		if (percent == -1) {
			this.percent = 100;
		}
		dir = direction;
		c = category;
		this.id = id;
	}

	@Override
	public void exec(Entity e, Game g) {
		e.Egg(dir, c);
	}

}
