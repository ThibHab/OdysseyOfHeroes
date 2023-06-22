package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Bomb;
import info3.game.entity.Entity;

public class GotPower extends Aut_Condition {
	
	int power;
	
	public GotPower(int p) {
		power = p;
	}

	@Override
	public boolean eval(Entity e, Game g) {
		if(e instanceof Bomb) {
			return ((Bomb)e).timer>0;
		}
		return (e.health > power);
	}

}
