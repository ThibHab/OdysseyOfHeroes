package info3.game.automata;

import java.util.List;

public class Aut_Transition {

	Aut_State src;
	Cond condition;
	List<Aut_Action> actions;
	Aut_State dest;
	
	public Aut_Transition(Aut_State s, Cond c, List<Aut_Action> a, Aut_State d) {
		this.src = s;
		this.condition = c;
		this.actions = a;
		this.dest = d;
	}
	
	public void add_src_state(Aut_State s) {
		this.src = s;
	}
	
}
