package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import info3.game.Game;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Category;
import info3.game.automata.Aut_Direction;
import info3.game.automata.Aut_State;
import info3.game.map.Map;

public abstract class Entity implements IEntity {
	public static int level;
	public static int experience;
	public static Game game;

	public Aut_Direction direction;
	public Aut_Category category;
	public Location location;
	public Aut_Automaton automaton;
	public Aut_State currentState;
	public String name;

	public BufferedImage[] sprites;
	public int imageIndex;
	public float scale;

	public int width, height, health, coins, weaponDamages, weaponRange, healingPotions, strengthPotions;
	public float speed;
	public boolean frozen;

	public Entity() {
		this.location = new Location(0, 0);
		this.currentState = null;
	}
	
	public void Tick(long elapsed) {
		if (!this.frozen) {
			this.automaton.step(this, Entity.game);
		}
	}

	@Override
	public void Move(Aut_Direction d) {
		this.frozen = true;
		
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
		
		this.frozen = false;
	}

	@Override
	public void Turn(Aut_Direction d) {
		if (d == null) {
			d = this.direction;
		}

		this.direction = d;
	}

	@Override
	public void Egg(Aut_Direction d, Aut_Category c) {
		if (d == null) {
			d = this.direction;
		}

		Random random = new Random();
		switch (c) {
		case A:
			new Goblin(Entity.game);
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
	public void Hit(Aut_Direction d) {
		Location t = frontTileLocation(d);

		Map map = (Map) this.game.map;
		Entity entity = map.map[(int) t.getX()][(int) t.getY()].entity;
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
		Map map = (Map) Entity.game.map;
		float xBaseIndex = this.location.getX() - 2, yBaseIndex = this.location.getY() - 2;
		for (int i = (int) xBaseIndex; i < xBaseIndex + 5; i++) {
			for (int j = (int) yBaseIndex; j < yBaseIndex + 5; j++) {
				Entity entity = map.map[i][j].entity;
				if (entity != null) {
					entity.health -= 5;
				}
			}
		}

		// TODO add explode method for animation (view)
	}

	@Override
	public void Pick(Aut_Direction d) {
		Location t = frontTileLocation(d);

		Map map = (Map) this.game.map;
		Entity entity = map.map[(int) t.getX()][(int) t.getY()].entity;
		if (entity.category == Aut_Category.P) {
			if (entity instanceof Coin) {
				this.coins++;
				// TODO destroy la coin
			} else if (entity instanceof HealingPotion) {
				this.healingPotions++;
			} else if (entity instanceof StrengthPotion) {
				this.strengthPotions++;
			}
		}
	}

	@Override
	public void Pop(Aut_Direction d, Aut_Category c) {
	}

	@Override
	public void Wizz(Aut_Direction d, Aut_Category c) {
	}

	@Override
	public void Power() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Store(Aut_Category c) {
	}

	@Override
	public void Throw(Aut_Direction d, Aut_Category category) {
		// TODO complete method

	}

	@Override
	public void Wait() {
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub

	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Location frontTileLocation(Aut_Direction d) {
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

		return new Location((int) xIndex, (int) yIndex);
	}
}
