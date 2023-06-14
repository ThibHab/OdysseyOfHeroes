package info3.game.automata;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import info3.game.Game;
import info3.game.entity.Entity;

public class Automaton {
	
	LinkedList<Transition> transitions;
	State initial;
	String name;
	
	public Automaton(String name, State i, List<Transition> t) {
		this.initial = i;
		this.transitions = (LinkedList<Transition>) t;
		this.name = name;
	}
	
	public State step(Entity e, Game g) {
		Iterator<Transition> iter = transitions.iterator();
		Transition next;
		while (iter.hasNext()) {
			next = iter.next();
			if (next.src == e.currentState && next.condition.eval(e, g)) {
				next.action.exec(e, g);
				e.currentState = next.dest;
				return next.dest;
			}
		}
		return null;
	}
}