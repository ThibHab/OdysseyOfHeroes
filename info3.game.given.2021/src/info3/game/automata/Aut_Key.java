package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Aut_Key extends Aut_Condition {

	int key;

	public Aut_Key(int k) {
		this.key = k;
	}

	@Override
	public boolean eval(Entity e, Game g) {
		for (Integer element : g.m_listener.keys) {
			if (element.equals(key))
				return true;
		}
		return false;

	}

}
