package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Pop extends Aut_Action {
	
	Aut_Category cat;

	public Pop(Aut_Direction direction, Aut_Category category, int percent) {
		super(direction, percent);
		this.cat = category;
	}

	@Override
	public void exec(Entity e, Game g) {
		Aut_Direction dir = this.dir.rightDirection(e);
		e.Pop(dir, cat);

	}

}
