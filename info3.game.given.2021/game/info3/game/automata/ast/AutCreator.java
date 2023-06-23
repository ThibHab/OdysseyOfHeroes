package info3.game.automata.ast;

import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import info3.game.automata.Aut_Action;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Category;
import info3.game.automata.Aut_Condition;
import info3.game.automata.Aut_Direction;
import info3.game.automata.Aut_Key;
import info3.game.automata.Aut_State;
import info3.game.automata.Aut_Transition;
import info3.game.automata.Cell;
import info3.game.automata.Closest;
import info3.game.automata.Cond;
import info3.game.automata.Egg;
import info3.game.automata.Explode;
import info3.game.automata.Get;
import info3.game.automata.GotPower;
import info3.game.automata.GotStuff;
import info3.game.automata.Hit;
import info3.game.automata.Move;
import info3.game.automata.MyDir;
import info3.game.automata.Pick;
import info3.game.automata.Pop;
import info3.game.automata.Power;
import info3.game.automata.Throw;
import info3.game.automata.True;
import info3.game.automata.Turn;
import info3.game.automata.Wait;
import info3.game.automata.Wizz;

public class AutCreator implements IVisitor {

	List<Aut_Transition> transitions;
	List<Aut_State> states;
	List<Aut_Action> actions;

	public AutCreator() {
	}

	@Override
	public Object visit(Category cat) {
		switch (cat.toString()) {
		case "A":
			return Aut_Category.A;
		case "C":
			return Aut_Category.C;
		case "D":
			return Aut_Category.D;
		case "G":
			return Aut_Category.G;
		case "J":
			return Aut_Category.J;
		case "M":
			return Aut_Category.M;
		case "O":
			return Aut_Category.O;
		case "P":
			return Aut_Category.P;
		case "T":
			return Aut_Category.T;
		case "V":
			return Aut_Category.V;
		case "_":
			return Aut_Category.UNDERSCORE;
		case "@":
			return Aut_Category.AT;
		}
		return null;
	}

	@Override
	public Object visit(Direction dir) {
		switch (dir.toString()) {
		case "N":
			return Aut_Direction.N;
		case "S":
			return Aut_Direction.S;
		case "E":
			return Aut_Direction.E;
		case "W":
			return Aut_Direction.W;
		case "F":
			return Aut_Direction.F;
		case "B":
			return Aut_Direction.B;
		case "L":
			return Aut_Direction.L;
		case "R":
			return Aut_Direction.R;
		case "H":
			return Aut_Direction.H;
		}
		return null;
	}

	@Override
	public Object visit(Key key) {
		switch(key.toString()) {
		case "FU" :
			return new Aut_Key(KeyEvent.VK_UP);
		case "FD" :
			return new Aut_Key(KeyEvent.VK_DOWN);
		case "FL" :
			return new Aut_Key(KeyEvent.VK_LEFT);
		case "FR" :
			return new Aut_Key(KeyEvent.VK_RIGHT);
		case "SPACE" :
			return new Aut_Key(KeyEvent.VK_SPACE);
		case "ENTER" :
			return new Aut_Key(KeyEvent.VK_ENTER);
		default :
			int ascii = (int)key.toString().toUpperCase().charAt(0);
			return new Aut_Key(ascii);
		}
	}

	@Override
	public Object visit(Value v) {
		return v.value;
	}

	@Override
	public Object visit(Underscore u) {
		return null;
	}

	@Override
	public void enter(FunCall funcall) {

	}

	@Override
	public Object exit(FunCall funcall, List<Object> parameters) {
		Aut_Action act = null;
		Aut_Direction dir = Aut_Direction.F;
		Aut_Category cat = null;
		int number = 0;

		for (Object param : parameters) {
			if (param instanceof Aut_Direction)
				dir = (Aut_Direction) param;
			else if (param instanceof Aut_Category)
				cat = (Aut_Category) param;
			else if (param instanceof Integer)
				number = (int) param;
		}

		switch (funcall.name) {
		case "True":
			return new True();
		case "Key":
			if (parameters.get(0) instanceof Aut_Key)
				return parameters.get(0);
			break;
		case "MyDir":
			return new MyDir(dir);
		case "Cell":
			return new Cell(dir, cat, number);
		case "GotPower":
			return new GotPower(number);
		case "GotStuff" :
			return new GotStuff(number);
		case "Closest" :
			return new Closest(dir, cat);

		case "Move":
			act = new Move(dir, funcall.percent);
			break;
		case "Egg":
			act = new Egg(dir, cat, funcall.percent, number);
			break;
		case "Hit":
			act = new Hit(dir, funcall.percent);
			break;
		case "Turn":
			act = new Turn(dir, funcall.percent);
			break;
		case "Wizz":
			act = new Wizz(dir, cat, funcall.percent);
			break;
		case "Pop":
			act = new Pop(dir, cat, funcall.percent);
			break;
		case "Explode":
			act = new Explode(funcall.percent);
			break;
		case "Get":
			act = new Get(funcall.percent);
			break;
		case "Pick":
			act = new Pick(dir, funcall.percent);
			break;
		case "Throw":
			act = new Throw(dir, cat, funcall.percent);
			break;
		case "Wait":
			act = new Wait(number, funcall.percent);
			break;
		case "Power":
			act = new Power(funcall.percent);
			break;
		}
		actions.add(act);
		return act;
	}

	@Override
	public Object visit(BinaryOp operator, Object left, Object right) {
		switch (operator.operator) {
		case "&":
			return new Cond((Aut_Condition) left, (Aut_Condition) right, "&");
		case "/":
			return new Cond((Aut_Condition) left, (Aut_Condition) right, "/");
		}
		return null;
	}

	@Override
	public Object visit(UnaryOp operator, Object expression) {
		if (operator.operator == "!")
			return new Cond((Aut_Condition) expression, null, "!");
		return null;
	}

	@Override
	public Object visit(State state) {
		for (Aut_State s : states) {
			if (state.toString().equals(s.name))
				return s;
		}
		Aut_State s = new Aut_State(state.toString());
		states.add(s);
		return s;
	}

	@Override
	public void enter(Mode mode) {

	}

	@Override
	public Object exit(Mode mode, Object source_state, Object behaviour) {
		List<Aut_Transition> AST_transitions = (List<Aut_Transition>) behaviour;
		Iterator<Aut_Transition> iter = AST_transitions.iterator();
		while (iter.hasNext()) {
			Aut_Transition next = iter.next();
			next.add_src_state((Aut_State) source_state);
		}
		return this.transitions;

	}

	@Override
	public Object visit(Behaviour behaviour, List<Object> transitions) {
		List<Aut_Transition> list = new LinkedList<Aut_Transition>();
		Iterator iter = transitions.iterator();
		while (iter.hasNext()) {
			Aut_Transition next = (Aut_Transition) iter.next();
			list.add(next);
		}
		return list;
	}

	@Override
	public void enter(Condition condition) {

	}

	@Override
	public Object exit(Condition condition, Object expression) {
		return expression;
	}

	@Override
	public void enter(Action acton) {
		actions = new LinkedList<Aut_Action>();

	}

	@Override
	public Object exit(Action action, List<Object> funcalls) {
		return null;
	}

	@Override
	public Object visit(Transition transition, Object condition, Object action, Object target_state) {
		if (condition instanceof Aut_Condition) {
			condition = new Cond((Aut_Condition) condition, null, "");
		}
		Aut_Transition t = new Aut_Transition(null, (Cond) condition, (List<Aut_Action>) actions,
				(Aut_State) target_state);
		transitions.add(t);
		return t;
	}

	@Override
	public void enter(Automaton automaton) {
		this.transitions = new LinkedList<Aut_Transition>();
		this.states = new LinkedList<Aut_State>();
	}

	@Override
	public Object exit(Automaton automaton, Object initial_state, List<Object> modes) {
		return new Aut_Automaton(automaton.toString(), (Aut_State) initial_state, this.transitions);
	}

	@Override
	public Object visit(AST bot, List<Object> automata) {
		List<Aut_Automaton> list = new LinkedList<Aut_Automaton>();
		Iterator iter = automata.iterator();
		while (iter.hasNext()) {
			Aut_Automaton next = (Aut_Automaton) iter.next();
			list.add(next);
		}
		return list;
	}

}
