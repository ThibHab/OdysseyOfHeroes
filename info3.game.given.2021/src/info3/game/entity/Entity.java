package info3.game.entity;

import java.awt.Graphics;
import java.util.Random;

import info3.game.automata.Automaton;
import info3.game.automata.Category;
import info3.game.automata.Direction;
import info3.game.automata.State;

public abstract class Entity implements IEntity {
	public int width, height, health;
	public Location location;
	public Automaton automaton;
	public State currentState;
	public float speed;
	public Direction direction;
	public Category category;

	public Entity() {
		this.location = new Location(0, 0);
		this.currentState = null;
	}

	@Override
	public void Move(Direction d) {
		if (d == null) {
			d = this.direction;
		}
		
		switch (d) {
		case N:
			this.location.setY(this.location.getY() - 1);
			break;
		case S:
			this.location.setY(this.location.getY() + 1);
			break;
		case W:
			this.location.setX(this.location.getX() - 1);
			break;
		case E:
			this.location.setX(this.location.getX() + 1);
			break;
		default:
			break;
		}
	}

	@Override
	public void Turn(Direction d) {
		this.direction = d;

	}

	@Override
	public void Egg(Direction d, Category c) {
		Random random = new Random();
		switch (c) {
		case A:
			new Goblin();
			break;
		case P:
			// TODO add coin and potion instance creation
			break;
		case M:
			// TODO add projectile drop
			break;
		default:
			break;
		}
	}

	@Override
	public void Hit() {		//TODO mettre une direction
		
	}

	@Override
	public void Jump() {
	}

	@Override
	public void Explode() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Pick(Category c) {
		// TODO mettre une direction à la place de la categorie

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
		// TODO ajouter un parametre direction avant category

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
