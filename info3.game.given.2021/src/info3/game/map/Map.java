package info3.game.map;

import java.util.LinkedList;
import java.util.Random;

import animations.Effect;
import info3.game.constants.Action;
import info3.game.constants.EntitiesConst;
import info3.game.entity.Bomb;
import info3.game.entity.Boss;
import info3.game.entity.Bush;
import info3.game.entity.Chest;
import info3.game.entity.DungeonEntrance;
import info3.game.entity.Entity;
import info3.game.entity.Hero;
import info3.game.entity.House;
import info3.game.entity.Location;
import info3.game.entity.MazeEntrance;
import info3.game.entity.Portal;
import info3.game.entity.Projectile;
import info3.game.entity.Rock;
import info3.game.entity.SpeechBubble;
import info3.game.entity.Statue;
import info3.game.entity.TransparencyBlock;
import info3.game.entity.Tree;

public abstract class Map implements IMap {
	public Tile[][] map;
	public int lenX, lenY;
	Entity player1, player2;
	public LinkedList<Projectile> projectiles;
	public LinkedList<SpeechBubble> bubbles;
	public LinkedList<Effect> effects;
	public LinkedList<Bush> deadBush;
	public Location rockLoc;

	public Map(int nb_x, int nb_y, Entity p1, Entity p2) {
		this.lenX = nb_x;
		this.lenY = nb_y;
		this.player1 = p1;
		this.player2 = p2;
		this.map = new Tile[lenX][lenY];
		this.projectiles = new LinkedList<>();
		this.bubbles = new LinkedList<>();
		this.effects = new LinkedList<>();
		this.deadBush = new LinkedList<>();
	}

	void createTree(int x, int y) {
		Tree tr = new Tree(new Location(x, y));
		map[x][y].entity = tr;
		int count = 0;
		for (int tr_j = 0; tr_j < 3; tr_j++) {
			for (int tr_i = 0; tr_i < 3; tr_i++) {
				int tr_x = (x - 1 + tr_i + lenX) % lenX;
				int tr_y = (y - 2 + tr_j + lenY) % lenY;
				if (x != tr_x || y != tr_y) {
					if (map[tr_x][tr_y].tpBlock != null) {
						map[tr_x][tr_y].tpBlock.add(tr);
						tr.liste[count] = map[tr_x][tr_y];
						count++;
					} else {
						TransparencyBlock tb = new TransparencyBlock(tr_x, tr_y);
						tb.add(tr);
						map[tr_x][tr_y].tpBlock = tb;
						tr.liste[count] = map[tr_x][tr_y];
						count++;
					}
				}
			}
		}
	}

	public void delTree(int x, int y) {
		if (map[x][y].entity instanceof Tree) {
			Tree tr = (Tree) map[x][y].entity;
			for (int tr_j = 0; tr_j < 3; tr_j++) {
				for (int tr_i = 0; tr_i < 3; tr_i++) {
					int tr_x = (x - 1 + tr_i + lenX) % lenX;
					int tr_y = (y - 2 + tr_j + lenY) % lenY;
					if (x != tr_x || y != tr_y) {
						if (map[tr_x][tr_y].tpBlock != null) {
							map[tr_x][tr_y].tpBlock.del(tr);
							map[tr_x][tr_y].tpBlock = null;
						}
					}
				}
			}
			map[x][y].entity = null;
		}
	}

	void createHouse(int x, int y) {
		House h = new House(new Location(x, y));
		h.hitbox.update();
		int count = 0;
		for (int hj = 0; hj < 3; hj++) {
			for (int hi = 0; hi < 3; hi++) {
				int hx = (x - 1 + hi + lenX) % lenX;
				int hy = (y - 2 + hj + lenY) % lenY;
				if (hj == 0) {
					if (map[hx][hy].tpBlock != null) {
						map[hx][hy].tpBlock.add(h);
						h.liste[count] = map[hx][hy];
						count++;
					} else {
						TransparencyBlock tb = new TransparencyBlock(hx, hy);
						tb.add(h);
						map[hx][hy].tpBlock = tb;
						h.liste[count] = map[hx][hy];
						count++;
					}
				} else {
					map[hx][hy].entity = h;
				}
			}
		}
	}

	boolean transparentBlockStatue(int x, int y) {
		return ((x <= 1 && y <= 1) || (x > 2 && y <= 1) || (x > 0 && x < 4 && y > 0 && y < 4) || (x == 2 && y == 4))
				&& !(x == 2 && y == 2);
	}

	void createStatue(int x, int y) {
		Statue st = new Statue(new Location(x, y));
		map[x][y].entity = st;
		int count = 0;
		for (int stj = 0; stj < 5; stj++) {
			for (int sti = 0; sti < 5; sti++) {
				int stx = (x - 2 + sti + lenX) % lenX;
				int sty = (y - 2 + stj + lenY) % lenY;
				if (transparentBlockStatue(sti, stj)) {
					if (map[stx][sty].tpBlock != null) {
						map[stx][sty].tpBlock.add(st);
						st.liste[count] = map[stx][sty];
						count++;
					} else {
						TransparencyBlock tb = new TransparencyBlock(stx, sty);
						tb.add(st);
						map[stx][sty].tpBlock = tb;
						st.liste[count] = map[stx][sty];
						count++;
					}
				}
			}
		}

	}

	void createRock(int x, int y) {
		Rock r = new Rock(new Location(x, y));
		map[x][y].entity = r;
	}

	void createBush(int x, int y) {
		Bush b = new Bush(new Location(x, y));
		map[x][y].entity = b;
	}

	void createPortal(int x, int y) {
		Portal b = new Portal(new Location(x, y));
		map[x][y].entity = b;
	}

	void createChest(int x, int y) {
		Chest c = new Chest(new Location(x, y));
		map[x][y].entity = c;
	}

	public void createBomb(int x, int y, Bomb b) {
		if (map[x][y].entity == null) {
			this.map[x][y].entity = b;
			Hero.bombs--;
		}
	}

	public float diffX(float a, float b) {
		float tmp = Math.abs(a - b);
		float tmp2 = Math.min(a, b) + lenX - Math.max(a, b);
		if (tmp < tmp2) {
			return tmp;
		}
		return tmp2;
	}

	public float diffY(float a, float b) {
		float tmp = Math.abs(a - b);
		float tmp2 = Math.min(a, b) + lenY - Math.max(a, b);
		if (tmp < tmp2) {
			return tmp;
		}
		return tmp2;
	}

	Location mid(Location loc1, Location loc2) {
		Location a = new Location(loc1.getX() + 0.5f, loc1.getY() + 0.5f);
		Location b = new Location(loc2.getX() + 0.5f, loc2.getY() + 0.5f);
		Location res = new Location(0, 0);
		float tmp = (a.getX() + b.getX()) / 2;
		float tmp2 = ((Math.min(a.getX(), b.getX()) + lenX + Math.max(a.getX(), b.getX())) / 2);
		if (diffX(tmp, a.getX()) < diffX(tmp2, a.getX())) {
			res.setX((tmp + lenX) % lenX);
		} else {
			res.setX((tmp2 + lenX) % lenX);
		}
		tmp = (a.getY() + b.getY()) / 2;
		tmp2 = ((Math.min(a.getY(), b.getY()) + lenY + Math.max(a.getY(), b.getY())) / 2);
		if (diffY(tmp, a.getY()) < diffY(tmp2, a.getY())) {
			res.setY((tmp + lenY) % lenY);
		} else {
			res.setY((tmp2 + lenY) % lenY);
		}
		return res;
	}

	public Location add(Location l, Location add) {
		Location res = new Location(0, 0);
		res.setX((l.getX() + add.getX() + lenX) % lenX);
		res.setY((l.getY() + add.getY() + lenY) % lenY);
		return res;
	}

	public float dist(Location a, Location b) {
		float dx = diffX(a.getX(), b.getX());
		float dy = diffY(a.getY(), b.getY());
		return (float) Math.sqrt((double) dx * dx + dy * dy);
	}

	public void setPlayer(int x, int y, Entity player) {
		if (player.action != Action.D) {
			player.action = Action.S;
		}

		player.location.setX(x);
		player.location.setY(y);
		player.hitbox.update();
		map[x][y].entity = player;

		if (player.action != Action.D) {
			player.frozen = false;
		}
	}

	public void setDungeonEntrance(int x, int y) {
		Location location = new Location(x, y);
		if (map[x][y].entity != null) {
			if (map[x][y].entity instanceof Tree) {
				this.delTree(x, y);
			} else {
				map[x][y].entity = null;
			}
		}

		map[x][y].entity = new DungeonEntrance(location);

		if (map[x][y + 1].entity instanceof Tree) {
			this.delTree(x, y + 1);
		} else {
			map[x][y + 1].entity = null;
		}

		if (map[x - 1][y + 1].entity instanceof Tree) {
			this.delTree(x - 1, y + 1);
		} else {
			map[x - 1][y + 1].entity = null;
		}

		if (map[x + 1][y + 1].entity instanceof Tree) {
			this.delTree(x + 1, y + 1);
		} else {
			map[x + 1][y + 1].entity = null;
		}
	}

	public void setMazeEntrance(int x, int y) {
		Location location = new Location(x, y);
		if (map[x][y].entity != null) {
			if (map[x][y].entity instanceof Tree) {
				this.delTree(x, y);
			} else {
				map[x][y].entity = null;
			}
		}

		map[x][y].entity = new MazeEntrance(location);

		if (map[x][y + 1].entity instanceof Tree) {
			this.delTree(x, y + 1);
		} else {
			map[x][y + 1].entity = null;
		}

		if (map[x - 1][y + 1].entity instanceof Tree) {
			this.delTree(x - 1, y + 1);
		} else {
			map[x - 1][y + 1].entity = null;
		}

		if (map[x + 1][y + 1].entity instanceof Tree) {
			this.delTree(x + 1, y + 1);
		} else {
			map[x + 1][y + 1].entity = null;
		}
	}

	/**
	 * Generate randomly with a chosen space between the chosen entity.
	 *
	 * @param rareness     the bigger the less entity it creates
	 * @param seed         to set the random
	 * @param spaceBetween the distance between two entity
	 */
	@Override
	public void setEntityRandomly(int x, int y, int areaSize, int spaceBetween, String ent, long seed, int rareness) {
		if (!(ent.equals("Bush")) && !(ent.equals("Rock")) && !(ent.equals("Tree")) && !(ent.equals("Portal"))) {
			return;
		}
		Random r = new Random(EntitiesConst.SEED);
		for (int i = x; i < x + areaSize; i++) {
			for (int j = y; j < y + areaSize; j++) {
				if (map[i][j].entity == null && !(map[i][j] instanceof WaterTile) && !(map[i][j] instanceof DirtTile)
						&& !(map[i][j] instanceof SaveTile) && !(map[i][j] instanceof RockTile)) {
					int n = r.nextInt(rareness);
					if (n == 1) {
						boolean already = false;
						int rec_x = i - spaceBetween;
						int rec_y = j - spaceBetween;
						while (rec_x < 0) {
							while (rec_y < 0) {
								if (map[lenX + rec_x][lenY + rec_y].entity != null) {
									already = true;
								}
								rec_y++;
							}
							while (rec_y <= j + spaceBetween) {
								if (map[lenX + rec_x][rec_y].entity != null) {
									already = true;
								}
								rec_y++;
							}
							rec_x++;
						}
						while (rec_x <= i + spaceBetween) {
							rec_y = j - spaceBetween;
							while (rec_y < 0) {
								if (map[rec_x][lenY + rec_y].entity != null) {
									already = true;
								}
								rec_y++;
							}
							while (rec_y <= j + spaceBetween) {
								if (map[rec_x][rec_y].entity != null) {
									already = true;
								}
								rec_y++;
							}
							rec_x++;
						}
						if (!already) {
							if (ent.equals("Bush")) {
								this.createBush(i, j);
							} else if (ent.equals("Rock")) {
								this.createRock(i, j);
							} else if (ent.equals("Tree")) {
								createTree(i, j);
							} else if (ent.equals("Portal")) {
								createPortal(i, j);
							}
						}
					}
				}
			}
		}
	}

	public void setSurfaceBackground(int x, int y, int width, int height, String tile) {
		for (int i = x; i < x + width; i++) {
			for (int j = y; j < y + height; j++) {
				Location l = new Location(i, j);
				if (tile.equals("Water")) {
					map[i][j] = new WaterTile(l);
				} else if (tile.equals("Rock")) {
					map[i][j] = new RockTile(l);
				} else if (tile.equals("Grass")) {
					map[i][j] = new GrassTile(l);
				} else if (tile.equals("Dirt")) {
					map[i][j] = new DirtTile(l);
				} else if (tile.equals("RockDungeon")) {
					map[i][j] = new RockDungeonTile(l);
				} else if (tile.equals("BlackTile")) {
					map[i][j] = new DungeonWalls(l, 4);
				}
			}
		}
	}

	boolean checkinradius(float x, float y, int c_x, int c_y, float radius) {
		return (x - c_x) * (x - c_x) + (y - c_y) * (y - c_y) < radius * radius;
	}

	public void setDisqueBackground(int x, int y, int radius, String type) {
		for (int i = x - (2 * radius); i < x + (2 * radius); i++) {
			for (int j = y - (2 * radius); j < y + (2 * radius); j++) {
				if (checkinradius(i, j, x, y, radius + 0.3f)) {
					Location l = new Location(j, j);
					if (type.equals("Water")) {
						this.delTree(i, j);
						map[i][j] = new WaterTile(l);
					} else if (type.equals("Rock")) {
						map[i][j] = new RockTile(l);
					} else if (type.equals("Dirt")) {
						map[i][j] = new DirtTile(l);
					} else {
						map[i][j] = new GrassTile(l);
					}
				}
			}
		}
	}

	public void setCircleBackground(int x, int y, int radius, String type) {
		for (int i = x - (2 * radius); i < x + (2 * radius); i++) {
			for (int j = y - (2 * radius); j < y + (2 * radius); j++) {
				if (checkinradius(i, j, x, y, radius + 0.3f) && !checkinradius(i, j, x, y, (radius - 1) + 0.3f)) {
					Location l = new Location(j, j);
					if (type.equals("Water")) {
						map[i][j] = new WaterTile(l);
					} else if (type.equals("Rock")) {
						map[i][j] = new RockTile(l);
					} else if (type.equals("Dirt")) {
						map[i][j] = new DirtTile(l);
					} else {
						map[i][j] = new GrassTile(l);
					}
				}
			}
		}
	}

	public void setBorderForest(int x, int y, int radius) {
		for (int i = x - (2 * radius); i < x + (2 * radius); i++) {
			for (int j = y - (2 * radius); j < y + (2 * radius); j++) {
				if (checkinradius(i, j, x, y, radius + 0.3f) && !checkinradius(i, j, x, y, (radius - 1) + 0.3f)) {
					Entity ent = map[i][j].entity;
					if ((ent == null || ent.location.getX() != i || ent.location.getY() != j)
							&& !(map[i][j] instanceof WaterTile) && !(map[i][j] instanceof RockTile)
							&& !(map[i][j] instanceof DirtTile)) {
						createTree(i, j);
					}
				}
			}
		}
	}

	public void setSpring(int x, int y, int radius) {
		setDisqueBackground(x, y, radius, "Rock");
		setCircleBackground(x, y, radius - 1, "Water");
		setCircleBackground(x, y, radius + 1, "Dirt");
		createStatue(x, y);
		WorldMap.saveTile1 = new SaveTile(new Location(x - radius - 1, y));
		map[x - radius - 1][y] = WorldMap.saveTile1;
		WorldMap.saveTile2 = new SaveTile(new Location(x + radius + 1, y));
		map[x + radius + 1][y] = WorldMap.saveTile2;
	}

	public void setDistrict(int x, int y) {
		createHouse(x, y);
		setSurfaceBackground(x, y + 1, 1, 1, "Dirt");
		setSurfaceBackground(x, y + 2, 7, 1, "Dirt");
		createHouse(x + 6, y);
		setSurfaceBackground(x + 6, y + 1, 1, 1, "Dirt");
		setSurfaceBackground(x + 3, y + 2, 1, 12, "Dirt");
		createHouse(x, y + 4);
		setSurfaceBackground(x, y + 5, 1, 1, "Dirt");
		setSurfaceBackground(x, y + 6, 7, 1, "Dirt");
		createHouse(x + 6, y + 4);
		setSurfaceBackground(x + 6, y + 5, 1, 1, "Dirt");
		createHouse(x, y + 8);
		setSurfaceBackground(x, y + 9, 1, 1, "Dirt");
		createHouse(x + 6, y + 8);
		setSurfaceBackground(x + 6, y + 9, 1, 1, "Dirt");
		setSurfaceBackground(x, y + 10, 7, 1, "Dirt");
		createHouse(x, y + 12);
		setSurfaceBackground(x, y + 13, 1, 1, "Dirt");
		createHouse(x + 6, y + 12);
		setSurfaceBackground(x + 6, y + 13, 1, 1, "Dirt");
		setSurfaceBackground(x, y + 14, 7, 1, "Dirt");
	}

	public void setVillage(int x, int y) {
		for (int i = 16; i < 45; i++) {
			for (int j = 22; j < 45; j++) {
				Tile tile = this.map[i][j];
				if (tile.entity instanceof Tree) {
					delTree(i, j);
				} else {
					tile.entity = null;
				}
			}
		}
		setDisqueBackground(x, y, 3, "Dirt");
		createTree(x, y);
		createHouse(x, y - 5);
		setSurfaceBackground(x, y - 4, 1, 1, "Dirt");
		setSurfaceBackground(x - 5, y, 2, 1, "Dirt");
		setDistrict(x - 12, y - 6);

		setSurfaceBackground(x + 4, y, 2, 1, "Dirt");
		setDistrict(x + 6, y - 6);

		setSurfaceBackground(x - 1, y + 4, 3, 3, "Dirt");
		setSpring(x, y + 10, 3);
	}

	public void setLac(int x, int y, int radius) {
		this.setDisqueBackground(x, y, 3, "Water");
		this.setDisqueBackground(x + 3, y, 3, "Water");
		this.setDisqueBackground(x + 5, y + 2, 3, "Water");
	}

	public void setForest(int x, int y, int radius, int seed) {
		createRock(x, y + (radius / 2));
		rockLoc = new Location(x, y + (radius / 2));
		setDisqueBackground(x + (radius / 2 - 3), y - (radius / 2 - 6), 3, "Water");
		setDisqueBackground(x + (radius / 2 + 2), y - (radius / 2 - 10), 4, "Water");
		setSurfaceBackground(x + (radius / 2 - 5), y - (radius / 2 - 8), 5, 4, "Rock");
		setSurfaceBackground(x + (radius / 2 - 5), y - (radius / 2 - 9), 4, 3, "Dirt");
		createHouse(x + (radius / 2 - 3), y - (radius / 2 - 10));
		createChest(x + (radius / 2 - 5), y - (radius / 2 - 9));
		setBorderForest(x, y, radius / 2);
		createChest(x, y);
		setEntityRandomly(x - ((radius / 2) + 10), y - ((radius / 2) + 10), radius + 15, 1, "Tree", seed, 4);
		for (int i = 77; i < 80; i++) {
			for (int j = 65; j < 68; j++) {
				Tile tile = this.map[i][j];
				if (tile.entity instanceof Tree) {
					delTree(i, j);
				} else {
					tile.entity = null;
				}
			}
		}
	}

	public void tickEntities(int x, int y, long elapsed) {
		MapRender rend = EntitiesConst.GAME.render;
		int nbTileY = rend.nbTileY + 4;
		int nbTileX = rend.nbTileX + 4;
		int tabSize = 200;
		Entity tab[] = new Entity[tabSize];
		int indexTab = 0;
		boolean alreadyTicked = false;
		if (this instanceof DungeonMap) {
			DungeonMap dmap = (DungeonMap) this;
			dmap.tick(elapsed);
		}
		if (this instanceof MazeMap) {
			MazeMap mmap = (MazeMap) this;
			mmap.tick(elapsed);
		}

		for (int j = 0; j < nbTileY; j++) {
			for (int i = 0; i < nbTileX; i++) {
				int mapX = (int) (i + rend.camera.getX() + lenX - nbTileX / 2) % lenX;
				int mapY = (int) (j + rend.camera.getY() + lenY - nbTileY / 2) % lenY;
				Tile renderTile = map[mapX][mapY];
				Entity ent = renderTile.entity;
				if (ent != null) {
					for (int k = 0; k < indexTab; k++) {
						if (ent == tab[k]) {
							alreadyTicked = true;
						}
					}
					if (!alreadyTicked) {
						if (ent instanceof House || ent instanceof Tree || ent instanceof Hero || ent instanceof Boss) {
							tab[indexTab++] = ent;
						}
						ent.tick(elapsed);
					}
					alreadyTicked = false;
				}
			}
		}
	}

	public void tickEffects(long elapsed) {
		for (Effect eff : this.effects) {
			if (eff != null) {
				eff.step(elapsed);
			}
		}
	}
}
