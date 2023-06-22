package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Egg extends Aut_Action {
	
	Aut_Category c;
	int id;
	
	public Egg(Aut_Direction direction, Aut_Category category, int percent, int id) {
		super(direction, percent);
		c = category;
		this.id = id;
	}

	@Override
	public void exec(Entity e, Game g) {
		Aut_Direction dir = this.dir.rightDirection(e);
		e.Egg(dir, c, id);
	}

}
