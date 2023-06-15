package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import info3.game.Game;
import info3.game.automata.Automaton;
import info3.game.automata.Category;
import info3.game.automata.Direction;
import info3.game.automata.State;
import info3.game.constants.EntitiesConst;
import info3.game.map.Map;
import info3.game.map.Tile;

public abstract class Entity implements IEntity {
	public String name;
	public Location location;
	public int health, weaponDamage, weaponRange;
	public float speed;

	public int coins, healingPotions, strengthPotions;

	public Automaton automaton;
	public State currentState;
	public Direction direction;
	public Category category;
	public boolean frozen;

	public BufferedImage[] sprites;
	public int imageIndex;
	public float scale;

	public Entity() {
		this.name = "";
		this.location = new Location(0, 0);
		this.health = -1;
		this.weaponDamage = 1;
		this.weaponRange = 1;
		this.speed = 1;

		this.coins = 0;
		this.healingPotions = 0;
		this.strengthPotions = 0;

		// --- TODO manage automaton ---
		this.automaton = null;
		this.currentState = null;
		// -----------------------------
		this.direction = Direction.N;
		this.category = Category.UNDERSCORE;
		this.frozen = false;

		this.scale = 1;
	}

	public static void InitStatics(Game g, int lvl, int xp) {
		EntitiesConst.GAME = g;
		EntitiesConst.MAP = (Map) g.map;
		EntitiesConst.MAP_MATRIX = EntitiesConst.MAP.map;
		EntitiesConst.LEVEL = lvl;
		EntitiesConst.EXPERIENCE = xp;
	}

	public void Tick(long elapsed) {
		if (!this.frozen) {
			this.automaton.step(this, EntitiesConst.GAME);
		}
	}

	@Override
	public void Move(Direction d) {
		this.frozen = true;

		if (d == null) {
			d = this.direction;
		}
		
		Location destLocation = new Location(this.location.getX(), this.location.getY());
		switch (d) {
		case N:
			//this.location.setY(this.location.getY() - 1);
			destLocation.setY((this.location.getY()+EntitiesConst.MAP.lenY-1)%EntitiesConst.MAP.lenY);
			break;
		case S:
			//this.location.setY(this.location.getY() + 1);
			destLocation.setY((this.location.getY()+EntitiesConst.MAP.lenY+1)%EntitiesConst.MAP.lenY);
			break;
		case W:
			//this.location.setX(this.location.getX() - 1);
			destLocation.setX((this.location.getX()+EntitiesConst.MAP.lenX-1)%EntitiesConst.MAP.lenX);
			break;
		case E:
			//this.location.setX(this.location.getX() + 1);
			destLocation.setX((this.location.getX()+EntitiesConst.MAP.lenX+1)%EntitiesConst.MAP.lenX);
			break;
		default:
			break;
		}
		
		Tile destTile = EntitiesConst.MAP_MATRIX[(int) destLocation.getX()][(int) destLocation.getY()];
		if (destTile.walkable && destTile.entity == null) {
			EntitiesConst.MAP_MATRIX[(int) this.location.getX()][(int) this.location.getY()].entity = null;
			destTile.entity = this;
			this.location.setX(destLocation.getX());
			this.location.setY(destLocation.getY());
		}

		this.frozen = false;
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

		Location location = this.frontTileLocation(d);
		Random random = new Random();
		int tirage = random.nextInt(7);
		switch (tirage) {
		case 0:
		case 1:
			new Goblin(location);
			break;
		case 2:
			new HealingPotion(location);
			break;
		case 3:
			new StrengthPotion(location);
			break;
		case 4:
		case 5:
		case 6:
			new Coin(location);
			break;
		default:
			break;
		}
	}

	@Override
	public void Hit(Direction d) {
		Location t = frontTileLocation(d);

		Entity entity = EntitiesConst.MAP_MATRIX[(int) t.getX()][(int) t.getY()].entity;
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
		float xBaseIndex = this.location.getX() - 2, yBaseIndex = this.location.getY() - 2;
		for (int i = (int) xBaseIndex; i < xBaseIndex + 5; i++) {
			for (int j = (int) yBaseIndex; j < yBaseIndex + 5; j++) {
				Entity entity = EntitiesConst.MAP_MATRIX[i][j].entity;
				if (entity != null) {
					entity.health -= 5;
				}
			}
		}

		// TODO delete destroyable rocks
		// TODO add explode method for animation (view)
	}

	@Override
	public void Pick(Direction d) {
		if (d == null) {
			d = this.direction;
		}

		Location t = frontTileLocation(d);

		Entity entity = EntitiesConst.MAP_MATRIX[(int) t.getX()][(int) t.getY()].entity;
		if (entity.category == Category.P) {
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
	public void Pop(Direction d, Category c) {
	}

	@Override
	public void Wizz(Direction d, Category c) {
	}

	@Override
	public void Power() {
		if (this.healingPotions > 0) {
			this.health = EntitiesConst.MAX_HEALTH;
			this.healingPotions--;
		}
	}

	@Override
	public void Store(Category c) {
	}

	@Override
	public void Throw(Direction d, Category category) {
	}

	@Override
	public void Wait() {
	}

	@Override
	public void paint(Graphics g, int TileSize, float screenPosX, float screenPosY) {
		// TODO Auto-generated method stub

	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Location frontTileLocation(Direction d) {
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
