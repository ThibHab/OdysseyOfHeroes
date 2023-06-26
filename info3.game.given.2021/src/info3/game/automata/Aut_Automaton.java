package info3.game.automata;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import info3.game.Game;
import info3.game.entity.Entity;

public class Aut_Automaton implements java.lang.Cloneable{
	
	public LinkedList<Aut_Transition> transitions;
	public Aut_State initial;
	public String name;
	
	public Aut_Automaton(String name, Aut_State i, List<Aut_Transition> t) {
		this.initial = i;
		this.transitions = (LinkedList<Aut_Transition>) t;
		this.name = name;
	}
	
	public Aut_Automaton clone() throws CloneNotSupportedException {
		return (Aut_Automaton) super.clone();
	}
	
	public Aut_State step(Entity e, Game g) {
		if (!e.frozen) {
			for (Aut_Transition next_t : transitions) {
				if (next_t.src == e.currentState && next_t.condition.eval(e, g)) {
					Random r = new Random();
					int chance = r.nextInt(100);
					int a = 0;
					Aut_Action action = null;
					for (Aut_Action act : next_t.actions) {
						if (chance < a + act.percent) {
							action = act;
							break;
						}
						a += act.percent;
					}
					if (action != null)
						action.exec(e, g);
					e.currentState = next_t.dest;
					return next_t.dest;
				}
			}
		}
		return null;
	}
}