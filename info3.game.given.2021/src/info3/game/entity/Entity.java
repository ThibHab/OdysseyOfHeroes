package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.Game;
import info3.game.automata.*;
import info3.game.constants.EntitiesConst;
import info3.game.constants.MapConstants;
import info3.game.map.DungeonMap;
import info3.game.map.Map;
import info3.game.map.MapRender;
import info3.game.map.MazeMap;
import info3.game.map.Tile;
import info3.game.map.WorldMap;

public abstract class Entity implements IEntity {
	public String name;
	public Location location;
	public Location destLocation;
	public Location relativeMouv;
	public Location originLocation;
	public int health, weaponDamage, weaponRange;
	public float speed, attackSpeed;
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
	public int maxHealth;
	public int range;
	public int healingPotions, strengthPotions;
	public int mazeCounter;
	public boolean mazeCounterActivated;

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
		this.range = 0;
		this.healingPotions = EntitiesConst.HEALING_POTIONS;
		this.strengthPotions = EntitiesConst.STRENGTH_POTIONS;

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

		this.mazeCounter = 0;
		this.mazeCounterActivated = false;
	}

	public static void InitStatics(Game g, int lvl, int xp) {
		EntitiesConst.GAME = g;
		EntitiesConst.MAP = (Map) g.map;
		EntitiesConst.MAP_MATRIX = EntitiesConst.MAP.map;
	}

	public void tick(long elapsed) {
		if (this.mazeCounterActivated) {
			this.mazeCounter += elapsed;
			if (this.mazeCounter >= EntitiesConst.MAZE_COUNTER_LIMIT) {
				EntitiesConst.GAME.player1.action = Action.S;
				EntitiesConst.GAME.player2.action = Action.S;
				EntitiesConst.GAME.map = new WorldMap(100, 100, EntitiesConst.GAME.player1, EntitiesConst.GAME.player2);
				EntitiesConst.GAME.render = new MapRender((Map) EntitiesConst.GAME.map, EntitiesConst.GAME);
				EntitiesConst.GAME.render.updateCam(EntitiesConst.GAME.player1, EntitiesConst.GAME.player2, EntitiesConst.GAME.m_canvas.getWidth(), EntitiesConst.GAME.m_canvas.getHeight());
				EntitiesConst.GAME.render.setOffsetCam();
				this.mazeCounterActivated = false;
				this.mazeCounter = 0;
			}
		}

		this.automaton.step(this, EntitiesConst.GAME);
		if (this.frozen) {
			this.mouvementIndex += elapsed;
			if (this.action == Action.M) {
				if ((mouvementIndex - elapsed)
						/ (EntitiesConst.MOUVEMENT_INDEX_MAX / this.getMvmtNbSprite()) < mouvementIndex
								/ (EntitiesConst.MOUVEMENT_INDEX_MAX / this.getMvmtNbSprite())) {
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
				if ((mouvementIndex - elapsed) / (EntitiesConst.HIT_INDEX_MAX / this.getHitNbSprite()) < mouvementIndex
						/ (EntitiesConst.HIT_INDEX_MAX / this.getHitNbSprite())) {
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
		} else {
			if (this.action != Action.S) {
				if (EntitiesConst.GAME.debug) {
					System.out.println(this.name + " is standing");
				}
				this.action = Action.S;
				this.imageIndex = this.sprites.length - 1;
				this.updateSpriteIndex();
			}
			this.action = Action.S;
			this.imageIndex = this.sprites.length;
			this.updateSpriteIndex();
		}
		if ((mouvementIndex - elapsed) / (EntitiesConst.STAND_INDEX_MAX / this.getStandNbSprite()) < mouvementIndex
				/ (EntitiesConst.STAND_INDEX_MAX / this.getStandNbSprite())) {
			this.updateSpriteIndex();
		}
	}

	@Override
	public void Move(Aut_Direction d) {
		if (!this.frozen) {
			this.frozen = true;
			this.moving = true;

			if (d == null) {
				d = this.direction;
			}
			this.direction = d;

			if (this.action != Action.M) {
				if (EntitiesConst.GAME.debug) {
					System.out.println(this.name + " is moving");
				}
				this.action = Action.M;
				this.imageIndex = this.sprites.length;
				this.updateSpriteIndex();
			}

			this.destLocation = new Location(this.location.getX(), this.location.getY());
			this.originLocation = new Location(this.location.getX(), this.location.getY());
			this.relativeMouv = new Location(0, 0);
			switch (d) {
			case N:
				this.destLocation.setY((this.location.getY() + EntitiesConst.MAP.lenY - 1) % EntitiesConst.MAP.lenY);
				this.relativeMouv.setY(-1);
				break;
			case S:
				this.destLocation.setY((this.location.getY() + EntitiesConst.MAP.lenY + 1) % EntitiesConst.MAP.lenY);
				this.relativeMouv.setY(1);
				break;
			case W:
				this.destLocation.setX((this.location.getX() + EntitiesConst.MAP.lenX - 1) % EntitiesConst.MAP.lenX);
				this.relativeMouv.setX(-1);
				break;
			case E:
				this.destLocation.setX((this.location.getX() + EntitiesConst.MAP.lenX + 1) % EntitiesConst.MAP.lenX);
				this.relativeMouv.setX(1);
				break;
			default:
				break;
			}

			Tile destTile = EntitiesConst.MAP_MATRIX[(int) destLocation.getX()][(int) destLocation.getY()];
			if ((destTile.entity instanceof DungeonEntrance || destTile.entity instanceof MazeEntrance) && this.direction == Aut_Direction.N) {
				if (destTile.entity instanceof DungeonEntrance) {
					EntitiesConst.GAME.map = new DungeonMap(32, 32, EntitiesConst.GAME.player1, EntitiesConst.GAME.player2);
					
				} else if (destTile.entity instanceof MazeEntrance) {
					EntitiesConst.GAME.map = new MazeMap(
							MapConstants.MAZE_MAP_SIZE * (MapConstants.MAZE_MAP_CORRIDOR_SIZE + 1) + 1,
							MapConstants.MAZE_MAP_SIZE * (MapConstants.MAZE_MAP_CORRIDOR_SIZE + 1) + 1,
							EntitiesConst.GAME.player1, EntitiesConst.GAME.player2);
					this.mazeCounterActivated = true;
				}
				
				EntitiesConst.GAME.render = new MapRender((Map) EntitiesConst.GAME.map, EntitiesConst.GAME);
				EntitiesConst.GAME.render.updateCam(EntitiesConst.GAME.player1, EntitiesConst.GAME.player2, EntitiesConst.GAME.m_canvas.getWidth(), EntitiesConst.GAME.m_canvas.getHeight());
				EntitiesConst.GAME.render.setOffsetCam();
			}

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
	public void Egg(Aut_Direction d, Aut_Category c, int id) {
		// TODO garder l'aléatoire si id pas reconnu mais sinon faire en fonction de
		// l'id
		// (id à partir de 1 pour ce qui a un sens : se mettre d'accord sur la
		// signification de chaque nombre)
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
		case D:
			new Bomb(location, this);
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
		// TODO Melee blocked when touching an enemy, also see for the hits in the
		// border of the maps
		if (!this.frozen) {
			this.frozen = true;
			if (this.action != Action.H) {
				if (EntitiesConst.GAME.debug) {
					System.out.println(this.name + " hits");
				}
				this.imageIndex = this.sprites.length;
				this.action = Action.H;
				this.updateSpriteIndex();
			}
			Location t = frontTileLocation(d);

			Entity entity = EntitiesConst.MAP_MATRIX[(int) t.getX()][(int) t.getY()].entity;
			if (entity != null) {
				switch (d) {
				case N:
					if ((entity.hitbox.location.getY() + entity.hitbox.height > t.getY() - 0.5)
							&& entity.category != Aut_Category.O) {
						entity.takeDamage(this);
					}
					break;
				case S:
					if ((entity.hitbox.location.getY() < t.getY() + 0.5) && entity.category != Aut_Category.O) {
						entity.takeDamage(this);
					}
					break;
				case E:
					if ((entity.hitbox.location.getX() < t.getX() + 0.5) && entity.category != Aut_Category.O) {
						entity.takeDamage(this);
					}
					break;
				case W:
					if ((entity.hitbox.location.getX() + entity.hitbox.width > t.getX() - 0.5)
							&& entity.category != Aut_Category.O) {
						entity.takeDamage(this);
					}
					break;
				default:
					break;
				}
			}
		}
	}

	public void takeDamage(Entity attacker) {
		System.out.println("HEHO CA FAIT MALEUH");
		if (this.health - attacker.weaponDamage > 0) {
			this.health -= attacker.weaponDamage;
		} else {
			this.health = 0;
			this.die(attacker);
		}
	}

	public void die(Entity attacker) {
		if (this.action != Action.D) {
			this.imageIndex = this.sprites.length;
			this.action = Action.D;
			this.updateSpriteIndex();

			if (attacker.category == Aut_Category.AT && this.category != Aut_Category.D) {
				Hero.addExperience(attacker);
			}

			if (EntitiesConst.GAME.debug) {
				System.out.println(this.name + " has died");
			}
			EntitiesConst.MAP_MATRIX[(int) this.location.getX()][(int) this.location.getY()].entity = null;
		}
	}

	public void updateStats() {
	}

	@Override
	public void Jump() {
		// TODO write function not usable ?
	}

	@Override
	public void Explode() {
	}

	@Override
	public void Pick(Aut_Direction d) {
		if (d == null) {
			d = this.direction;
		}

		Location location = frontTileLocation(d);
		Entity entity = EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity;
		if (entity.category == Aut_Category.P) {
			if (entity instanceof Coin) {
				// TODO destroy the coin
			} else if (entity instanceof HealingPotion) {
				// TODO destroy the healingPotion
			} else if (entity instanceof StrengthPotion) {
				// TODO destroy the strengthPotion
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
	}

	@Override
	public void Store(Aut_Category c) {
	}

	@Override
	public void Throw(Aut_Direction d, Aut_Category category) {
		// TODO complete method

	}

	@Override
	public void Wait(int time) {
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
		float x1, x2, X1, X2, y1, y2, Y1, Y2;
		if (tgt instanceof DecorElement) {
			x1 = this.hitbox.location.getX();
			y1 = this.hitbox.location.getY();
			X1 = this.destLocation.getX();
			Y1 = this.destLocation.getY();
			x2 = (x1 + this.hitbox.width + EntitiesConst.MAP.lenX) % EntitiesConst.MAP.lenX;
			y2 = (y1 + this.hitbox.height + EntitiesConst.MAP.lenY) % EntitiesConst.MAP.lenY;
			X2 = (X1 + tgt.hitbox.width + EntitiesConst.MAP.lenX) % EntitiesConst.MAP.lenX;
			Y2 = (Y1 + tgt.hitbox.height + EntitiesConst.MAP.lenY) % EntitiesConst.MAP.lenY;
		} else {
			x1 = this.hitbox.location.getX();
			y1 = this.hitbox.location.getY();
			X1 = tgt.hitbox.location.getX();
			Y1 = tgt.hitbox.location.getY();
			x2 = (x1 + this.hitbox.width + EntitiesConst.MAP.lenX) % EntitiesConst.MAP.lenX;
			y2 = (y1 + this.hitbox.height + EntitiesConst.MAP.lenY) % EntitiesConst.MAP.lenY;
			X2 = (X1 + tgt.hitbox.width + EntitiesConst.MAP.lenX) % EntitiesConst.MAP.lenX;
			Y2 = (Y1 + tgt.hitbox.height + EntitiesConst.MAP.lenY) % EntitiesConst.MAP.lenY;
		}
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
		imageIndex = 0;
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

	public boolean circleIntersect(Location bomb, Entity ent, float radius) {
		Map map = EntitiesConst.MAP;
		float w = ent.hitbox.width;
		float h = ent.hitbox.height;
		Location hg = ent.hitbox.location;
		Location hd = map.add(hg, new Location(w, 0));
		Location bg = map.add(hg, new Location(0, h));
		Location bd = map.add(hg, new Location(w, h));

		Location circleCenter = map.add(bomb, new Location(0.5f, 0.5f));

		return (map.dist(hg, circleCenter) <= radius) || (map.dist(hd, circleCenter) <= radius)
				|| (map.dist(bg, circleCenter) <= radius) || (map.dist(bd, circleCenter) <= radius);

	}
}
