package info3.game.automata;

public class Transition {

	State src;
	Condition condition;
	Action action;
	State dest;
	
	public Transition(State s, Condition c, Action a, State d) {
		this.src = s;
		this.condition = c;
		this.action = a;
		this.dest = d;
	}
}
