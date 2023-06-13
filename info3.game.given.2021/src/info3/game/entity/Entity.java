package info3.game.entity;

import java.awt.Graphics;

import info3.game.automata.Automaton;
import info3.game.automata.Category;
import info3.game.automata.Direction;
import info3.game.automata.State;

public abstract class Entity implements IEntity {
	public int width, height;
	public Location location;
	public Automaton automaton;
	public State currentState;
	public float speed;
	
	@Override
	public void Move(Direction d) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void Turn(Direction d) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void Egg(Direction d, Category c) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void Hit() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void Jump() {}
	
	@Override
	public void Explode() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void Pick(Category c) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void Pop(Direction d, Category c) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void Wizz(Direction d, Category c) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void Power() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void Store(Category c) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void Throw(Category c) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void Wait() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}
