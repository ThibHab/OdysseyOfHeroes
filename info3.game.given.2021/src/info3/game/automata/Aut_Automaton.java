package info3.game.automata;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import info3.game.Game;
import info3.game.entity.Entity;

public class Aut_Automaton {
	
	public LinkedList<Aut_Transition> transitions;
	public Aut_State initial;
	public String name;
	
	public Aut_Automaton(String name, Aut_State i, List<Aut_Transition> t) {
		this.initial = i;
		this.transitions = (LinkedList<Aut_Transition>) t;
		this.name = name;
	}
	
	public Aut_State step(Entity e, Game g) {
		if (!e.frozen) {
			Iterator<Aut_Transition> iter = transitions.iterator();
			Aut_Transition next;
			while (iter.hasNext()) {
				next = iter.next();
				if (next.src == e.currentState && next.condition.eval(e, g)) {
					next.action.exec(e, g);
					e.currentState = next.dest;
					return next.dest;
				}
			}
		}
		return null;
	}
}