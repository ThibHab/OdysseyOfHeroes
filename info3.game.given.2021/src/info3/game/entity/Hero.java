package info3.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.RandomAccessFile;

import info3.game.Game;
import info3.game.automata.Aut_Category;
import info3.game.automata.Aut_Direction;
import info3.game.automata.Aut_Transition;
import info3.game.constants.Action;
import info3.game.constants.EntitiesConst;
import info3.game.map.Tile;
import info3.game.map.WorldMap;
import info3.game.sound.RandomFileInputStream;

public abstract class Hero extends Entity {
	public static int coins, level, levelUp, experience, bushesCut, bombs;
	public static boolean firePowerUnlocked;
	public int healingPotions, strengthPotions;
	public static boolean tryToEnterDungeon;

	public Hero() {
		super();
		this.category = Aut_Category.AT;
		this.scale = EntitiesConst.HEROES_SCALE;
		Hero.coins = EntitiesConst.COINS;
		Hero.level = EntitiesConst.LEVEL;
		Hero.levelUp = EntitiesConst.LEVEL_UP;
		Hero.experience = EntitiesConst.EXPERIENCE;
		Hero.firePowerUnlocked = false;
		Hero.bushesCut = 0;
		Hero.bombs = 0;
		this.hitbox = new Hitbox(this, (float) 0.50, (float) 0.65);
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
			if ((destTile.entity instanceof DungeonEntrance || destTile.entity instanceof MazeEntrance)
					&& this.direction == Aut_Direction.N) {
				if (destTile.entity instanceof DungeonEntrance) {
					if (Hero.firePowerUnlocked) {
						EntitiesConst.GAME.openMap(Game.BOSS);
					} else {
						tryToEnterDungeon = true;
					}
				} else if (destTile.entity instanceof MazeEntrance) {
					EntitiesConst.GAME.openMap(Game.MAZE);
				}
			}

			if (destTile.walkable && destTile.entity == null && EntitiesConst.GAME.render.moveDooable(destLocation, d,
					EntitiesConst.GAME.m_canvas.getHeight(), EntitiesConst.GAME.m_canvas.getWidth())) {
				destTile.entity = this;
			} else {
				this.frozen = false;
			}
			WorldMap.saveTile1.changeTile(false);
			WorldMap.saveTile2.changeTile(false);
		}
	}

	public void saveRestore(Location loc, String state, int health, int maxHealth, int hPotions, int wDamage, int range,
			Aut_Direction dir) {
		this.location = loc;
		this.destLocation = loc;
		EntitiesConst.MAP_MATRIX[(int) loc.getX()][(int) loc.getY()].entity = this;
		EntitiesConst.MAP_MATRIX[0][4].entity = null; // remove players from the tiles where they are created in world
														// map
		EntitiesConst.MAP_MATRIX[1][4].entity = null;
		if (this.automaton.initial.name.equals(state))
			this.currentState = this.automaton.initial;
		for (Aut_Transition next : this.automaton.transitions) {
			if (next.dest.name.equals(state)) {
				this.currentState = next.dest;
				break;
			}
		}

		this.healingPotions = hPotions;
		this.weaponDamage = wDamage;
		this.weaponRange = range;
		this.health = health;
		this.maxHealth = maxHealth;
		this.direction = dir;

	}

	public void paint(Graphics g, int tileSize) {
		BufferedImage img = anim.getFrame();
		Location pixel = EntitiesConst.GAME.render.gridToPixel(location, true);
		int dimension = (int) (scale * tileSize);
		float shiftXY = ((scale - 1) / 2) * tileSize;
		int positionX = (int) (pixel.getX() - shiftXY);
		int positionY = (int) (pixel.getY() - shiftXY);
		g.drawImage(img, positionX, positionY, dimension, dimension, null);
		if (EntitiesConst.GAME.debug) {
			g.setColor(Color.blue);
			Location l = EntitiesConst.GAME.render.gridToPixel(this.hitbox.location, true);
			g.drawRect((int) l.getX(), (int) l.getY(), (int) (tileSize * this.hitbox.width),
					(int) (tileSize * this.hitbox.height));
		}
	}

	@Override
	public void Power() {
		if (this.healingPotions > 0 && this.health < this.maxHealth) {
			this.health = this.maxHealth;
			this.healingPotions--;
			try {
				RandomAccessFile file = new RandomAccessFile("resources/sounds/heal.ogg", "r");
				RandomFileInputStream fis = new RandomFileInputStream(file);
				EntitiesConst.GAME.m_canvas.playSound("heal", fis, 0, 0.8F);
			} catch (Throwable th) {
				th.printStackTrace(System.err);
				System.exit(-1);
			}
			Wait(250);
		}
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
				Hero.coins++;
				try {
					RandomAccessFile file = new RandomAccessFile("resources/sounds/coin.ogg", "r");
					RandomFileInputStream fis = new RandomFileInputStream(file);
					EntitiesConst.GAME.m_canvas.playSound("coin", fis, 0, 0.8F);
				} catch (Throwable th) {
					th.printStackTrace(System.err);
					System.exit(-1);
				}
			} else if (entity instanceof HealingPotion)
				this.healingPotions++;

			else if (entity instanceof StrengthPotion)
				this.strengthPotions++;
			else if (entity instanceof Chest) {
				Hero.coins += 5;
				try {
					RandomAccessFile file = new RandomAccessFile("resources/sounds/chest.ogg", "r");
					RandomFileInputStream fis = new RandomFileInputStream(file);
					EntitiesConst.GAME.m_canvas.playSound("chest", fis, 0, 0.8F);
				} catch (Throwable th) {
					th.printStackTrace(System.err);
					System.exit(-1);
				}
			}

			EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity = null;
		}
	}

	public static void addExperience(Entity attacker) {
		Hero.experience += EntitiesConst.DEATH_EXPERIENCE_GIVEN;
		if (Hero.experience >= Hero.levelUp) {
			Hero.level++;
			try {
				RandomAccessFile file = new RandomAccessFile("resources/sounds/lvlup.ogg", "r");
				RandomFileInputStream fis = new RandomFileInputStream(file);
				EntitiesConst.GAME.m_canvas.playSound("lvlup", fis, 0, 0.8F);
			} catch (Throwable th) {
				th.printStackTrace(System.err);
				System.exit(-1);
			}
			Hero.experience = 0;
			Hero.levelUp = Hero.levelUp * 2;

			EntitiesConst.GAME.player1.updateStats();
			EntitiesConst.GAME.player2.updateStats();
		}

	}

	@Override
	public void takeDamage(Entity attacker) {
		this.health -= attacker.weaponDamage;
	}

	@Override
	public void die() {
		if (this.action != Action.D) {
			this.action = Action.D;
			this.anim.changeAction(action);
			this.frozen = true;

			if (EntitiesConst.GAME.debug) {
				System.out.println(this.name + " has died");
			}
		}
	}

	@Override
	public void Pop(Aut_Direction d, Aut_Category c) {
		this.frozen = true;
		if (this.action != Action.I) {
			if (EntitiesConst.GAME.debug) {
				System.out.println(this.name + " is popping");
			}
			this.action = Action.I;
		}
		if (d == null) {
			d = this.direction;
		}

		Location location = frontTileLocation(d);
		Tile tile = EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()];
		if (tile.entity instanceof Villager) {
			Villager v = (Villager) tile.entity;
			v.talks();
		}
	}

	public static void restore(int lvl, int xp, int coins, int bombs, int bushes) {
		Hero.coins += coins;
		int i = 1;
		while (i < lvl) {
			Hero.levelUp *= 2;
			i++;
		}
		Hero.level = lvl;
		Hero.experience = xp;

		Hero.bombs = bombs;
		Hero.bushesCut = bushes;
	}
}
