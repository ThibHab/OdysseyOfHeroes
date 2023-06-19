package info3.game.map;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import info3.game.constants.EntitiesConst;
import info3.game.entity.*;

public abstract class Map implements IMap {
	public Tile[][] map;
	public int lenX, lenY;
	Entity player1, player2;
	public LinkedList<Projectile> projectiles;

	public Map(int nb_x, int nb_y, Entity p1, Entity p2) {
		this.lenX = nb_x;
		this.lenY = nb_y;
		this.player1 = p1;
		this.player2 = p2;
		this.map = new Tile[lenX][lenY];
		this.projectiles = new LinkedList<>();
	}

	/**
	 * Generate randomly with a chosen space between the chosen entity.
	 * 
	 * @param rareness     the bigger the less entity it creates
	 * @param seed         to set the random
	 * @param spaceBetween the distance between two entity
	 */
	public void setEntityRandomly(int x, int y, int areaSize, int spaceBetween, String ent, long seed, int rareness) {
		if (!(ent.equals("Bush")) && !(ent.equals("Rock")) && !(ent.equals("Tree"))) {
			return;
		}
		Random r = new Random(seed);
		for (int i = x; i < x + areaSize; i++) {
			for (int j = y; j < y + areaSize; j++) {
				if (map[i][j].entity == null && !(map[i][j] instanceof WaterTile) && !(map[i][j] instanceof DirtTile)) {
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
								map[i][j].entity = new Bush(new Location(i, j));
							} else if (ent.equals("Rock")) {
								map[i][j].entity = new Rock(new Location(i, j));
							} else {
								if (j - 1 < 0) {
									if (map[i][lenY - 1].entity == null && map[i + 1][lenY - 1].entity == null
											&& map[i + 1][j].entity == null) {
										Tree tree = new Tree(new Location(i, j - 1));
										map[i][lenY - 1].entity = tree;
										map[i][j].entity = tree;
										map[i + 1][lenY - 1].entity = tree;
										map[i + 1][j].entity = tree;
									}
								} else {
									if (map[i][j - 1].entity == null && map[i + 1][j - 1].entity == null
											&& map[i + 1][j].entity == null) {
										Tree tree = new Tree(new Location(i, j - 1));
										map[i][j - 1].entity = tree;
										map[i][j].entity = tree;
										map[i + 1][j - 1].entity = tree;
										map[i + 1][j].entity = tree;
									}
								}

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
				Location l = new Location(j, j);
				if (tile.equals("Water")) {
					map[i][j] = new WaterTile(l);
				} else if (tile.equals("Rock")) {
					map[i][j] = new RockTile(l);
				} else if (tile.equals("Grass")) {
					map[i][j] = new GrassTile(l);
				} else {
					map[i][j] = new DirtTile(l);
				}
			}
		}
	}

	boolean checkinradius(float x, float y, int c_x, int c_y, float radius) {
		return (x - c_x) * (x - c_x) + (y - c_y) * (y - c_y) < radius * radius;
	}

	public void setCircleWaterBackground(int x, int y, int radius) {
		for (int i = x - (2 * radius); i < x + (2 * radius); i++) {
			for (int j = y - (2 * radius); j < y + (2 * radius); j++) {
				if (checkinradius(i, j, x, y, radius + 0.3f)) {
					Location l = new Location(j, j);
					map[i][j] = new WaterTile(l);
				}
			}
		}
	}

	public void setVillage(int x, int y, int areaSize) {
		setSurfaceBackground(x, y, areaSize, 1, "Dirt");
		setSurfaceBackground(x + 2, y + 4, areaSize - 3, 1, "Dirt");
		setSurfaceBackground(x + (areaSize / 2), y, 1, areaSize, "Dirt");
		for (int indexHouse = x + 2; indexHouse < areaSize; indexHouse += 4) {
			setSurfaceBackground(indexHouse, y - 1, 1, 1, "Dirt");
		}
		int i = x + 1;
		while (i < areaSize) {
			Location l = new Location(i, y - 4);
			Location l2 = new Location(i, y + 1);
			House house = new House(l);
			House house2 = new House(l2);
			for (int j = 0; j < house.width; j++) {
				for (int k = 2; k < house.height + 2; k++) {
					map[i + j][y - k].entity = house;
				}
			}
			
			if (!(map[i + 1][y + 2] instanceof DirtTile)) {
				for (int m = 0; m < house.width; m++) {
					for (int n = 1; n < house.height + 1; n++) {
						map[i + m][y + n].entity = house2;
					}
				}
			}
			
			i += house.width + 1;
		}
		
	}
}
