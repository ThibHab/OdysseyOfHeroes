package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;

public class Cond extends Aut_Condition{
	
	Aut_Condition condition1;
	Aut_Condition condition2;
	String op;

	public Cond(Aut_Condition c1, Aut_Condition c2, String op) {
		this.condition1 = c1;
		this.condition2 = c2;
		this.op = op;
	}
	
	public boolean eval(Entity e, Game g) {
		switch(op) {
		case "!" :
			return !condition1.eval(e, g);
		case "/" :
			return (condition1.eval(e, g) || condition2.eval(e, g));
		case "&" :
			return (condition1.eval(e, g) && condition2.eval(e, g));
		default :
			return condition1.eval(e, g);
		}
	}

}
