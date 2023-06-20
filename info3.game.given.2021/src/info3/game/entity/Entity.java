package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import info3.game.constants.Action;
import info3.game.constants.AnimConst;
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
	public float speed, attackSpeed;
	public int coins, healingPotions, strengthPotions;

	public Aut_Automaton automaton;
	public Aut_State currentState;
	public Aut_Direction direction;
	public Aut_Category category;
	public boolean frozen;
	public boolean hitFrozen;
	public boolean moving;
	public long mouvementIndex;
	public long attackIndex;
	public Action action;
	public int detectionRadius;

	public BufferedImage[] sprites;
	public int imageIndex;
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

		// --- TODO manage automaton ---
		this.automaton = null;
		this.currentState = null;
		// -----------------------------
		this.direction = Aut_Direction.N;
		this.category = Aut_Category.UNDERSCORE;
		this.frozen = false;
		this.hitFrozen = false;
		this.moving = false;
		this.mouvementIndex = 0;
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
		// TODO : step only if not frozen, then remove if not frozen in move
		this.automaton.step(this, EntitiesConst.GAME);
		if (this.frozen) {
			this.mouvementIndex += elapsed;
			if (this.action == Action.M) {
				if (mouvementIndex % 200 == 0) {
					this.updateSpriteIndex();
				}
				if (this.mouvementIndex >= EntitiesConst.MOUVEMENT_INDEX_MAX) {
					this.frozen = false;
					this.moving = false;
					this.mouvementIndex = 0;
					this.location.setX(destLocation.getX());
					this.location.setY(destLocation.getY());
					this.hitbox.update();
					EntitiesConst.MAP_MATRIX[(int) this.originLocation.getX()][(int) this.originLocation
							.getY()].entity = null;
				} else if (mouvementIndex != 0) {
					float progress = (float) this.mouvementIndex / EntitiesConst.MOUVEMENT_INDEX_MAX;
					this.location
							.setX((this.originLocation.getX() + EntitiesConst.MAP.lenX + progress * relativeMouv.getX())
									% EntitiesConst.MAP.lenX);
					this.location
							.setY((this.originLocation.getY() + EntitiesConst.MAP.lenY + progress * relativeMouv.getY())
									% EntitiesConst.MAP.lenY);
					this.hitbox.update();
				}
			} else if (this.action == Action.H) {
				if (mouvementIndex % 50 == 0) {
					this.updateSpriteIndex();
				}
				this.attackIndex += elapsed;
				if (this.attackIndex >= this.attackSpeed) {
					this.frozen = false;
					this.hitFrozen = false;
					this.mouvementIndex = 0;
					this.attackIndex = 0;
				}
			}
		} else if (this.action != Action.S) {
			System.out.println(this.name + " is standing");
			this.action = Action.S;
			this.imageIndex = this.sprites.length - 1;
			this.updateSpriteIndex();
		}
//		if (this.hitFrozen) {
//			this.attackIndex += elapsed;
//			if (this.attackIndex >= this.attackSpeed) {
//				this.hitFrozen = false;
//				this.attackIndex = 0;
//			}
//		}
	}

	@Override
	public void Move(Aut_Direction d) {
		if (!this.frozen) {
			this.frozen = true;
			this.moving = true;

			if (d == null) {
				d = this.direction;
			}
			if (this.action != Action.M) {
				System.out.println(this.name + " is moving");
				this.action = Action.M;
				this.imageIndex = this.sprites.length;
				this.updateSpriteIndex();
			}

			this.destLocation = new Location(this.location.getX(), this.location.getY());
			originLocation = new Location(this.location.getX(), this.location.getY());
			relativeMouv = new Location(0, 0);
			switch (this.direction) {
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
			this.frozen = true;
			if (this.action != Action.H) {
				System.out.println(this.name + " hits");
				this.imageIndex = this.sprites.length;
				this.action = Action.H;
				this.updateSpriteIndex();
			}
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
			if (this.action != Action.T) {
				System.out.println(this.name + " is touched");
				this.imageIndex = this.sprites.length;
				this.action = Action.T;
				this.updateSpriteIndex();
			}
		} else {
			this.health = 0;
			this.die();
		}
	}

	public void die() {
		if (this.action != Action.D) {
			this.imageIndex = this.sprites.length;
			this.action = Action.D;
			this.updateSpriteIndex();

			System.out.println(this.name + " is diing");
		}
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

	public void updateSpriteIndex() {
	}

	public int getHitNbSprite() {
		return 1;
	}

	public int getMvmtNbSprite() {
		return 1;
	}

	public int getStandNbSprite() {
		return 1;
	}

	public int getDieNbSprite() {
		return 1;
	}

	public int getTouchedNbSprite() {
		return 1;
	}
}
