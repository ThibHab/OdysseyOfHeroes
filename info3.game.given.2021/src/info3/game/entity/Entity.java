package info3.game.entity;

import java.awt.Graphics;
import java.util.Random;

import animations.Action;
import animations.Animation;
import info3.game.Game;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Category;
import info3.game.automata.Aut_Direction;
import info3.game.automata.Aut_State;
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
	public float speed, attackSpeed;
	public int coins, healingPotions, strengthPotions;

	public Aut_Automaton automaton;
	public Aut_State currentState;
	public Aut_Direction direction;
	public Aut_Category category;
	public boolean frozen;
	public boolean hitFrozen;
	public boolean moving;
	public long attackIndex;
	public int detectionRadius;

	public Animation anim;
	public float scale;
	public Hitbox hitbox;

	public Entity() {
		this.name = "";
		this.location = new Location(0, 0);
		this.destLocation = this.location;
		this.hitbox = new Hitbox(this, 1, 1);
		this.health = -1;
		this.weaponDamage = 1;
		this.weaponRange = 1;
		this.speed = 1;
		this.attackSpeed = 500;

		this.coins = 0;
		this.healingPotions = 0;
		this.strengthPotions = 0;

		this.automaton = null;
		this.currentState = null;

		this.direction = Aut_Direction.N;
		this.category = Aut_Category.UNDERSCORE;
		this.frozen = false;
		this.hitFrozen = false;
		this.moving = false;
		this.attackIndex = 0;

		this.scale = 1;
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
		this.anim.step(elapsed);
		if (this.frozen) {
			if (anim.action == Action.M) {
				if (this.anim.isFinished()) {
					this.moving = false;
					this.anim.resetAnim();
					this.location.setX(destLocation.getX());
					this.location.setY(destLocation.getY());
					this.hitbox.update();
					EntitiesConst.MAP_MATRIX[(int) this.originLocation.getX()][(int) this.originLocation
							.getY()].entity = null;
				} else if (anim.mouvementIndex != 0) {
					float progress = (float) this.anim.mouvementIndex / EntitiesConst.MOUVEMENT_INDEX_MAX;
					this.location
							.setX((this.originLocation.getX() + EntitiesConst.MAP.lenX + progress * relativeMouv.getX())
									% EntitiesConst.MAP.lenX);
					this.location
							.setY((this.originLocation.getY() + EntitiesConst.MAP.lenY + progress * relativeMouv.getY())
									% EntitiesConst.MAP.lenY);
					this.hitbox.update();
				}
			} else if (anim.action == Action.H) {
				this.attackIndex += elapsed;
				if (this.attackIndex >= this.attackSpeed) {
					this.anim.resetAnim();
					this.hitFrozen = false;
					this.attackIndex = 0;
				}
			}
		} else {
			this.anim.changeAction(Action.S);
		}
	}

	@Override
	public void Move(Aut_Direction d) {
		if (!this.frozen) {
			this.moving = true;
			this.anim.changeAction(Action.M);

			if (d == null) {
				d = this.direction;
			}

			this.destLocation = new Location(this.location.getX(), this.location.getY());
			originLocation = new Location(this.location.getX(), this.location.getY());
			relativeMouv = new Location(0, 0);
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
			if (destTile.walkable && destTile.entity == null && EntitiesConst.GAME.render.moveDooable(destLocation, d,
					EntitiesConst.GAME.m_canvas.getHeight(), EntitiesConst.GAME.m_canvas.getWidth())) {
				destTile.entity = this;
			} else {
				this.frozen = false;
			}
		} // else {
			// this.mouvementIndex = 0;
			// }
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
			}
		case AT:
			Random randomAT = new Random();
			int tirageAT = randomAT.nextInt(3);
			switch (tirageAT) {
			case 0:
				new Melee("melee", EntitiesConst.GAME);
				break;
			case 1:
				new Range("range", EntitiesConst.GAME);
				break;
			}
		default:
			break;
		}
	}

	@Override
	public void Hit(Aut_Direction d) {
		if (!this.frozen) {
			if (d == null) {
				d = this.direction;
			}
			this.anim.changeAction(Action.H);
			Location t = frontTileLocation(d);

			Entity entity = EntitiesConst.MAP_MATRIX[(int) t.getX()][(int) t.getY()].entity;
			if (entity != null) {
				switch (d) {
				case N:
					if (entity.hitbox.location.getY() + entity.hitbox.height > t.getY() + 0.5) {
						entity.takeDamage(this.weaponDamage);
					}
					break;
				case S:
					if (entity.hitbox.location.getY() < t.getY() + 0.5) {
						entity.takeDamage(this.weaponDamage);
					}
					break;
				case E:
					if (entity.hitbox.location.getX() < t.getX() + 0.5) {
						entity.takeDamage(this.weaponDamage);
					}
					break;
				case W:
					if (entity.hitbox.location.getX() + entity.hitbox.width > t.getX() + 0.5) {
						entity.takeDamage(this.weaponDamage);
					}
					break;
				default:
					break;
				}
			}
		}
	}

	public void takeDamage(int dmg) {
		System.out.println("HEHO CA FAIT MALEUH");
		if (this.health - dmg > 0) {
			this.health -= dmg;
			this.anim.changeAction(Action.T);
		} else {
			this.health = 0;
			this.die();
		}
	}

	public void die() {
		this.anim.changeAction(Action.D);
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
			this.health = -1;
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
			yIndex = (this.location.getY() + EntitiesConst.MAP.lenY - 1) % EntitiesConst.MAP.lenY;
			break;
		case S:
			xIndex = this.location.getX();
			yIndex = (this.location.getY() + EntitiesConst.MAP.lenY + 1) % EntitiesConst.MAP.lenY;
			break;
		case W:
			xIndex = (this.location.getX() + EntitiesConst.MAP.lenX - 1) % EntitiesConst.MAP.lenX;
			yIndex = this.location.getY();
			break;
		case E:
			xIndex = (this.location.getX() + EntitiesConst.MAP.lenX + 1) % EntitiesConst.MAP.lenX;
			yIndex = this.location.getY();
			break;
		default:
			break;
		}

		return new Location((int) xIndex, (int) yIndex);
	}

	public boolean hitboxOverlap(Entity tgt) {
		float x1 = this.hitbox.location.getX();
		float y1 = this.hitbox.location.getY();
		float X1 = tgt.hitbox.location.getX();
		float Y1 = tgt.hitbox.location.getY();
		float x2 = x1 + this.hitbox.width;
		float y2 = y1 + this.hitbox.height;
		float X2 = X1 + tgt.hitbox.width;
		float Y2 = Y1 + tgt.hitbox.height;
		switch (this.direction) {
		case S:
			return y2 > Y1 && x1 <= X2 && x2 >= X1;
		case E:
			return x2 > X1 && y1 <= Y2 && y2 >= Y1;
		case N:
			return y1 < Y2 && x1 <= X2 && x2 >= X1;
		case W:
			return x1 < X2 && y1 <= Y2 && y2 >= Y1;
		default:
			return false;
		}
	}

	public int getNbActionSprite(Action a) {
		return 1;
	}

	public int totSrpitePerDir() {
		return 1;
	}
}
