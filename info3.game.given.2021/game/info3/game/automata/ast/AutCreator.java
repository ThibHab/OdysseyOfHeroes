package info3.game.automata.ast;

import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import info3.game.automata.*;

public class AutCreator implements IVisitor {
	
	Aut_Category cat;
	Aut_Direction dir;
	Aut_Key key;
	Aut_State workingState;
	List<Aut_Transition> transitions;
	List<Aut_State> states;

	public AutCreator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object visit(Category cat) {
		// TODO Auto-generated method stub
		switch(cat.toString()) {
		case "A":
			this.cat = Aut_Category.A;
			break;
		case "P":
			this.cat = Aut_Category.P;
			break;
		case "T":
			this.cat = Aut_Category.T;
			break;
		case "V":
			this.cat = Aut_Category.V;
			break;
		case "_":
			this.cat = Aut_Category.UNDERSCORE;
			break;
		case "@":
			this.cat = Aut_Category.AT;
			break;
		}
		return this.cat;
	}

	@Override
	public Object visit(Direction dir) {
		// TODO Auto-generated method stub
		switch(dir.toString()) {
		case "N":
			this.dir = Aut_Direction.N;
			break;
		case "S":
			this.dir = Aut_Direction.S;
			break;
		case "E":
			this.dir = Aut_Direction.E;
			break;
		case "W":
			this.dir = Aut_Direction.W;
			break;
		case "F":
			this.dir = Aut_Direction.F;
			break;
		case "B":
			this.dir = Aut_Direction.B;
			break;
		case "L":
			this.dir = Aut_Direction.L;
			break;
		case "R":
			this.dir = Aut_Direction.R;
			break;
		case "H":
			this.dir = Aut_Direction.H;
			break;
		}
		return this.dir;
	}

	@Override
	public Object visit(Key key) {
		switch(key.toString()) {
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
			return new Aut_Key(KeyEvent.VK_S);
		case "s":
			return new Aut_Key(KeyEvent.VK_Q);
		case "q":
			return new Aut_Key(KeyEvent.VK_D);
		case "d":
			return new Aut_Key(KeyEvent.VK_F);
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
		switch(funcall.name) {
		case "True" :
			return new True();
		case "Key" :
			if (parameters.get(0) instanceof Aut_Key)
				return parameters.get(0);
		case "MyDir" :
			if (parameters.get(0) instanceof Aut_Direction)
				return new MyDir((Aut_Direction)parameters.get(0));
		case "Cell" :
			if (parameters.get(0) instanceof Aut_Direction && parameters.get(1) instanceof Aut_Category)
				return new Cell((Aut_Direction)parameters.get(0), (Aut_Category)parameters.get(1));
		case "Move" :
			if (parameters.get(0) instanceof Aut_Direction)
				return new Move((Aut_Direction)parameters.get(0));
			return new Move(Aut_Direction.F);
		case "Egg" :
			if (parameters.get(0) instanceof Aut_Direction && parameters.get(1) instanceof Aut_Category)
				return new Egg((Aut_Direction)parameters.get(0), (Aut_Category)parameters.get(1));
			if (parameters.get(0) instanceof Aut_Category)
				return new Egg(Aut_Direction.F, (Aut_Category)parameters.get(0));
		case "Hit" :
			if (parameters.get(0) instanceof Aut_Direction)
				return new Hit((Aut_Direction)parameters.get(0));
			return new Hit(Aut_Direction.F);
		case "Turn" :
			if (parameters.get(0) instanceof Aut_Direction)
				return new Turn((Aut_Direction)parameters.get(0));
			return new Turn(Aut_Direction.F);
		}
		return null;
	}

	@Override
	public Object visit(BinaryOp operator, Object left, Object right) {
		// TODO Auto-generated method stub
		switch(operator.toString()) {
		case "&":
			return new Cond((Aut_Condition)left, (Aut_Condition)right, "&");
		case "/":
			return new Cond((Aut_Condition)left, (Aut_Condition)right, "/");
		}
		return null;
	}

	@Override
	public Object visit(UnaryOp operator, Object expression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(State state) {
		// TODO Auto-generated method stub
		for(Aut_State s : states) {
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
		List<Aut_Transition> AST_transitions = (List<Aut_Transition>)behaviour;
		Iterator<Aut_Transition> iter = AST_transitions.iterator();
		while(iter.hasNext()) {
			Aut_Transition next = iter.next();
			next.add_src_state((Aut_State)source_state);
		}
		return this.transitions;
		
	}

	@Override
	public Object visit(Behaviour behaviour, List<Object> transitions) {
		// TODO Auto-generated method stub
		List<Aut_Transition> list = new LinkedList<Aut_Transition>();
		Iterator iter = transitions.iterator();
		while (iter.hasNext()) {
			Aut_Transition next = (Aut_Transition)iter.next();
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
		return (Aut_Condition)expression;
	}

	@Override
	public void enter(Action acton) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object exit(Action action, List<Object> funcalls) {
		// TODO Auto-generated method stub
		return (Aut_Action)funcalls.get(0);
	}

	@Override
	public Object visit(Transition transition, Object condition, Object action, Object target_state) {
		if (condition instanceof Aut_Condition) {
			condition = new Cond((Aut_Condition)condition, null, "");
		}
		Aut_Transition t = new Aut_Transition(null, (Cond) condition, (Aut_Action) action, (Aut_State) target_state);
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
		
		return new Aut_Automaton(automaton.toString(), (Aut_State)initial_state, this.transitions);
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
