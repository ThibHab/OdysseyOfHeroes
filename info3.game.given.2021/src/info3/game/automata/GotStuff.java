package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class GotStuff extends Aut_Condition {
	
	int id;

	public GotStuff(int id) {
		this.id = id;
	}

	@Override
	public boolean eval(Entity e, Game g) {
		switch(id) {
		case 1 :
			return e.healingPotions > 0;
		case 2 :
			return e.strengthPotions > 0;
		case 3 :
			return e.coins > 0;
		default :
			return false;
		}
	}

}
