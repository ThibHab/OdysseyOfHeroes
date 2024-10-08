package info3.game.map;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import info3.game.constants.EntitiesConst;
import info3.game.entity.Boss;
import info3.game.entity.Entity;
import info3.game.entity.Location;
import info3.game.entity.Rock;
import info3.game.entity.Torch;

public class DungeonMap extends Map {
	public static List<Torch> torches;
	public boolean lit;
	public int sizeX = 20;
	public int sizeY = 12;
	public static boolean initLit;
	public static boolean finish;
	int transiMax = 5000;
	int transi;
	float transiPercent;

	public DungeonMap(int nb_x, int nb_y, Entity p1, Entity p2) {
		super(nb_x, nb_y, p1, p2);
		this.transi = transiMax;

		DungeonMap.initLit = false;
		DungeonMap.finish = false;

		this.setSurfaceBackground(0, 0, nb_x, nb_y, "BlackTile");
		this.setSurfaceBackground(1, 1, sizeX, sizeY, "RockDungeon");
		this.setDongeonWalls(1, 1, sizeX, sizeY);

		this.setPlayer(4, 5, p1);
		this.setPlayer(4, 6, p2);

		DungeonMap.torches = new LinkedList<>();
		int x, y;
		Location loc;
		Random r = new Random(EntitiesConst.SEED);
		for (int i = 0; i < EntitiesConst.NUMBER_OF_TORCHES; i++) {
			do {
				x = r.nextInt(sizeX - 2) + 1;
				y = r.nextInt(sizeY - 2) + 1;
			} while (map[x][y].entity != null);

			loc = new Location(x, y);
			Torch t = new Torch(loc);
			torches.add(t);
			this.map[x][y].entity = t;
		}
	}

	public void setDongeonWalls(int x, int y, int nbTileX, int nbTileY) {
		for (int i = x; i < x + nbTileX; i++) {
			map[i][y - 1] = new DungeonWalls(new Location(i, y - 1), 1);
		}
		for (int i = x; i < x + nbTileX; i++) {
			map[i][y + nbTileY] = new DungeonWalls(new Location(i, y + nbTileY), 3);
		}
		for (int j = y - 1; j < y + nbTileY; j++) {
			map[x - 1][j] = new DungeonWalls(new Location(x - 1, j), 0);
		}
		for (int j = y - 1; j < y + nbTileY; j++) {
			map[x + nbTileX][j] = new DungeonWalls(new Location(x + nbTileX, j), 2);
		}
	}

	public boolean torchLit() {
		if (!initLit) {
			for (Torch torch : torches) {
				if (!torch.lit) {
					this.lit = false;
					DungeonMap.initLit = false;
					return false;
				}
			}
			DungeonMap.initLit = true;
			for (Torch torch : torches) {
				EntitiesConst.MAP_MATRIX[(int) torch.location.getX()][(int) torch.location.getY()].entity = null;
			}

			for (int i = 1; i < sizeY + 1; i++) {
				if (map[18][i].entity == player1 || map[19][i].entity == player1 || map[20][i].entity == player1) {
					if (map[17][i].entity == null) {
						setPlayer(17, i, player1);
					} else {
						setPlayer(15, i, player1);
					}
				}
				if (map[18][i].entity == player2 || map[19][i].entity == player2 || map[20][i].entity == player2) {
					if (map[17][i].entity == null) {
						setPlayer(17, i, player2);
					} else {
						setPlayer(15, i, player2);
					}
				}
				if (i != 5 && i != 6 && i != 7) {
					Rock r = new Rock(new Location(18, i));
					map[18][i].entity = r;
					r.health = Integer.MAX_VALUE;
				}
			}
			System.out.println("Boss spawned");
			EntitiesConst.MAP_MATRIX[20][6].entity = new Boss(new Location(20, 6));
		}
		return true;
	}

	public void tick(long elapsed) {
		if (!EntitiesConst.GAME.inMenu.isPaused) {
			transiPercent = (float) transi / transiMax;
			if (!finish && initLit) {
				transi -= elapsed;
				if (transi <= 0) {
					finish = true;
					this.lit = true;
					EntitiesConst.GOBLIN_DETECTION = 10;
					EntitiesConst.SKELETON_DETECTION = 10;
					EntitiesConst.MAP_MATRIX[20][6].entity.frozen = false; // unfreeze boss
				}
			}
		}
	}
}
