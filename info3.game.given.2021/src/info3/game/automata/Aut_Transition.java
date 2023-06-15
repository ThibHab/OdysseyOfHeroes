package info3.game.automata;

public class Aut_Transition {

	Aut_State src;
	Cond condition;
	Aut_Action action;
	Aut_State dest;
	
	public Aut_Transition(Aut_State s, Cond c, Aut_Action a, Aut_State d) {
		this.src = s;
		this.condition = c;
		this.action = a;
		this.dest = d;
	}
	
	public void add_src_state(Aut_State s) {
		this.src = s;
	}
	
}
