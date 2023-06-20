package info3.game.automata;

import java.util.Iterator;

import info3.game.Game;
import info3.game.entity.Entity;

public class Aut_Key extends Aut_Condition {
	
	int key;
	
	public Aut_Key(int k) {
		this.key = k;
	}

	@Override
	public boolean eval(Entity e, Game g) {
		Iterator<Integer> iter = g.m_listener.keys.iterator();
		while (iter.hasNext()) {
			if (iter.next().equals(key))
				return true;
		}
		return false;
		
	}

}
