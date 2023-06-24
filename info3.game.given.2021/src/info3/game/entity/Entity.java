package info3.game.entity;

import java.awt.Graphics;
import java.util.Random;

import animations.Animation;
import animations.BloodEffect;
import animations.Effect;
import animations.GroundEffect;
import animations.SpearEffect;
import animations.SwordEffect;
import info3.game.Game;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Category;
import info3.game.automata.Aut_Direction;
import info3.game.automata.Aut_State;
import info3.game.constants.Action;
import info3.game.constants.EntitiesConst;
import info3.game.constants.MapConstants;
import info3.game.map.DungeonMap;
import info3.game.map.Map;
import info3.game.map.MapRender;
import info3.game.map.MazeMap;
import info3.game.map.Tile;

public abstract class Entity implements IEntity {
	public String name;
	public int health, weaponDamage, weaponRange, maxHealth, range;
	public float speed, attackSpeed;
	public Aut_Category category;
	public int healingPotions, strengthPotions, bombs;
	public boolean dead;

	public Aut_Automaton automaton;
	public Aut_State currentState;
	public Aut_Direction direction;
	public boolean frozen, hitFrozen;
	public int timer;

	public Location originLocation, location, destLocation, relativeMouv;

	public long actionIndex;
	public int detectionRadius;
	public Hitbox hitbox;
	public int mazeCounter;
	public boolean mazeCounterActivated;

	public Animation anim;
	public float scale;
	public Action action;

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
		this.bombs = 0;

		// TODO assign default automaton
		this.automaton = null;
		this.currentState = null;
		this.timer = Integer.MIN_VALUE;

		this.direction = Aut_Direction.S;
		this.category = Aut_Category.UNDERSCORE;
		this.frozen = false;
		this.hitFrozen = false;
		this.actionIndex = 0;
		this.dead = false;

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
		if (currentState.name.equals("")) {
			this.die();
		}
		this.automaton.step(this, EntitiesConst.GAME);

		if (this.frozen) {
			this.actionIndex += elapsed;
			if (action == Action.M) {
				if (this.isFinished()) {
					this.actionIndex = 0;
					this.frozen = false;
					this.location.setX(destLocation.getX());
					this.location.setY(destLocation.getY());
					this.hitbox.update();
					EntitiesConst.MAP_MATRIX[(int) this.originLocation.getX()][(int) this.originLocation
							.getY()].entity = null;
				} else if (actionIndex != 0) {
					float progress = (float) this.actionIndex / EntitiesConst.MOUVEMENT_INDEX_MAX;
					this.location
							.setX((this.originLocation.getX() + EntitiesConst.MAP.lenX + progress * relativeMouv.getX())
									% EntitiesConst.MAP.lenX);
					this.location
							.setY((this.originLocation.getY() + EntitiesConst.MAP.lenY + progress * relativeMouv.getY())
									% EntitiesConst.MAP.lenY);
					this.hitbox.update();
				}
			} else if (action == Action.H) {
				if (this.isFinished()) {
					this.frozen = false;
					this.actionIndex = 0;
				}
				if (this.actionIndex >= this.attackSpeed) {
					this.hitFrozen = false;
				}
			} else if (action == Action.I) {
				if (this.isFinished()) {
					this.frozen = false;
					this.actionIndex = 0;
				}
//			} else if (action == Action.T) {
//				if (this.isFinished()) {
//					this.frozen = false;
//					this.actionIndex = 0;
////					if (this.health == 0)
////						this.die();
//				}
			} else if (action == Action.D) {
				if (this.isFinished()) {
					this.actionIndex = 0;
					this.dead = true;
					if (!(this instanceof Hero))
						EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity = null;

				}
			} else if (timer != Integer.MIN_VALUE) {
				this.timer -= elapsed;
				if (timer < 0) {
					this.frozen = false;
					timer = Integer.MIN_VALUE;
					waited();
				}
			}
		} else {
			if (this.action != Action.S) {
				if (EntitiesConst.GAME.debug) {
					System.out.println(this.name + " is standing");
				}
				this.action = Action.S;
				this.anim.changeAction(action);
			}
			if (!this.dead)
				this.anim.changeAction(action);
		}
		if (!this.dead)
			this.anim.step(elapsed);

	}

	@Override
	public void Move(Aut_Direction d) {
		if (!this.frozen) {
			this.frozen = true;
			if (d != null) {
				this.direction = d;
			}
			this.direction = d;

			if (this.action != Action.M) {
				this.action = Action.M;
			}
			this.anim.changeAction(action);

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
			if (this instanceof Hero
					&& (destTile.entity instanceof DungeonEntrance || destTile.entity instanceof MazeEntrance)
					&& this.direction == Aut_Direction.N) {
				if (destTile.entity instanceof DungeonEntrance) {
					EntitiesConst.GAME.previousMap = 2;
					EntitiesConst.MAP_MATRIX[EntitiesConst.DUNGEON_ENTRANCE_X_POS][EntitiesConst.DUNGEON_ENTRANCE_Y_POS
							+ 1].entity = null;
					EntitiesConst.GAME.map = new DungeonMap(40, 40, EntitiesConst.GAME.player1,
							EntitiesConst.GAME.player2);
				} else if (destTile.entity instanceof MazeEntrance) {
					EntitiesConst.GAME.previousMap = 1;
					EntitiesConst.MAP_MATRIX[EntitiesConst.MAZE_ENTRANCE_X_POS][EntitiesConst.MAZE_ENTRANCE_Y_POS
							+ 1].entity = null;
					EntitiesConst.GAME.map = new MazeMap(
							MapConstants.MAZE_MAP_SIZE * (MapConstants.MAZE_MAP_CORRIDOR_SIZE + 1) + 1,
							MapConstants.MAZE_MAP_SIZE * (MapConstants.MAZE_MAP_CORRIDOR_SIZE + 1) + 1,
							EntitiesConst.GAME.player1, EntitiesConst.GAME.player2);
					EntitiesConst.GAME.player1.mazeCounterActivated = true;
				}

				EntitiesConst.GAME.render = new MapRender(EntitiesConst.MAP, EntitiesConst.GAME);
				EntitiesConst.GAME.render.updateCam(EntitiesConst.GAME.player1, EntitiesConst.GAME.player2,
						EntitiesConst.GAME.m_canvas.getWidth(), EntitiesConst.GAME.m_canvas.getHeight());
				EntitiesConst.GAME.render.setOffsetCam();
			}

			if (destTile.walkable && destTile.entity == null && EntitiesConst.GAME.render.moveDooable(destLocation, d,
					EntitiesConst.GAME.m_canvas.getHeight(), EntitiesConst.GAME.m_canvas.getWidth())) {
				destTile.entity = this;
			} else {
				this.frozen = false;
			}
		}
	}

	@Override
	public void Turn(Aut_Direction d) {
		if (d != null) {
			this.direction = d;
			this.anim.changeAction(action);
		}
	}

	@Override
	public void Egg(Aut_Direction d, Aut_Category c, int id) {
		if (d == null) {
			d = this.direction;
		}

		Location location = this.frontTileLocation(d);
		switch (c) {
		case A:
			if (id == 0) {
				Random randomA = new Random();
				id = randomA.nextInt(2) + 1;
			}
			switch (id) {
			case 1:
				Goblin gob = new Goblin(location);
				EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity = gob;
				break;
//			case 2:
//				Skeleton s = new Skeleton(location);
//				EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity = s;
//				break;
			}
		case D:
			if (this.bombs > 0) {
				new Bomb(location, this);
				this.bombs--;
			}
			break;
		case P:
			if (id == 0) {
				Random randomP = new Random();
				id = randomP.nextInt(3) + 1;
			}
			switch (id) {
			case 1:
				Coin coin = new Coin(location);
				EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity = coin;
				break;
			case 2:
				HealingPotion hp = new HealingPotion(location);
				EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity = hp;
				break;
			case 3:
				StrengthPotion sp = new StrengthPotion(location);
				EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity = sp;
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
			if (d != null) {
				this.direction = d;
			}
			if (this.action != Action.H) {
				this.action = Action.H;
			}
			this.anim.changeAction(action);
			Location t = frontTileLocation(d);
			this.attackEffect(t);

			Entity entity = EntitiesConst.MAP_MATRIX[(int) t.getX()][(int) t.getY()].entity;
			if (entity != null) {
				switch (d) {
				case N:
					if ((entity.hitbox.location.getY() + entity.hitbox.height > t.getY() - 0.5)
							&& !(entity instanceof BombRock)) {
						entity.takeDamage(this);
						if (entity instanceof Mob || entity instanceof Hero)
							new BloodEffect(entity.frontTileLocation(direction), Aut_Direction.N);
					}
					break;
				case S:
					if ((entity.hitbox.location.getY() < t.getY() + 0.5) && !(entity instanceof BombRock)) {
						entity.takeDamage(this);
						if (entity instanceof Mob || entity instanceof Hero)
							new BloodEffect(entity.frontTileLocation(direction), Aut_Direction.S);
					}
					break;
				case E:
					if ((entity.hitbox.location.getX() < t.getX() + 0.5) && !(entity instanceof BombRock)) {
						entity.takeDamage(this);
						if (entity instanceof Mob || entity instanceof Hero)
							new BloodEffect(entity.frontTileLocation(direction), Aut_Direction.E);
					}
					break;
				case W:
					if ((entity.hitbox.location.getX() + entity.hitbox.width > t.getX() - 0.5)
							&& !(entity instanceof BombRock)) {
						entity.takeDamage(this);
						if (entity instanceof Mob || entity instanceof Hero)
							new BloodEffect(entity.frontTileLocation(direction), Aut_Direction.W);
					}
					break;
				default:
					break;
				}
			}
		}
	}

	public void takeDamage(Entity attacker) {
		if (this.dead && attacker instanceof Hero && this instanceof Hero) {
			this.revive();
		}
//		else if (!this.frozen /* && attacker.category != this.category */) {
		if (EntitiesConst.GAME.debug)
			System.out.println("victim has " + this.health + " hearts");
//			this.frozen = true;
//			this.action = Action.T;
//			this.anim.changeAction(action);
		this.health -= attacker.weaponDamage;
//			if (this.health - attacker.weaponDamage > 0) {
//				this.health -= attacker.weaponDamage;
//			} else {
//				this.health = 0;
//				if (attacker.category == Aut_Category.AT) {
//					Hero.addExperience(attacker);
//				}
//			}
//		}
	}

	public void die() {
		if (this.action != Action.D) {
			this.action = Action.D;

			this.frozen = true;

			if (EntitiesConst.GAME.debug) {
				System.out.println(this.name + " has died");
			}
		}
	}

	public void revive() {
		this.dead = false;
		this.health = this.maxHealth;
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
			EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity = null;
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
		this.health = maxHealth;
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
		this.timer = time;
		this.frozen = true;
	}

	public void waited() {

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
		case H:
			xIndex = this.location.getX();
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

	public int getNbActionSprite(Action a) {
		return 1;
	}

	public int totSrpitePerDir() {
		return 1;
	}

	public boolean isFinished() {
		switch (this.action) {
		case S:
			return this.actionIndex >= EntitiesConst.STAND_INDEX_MAX;
		case M:
			return this.actionIndex >= EntitiesConst.MOUVEMENT_INDEX_MAX;
		case H:
			return this.actionIndex >= EntitiesConst.HIT_INDEX_MAX;
		case D:
			return this.actionIndex >= EntitiesConst.DIE_INDEX_MAX;
		case T:
			return this.actionIndex >= EntitiesConst.TOUCHED_INDEX_MAX;
		case I:
			return this.actionIndex >= EntitiesConst.INTERACT_INDEX_MAX;
		default:
			return true;
		}
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

	public void attackEffect(Location t) {
	}
}
