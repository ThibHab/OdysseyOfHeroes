package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import info3.game.Game;
import info3.game.automata.*;
import info3.game.constants.EntitiesConst;
import info3.game.map.Map;
import info3.game.map.Tile;

public abstract class Entity implements IEntity {
	public String name;
	public Location location;
	public Location destLocation;
	public Location relativeMouv;
	public Location originLocation;
	public int health, weaponDamage, weaponRange;
	public float speed;

	public Aut_Automaton automaton;
	public Aut_State currentState;
	public Aut_Direction direction;
	public Aut_Category category;
	public int coins, healingPotions, strengthPotions;
	public boolean frozen;
	public long mouvementIndex;

	public BufferedImage[] sprites;
	public int imageIndex;
	public float scale;
	public float ratioHitBoxX;
	public float ratioHitBoxY;
	
	public Location hitBoxLocation;
	public Location destHitBoxLocation;

	public Entity() {
		this.name = "";
		this.location = new Location(0, 0);
		this.hitBoxLocation = new Location(0,0);
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
		this.direction = Aut_Direction.N;
		this.category = Aut_Category.UNDERSCORE;
		this.frozen = false;
		this.mouvementIndex = 0;

		this.scale = 1;
		
		this.ratioHitBoxX = (float)0.50;
		this.ratioHitBoxY = (float)0.75;
		
		this.hitBoxLocation.setX((float)(location.getX() + (1 - this.ratioHitBoxX)/2));
		this.hitBoxLocation.setY((float)(location.getY() + (1 - this.ratioHitBoxY)/2));
	}

	public static void InitStatics(Game g, int lvl, int xp) {
		EntitiesConst.GAME = g;
		EntitiesConst.MAP = (Map) g.map;
		EntitiesConst.MAP_MATRIX = EntitiesConst.MAP.map;
		EntitiesConst.LEVEL = lvl;
		EntitiesConst.EXPERIENCE = xp;
	}

	public void tick(long elapsed) {
		this.automaton.step(this, EntitiesConst.GAME);
		if (this.frozen) {
			this.mouvementIndex += elapsed;
			if (this.mouvementIndex >= EntitiesConst.MOUVEMENT_INDEX_MAX) {
				this.frozen = false;
				this.mouvementIndex = 0;
				this.location.setX(destLocation.getX());
				this.location.setY(destLocation.getY());
				this.hitBoxLocation.setX((float)(location.getX() + (1 - this.ratioHitBoxX)/2));
				this.hitBoxLocation.setY((float)(location.getY() + (1 - this.ratioHitBoxY)/2));
			} else {
				if (mouvementIndex != 0) {
					float progress = (float) this.mouvementIndex / EntitiesConst.MOUVEMENT_INDEX_MAX;
					this.location.setX((this.originLocation.getX() + EntitiesConst.MAP.lenX + progress * relativeMouv.getX())
							% EntitiesConst.MAP.lenX);
					this.location.setY((this.originLocation.getY() + EntitiesConst.MAP.lenY + progress * relativeMouv.getY())
							% EntitiesConst.MAP.lenY);
					this.hitBoxLocation.setX((float)(location.getX() + (1 - this.ratioHitBoxX)/2));
					this.hitBoxLocation.setY((float)(location.getY() + (1 - this.ratioHitBoxY)/2));
				}
			}
		}
	}

	@Override
	public void Move(Aut_Direction d) {

		if (!this.frozen) {
			this.frozen = true;
			if (d == null) {
				d = this.direction;
			}

			destLocation = new Location(this.location.getX(), this.location.getY());
			originLocation=new Location(this.location.getX(), this.location.getY());
			relativeMouv=new Location(0,0);
			switch (d) {
			case N:
				destLocation.setY((this.location.getY() + EntitiesConst.MAP.lenY - 1) % EntitiesConst.MAP.lenY);
				relativeMouv.setY(-1);
				break;
			case S:
				destLocation.setY((this.location.getY() + EntitiesConst.MAP.lenY + 1) % EntitiesConst.MAP.lenY);
				relativeMouv.setY(1);
				break;
			case W:
				destLocation.setX((this.location.getX() + EntitiesConst.MAP.lenX - 1) % EntitiesConst.MAP.lenX);
				relativeMouv.setX(-1);
				break;
			case E:
				destLocation.setX((this.location.getX() + EntitiesConst.MAP.lenX + 1) % EntitiesConst.MAP.lenX);
				relativeMouv.setX(1);
				break;
			default:
				break;
			}
			Tile destTile = EntitiesConst.MAP_MATRIX[(int) destLocation.getX()][(int) destLocation.getY()];
			if (destTile.walkable && destTile.entity == null) {
				EntitiesConst.MAP_MATRIX[(int) this.location.getX()][(int) this.location.getY()].entity = null;
				destTile.entity = this;
			} else {
				this.frozen = false;
			}

		} else {
			this.mouvementIndex = 0;
		}

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

		Location location = this.frontTileLocation(d);
		switch (c) {
		case A:
			Random randomA = new Random();
			int tirageA = randomA.nextInt(2);
			switch (tirageA) {
			case 0:
				new Goblin(location);
				break;
			case 1:
				new Skeleton(location);
				break;
			}
		case M:
			break;
		case P:
			Random randomP = new Random();
			int tirageP = randomP.nextInt(3);
			switch (tirageP) {
			case 0:
				new Coin(location);
				break;
			case 1:
				new HealingPotion(location);
				break;
			case 2:
				new StrengthPotion(location);
				break;
			}
		case T:
			Random randomT = new Random();
			int tirageT = randomT.nextInt(3);
			switch (tirageT) {
			case 0:
				new Villager(location);
				break;
			case 1:
				new Merchant(location);
				break;
			}
		case AT:
			Random randomAT = new Random();
			int tirageAT = randomAT.nextInt(3);
			switch (tirageAT) {
			case 0:
				new Melee("melee", location);
				break;
			case 1:
				new Range("range", location);
				break;
			}
		}
	}

	@Override
	public void Hit(Aut_Direction d) {
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
	public void Pick(Aut_Direction d) {
		if (d == null) {
			d = this.direction;
		}

		Location t = frontTileLocation(d);

		Entity entity = EntitiesConst.MAP_MATRIX[(int) t.getX()][(int) t.getY()].entity;
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
		if (this.healingPotions > 0) {
			this.health = EntitiesConst.MAX_HEALTH;
			this.healingPotions--;
		}
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
	public void paint(Graphics g, int TileSize, float screenPosX, float screenPosY) {
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
	
	public boolean hitboxOverlap(Entity tgt) {
		float x1 = this.hitBoxLocation.getX();
		float y1 = this.hitBoxLocation.getY();
		float X1 = tgt.hitBoxLocation.getX();
		float Y1 = tgt.hitBoxLocation.getY();
		float x2 = x1 + this.ratioHitBoxX;
		float y2 = y1 + this.ratioHitBoxY;
		float X2 = X1 + tgt.ratioHitBoxX;
		float Y2 = Y1 + tgt.ratioHitBoxY;
		switch(this.direction) {
		case S:
			return x1 > X1 && X1 > x2;
		case E:
			return y1 > Y1 && Y1 > y2;
		case N:
			return x1 < X2 && X2 < x2;
		case W:
			return y1 < Y2 && Y2 < y2;
		default:
			return false;
		}
	}
}
