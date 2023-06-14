package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import info3.game.Game;
import info3.game.automata.Automaton;
import info3.game.automata.Category;
import info3.game.automata.Direction;
import info3.game.automata.State;
import info3.game.map.Map;

public abstract class Entity implements IEntity {
	public static int level;
	public static int experience;

	public Direction direction;
	public Category category;
	public Location location;
	public Automaton automaton;
	public State currentState;
	public Game game;	
	public String name;

	public BufferedImage[] sprites;
	public int imageIndex;
	public float scale;
	
	public int width, height, health, coins, weaponDamages, weaponRange;
	public float speed;
	public boolean frozen;

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
		if (d == null) {
			d = this.direction;
		}
		
		this.direction = d;
	}

	@Override
	public void Egg(Direction d, Category c) {
		if (d == null) {
			d = this.direction;
		}
		
		Random random = new Random();
		switch (c) {
		case A:
			new Goblin(this.game);
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
	public void Hit(Direction d) {
		if (d == null) {
			d = this.direction;
		}
		
		float xIndex = 0, yIndex = 0;
		switch (d) {
		case N:
			xIndex = this.location.getX();
			yIndex = this.location.getY() - 1;
			break;
		case S:
			xIndex = this.location.getX();
			yIndex = this.location.getY() + 1;
			break;
		case W:
			xIndex = this.location.getX() - 1;
			yIndex = this.location.getY();
			break;
		case E:
			xIndex = this.location.getX() + 1;
			yIndex = this.location.getY();
			break;
		default:
			break;
		}
		
		Map map = (Map) this.game.map;
		Entity entity = map.map[(int) xIndex][(int) yIndex].entity;
		if (entity != null) {
			entity.health--;
		}
		
		// TODO takeDamage method for animation (view) ?
	}

	@Override
	public void Jump() {
	}

	@Override
	public void Explode() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Pick(Direction d) {
		// TODO complete method

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
	public void Throw(Direction d, Category category) {
		// TODO complete method

	}

	@Override
	public void Wait() {
		// TODO Auto-generated method stub

	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub

	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
