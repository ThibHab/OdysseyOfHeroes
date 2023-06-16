package info3.game.automata.ast;

import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import info3.game.automata.*;

public class AutCreator implements IVisitor {

	List<Aut_Transition> transitions;
	List<Aut_State> states;
	List<Aut_Action> actions;

	public AutCreator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object visit(Category cat) {
		// TODO Auto-generated method stub
		switch (cat.toString()) {
		case "A":
			return Aut_Category.A;
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
		// TODO Auto-generated method stub
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
		switch (key.toString()) {
		case "o":
			return new Aut_Key(KeyEvent.VK_O);
		case "k":
			return new Aut_Key(KeyEvent.VK_K);
		case "l":
			return new Aut_Key(KeyEvent.VK_L);
		case "m":
			return new Aut_Key(KeyEvent.VK_M);
		case "i":
			return new Aut_Key(KeyEvent.VK_I);
		case "z":
			return new Aut_Key(KeyEvent.VK_Z);
		case "s":
			return new Aut_Key(KeyEvent.VK_S);
		case "q":
			return new Aut_Key(KeyEvent.VK_Q);
		case "d":
			return new Aut_Key(KeyEvent.VK_D);
		case "a":
			return new Aut_Key(KeyEvent.VK_A);
		}
		return null;
	}

	@Override
	public Object visit(Value v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Underscore u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enter(FunCall funcall) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object exit(FunCall funcall, List<Object> parameters) {
		// TODO Auto-generated method stub
		Aut_Action act = null;
		switch (funcall.name) {
		case "True":
			return new True();
		case "Key":
			if (parameters.get(0) instanceof Aut_Key)
				return parameters.get(0);
		case "MyDir":
			if (parameters.get(0) instanceof Aut_Direction)
				return new MyDir((Aut_Direction) parameters.get(0));
		case "Cell":
			if (parameters.get(0) instanceof Aut_Direction && parameters.get(1) instanceof Aut_Category)
				return new Cell((Aut_Direction) parameters.get(0), (Aut_Category) parameters.get(1));
		case "Move":
			if (parameters.get(0) instanceof Aut_Direction)
				act = new Move((Aut_Direction) parameters.get(0), funcall.percent);
			else
				act = new Move(Aut_Direction.F, funcall.percent);
			break;
		case "Egg":
			if (parameters.get(0) instanceof Aut_Direction && parameters.get(1) instanceof Aut_Category)
				act = new Egg((Aut_Direction) parameters.get(0), (Aut_Category) parameters.get(1), funcall.percent);
			else if (parameters.get(0) instanceof Aut_Category)
				act = new Egg(Aut_Direction.F, (Aut_Category) parameters.get(0), funcall.percent);
			break;
		case "Hit":
			if (parameters.get(0) instanceof Aut_Direction)
				act = new Hit((Aut_Direction) parameters.get(0), funcall.percent);
			else
				act = new Hit(Aut_Direction.F, funcall.percent);
			break;
		case "Turn":
			if (parameters.get(0) instanceof Aut_Direction)
				act = new Turn((Aut_Direction) parameters.get(0), funcall.percent);
			else
				act = new Turn(Aut_Direction.F, funcall.percent);
			break;
		}
		actions.add(act);
		return act;
	}

	@Override
	public Object visit(BinaryOp operator, Object left, Object right) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub

	}

	@Override
	public Object exit(Mode mode, Object source_state, Object behaviour) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub

	}

	@Override
	public Object exit(Condition condition, Object expression) {
		// TODO Auto-generated method stub
		return expression;
	}

	@Override
	public void enter(Action acton) {
		actions = new LinkedList<Aut_Action>();

	}

	@Override
	public Object exit(Action action, List<Object> funcalls) {
		// TODO Auto-generated method stub
		return (Aut_Action) funcalls.get(0);
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
		// TODO Auto-generated method stub
		this.transitions = new LinkedList<Aut_Transition>();
		this.states = new LinkedList<Aut_State>();
	}

	@Override
	public Object exit(Automaton automaton, Object initial_state, List<Object> modes) {
//		List<Aut_Transition> transitions = new LinkedList<Aut_Transition>();
//		Iterator iter = modes.iterator();
//		while (iter.hasNext()) {
//			transitions.add((Aut_Transition)iter.next());
//		}

		return new Aut_Automaton(automaton.toString(), (Aut_State) initial_state, this.transitions);
	}

	@Override
	public Object visit(AST bot, List<Object> automata) {
		// TODO Auto-generated method stub
		List<Aut_Automaton> list = new LinkedList<Aut_Automaton>();
		Iterator iter = automata.iterator();
		while (iter.hasNext()) {
			Aut_Automaton next = (Aut_Automaton) iter.next();
			list.add(next);
		}
		return list;
	}

}
