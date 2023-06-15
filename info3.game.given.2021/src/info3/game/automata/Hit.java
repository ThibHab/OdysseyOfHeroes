package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Hit extends Aut_Action {
	
	Aut_Direction dir;
	
	public Hit(Aut_Direction direction) {
		this.dir = direction;
	}

	@Override
	public void exec(Entity e, Game g) {
		e.Hit(dir);
	}

}
