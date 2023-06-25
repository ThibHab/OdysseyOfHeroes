package info3.game.map;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import info3.game.automata.Aut_Direction;
import info3.game.constants.EntitiesConst;
import info3.game.entity.Boss;
import info3.game.entity.Entity;
import info3.game.entity.Location;
import info3.game.entity.Melee;
import info3.game.entity.Torch;

public class DungeonMap extends Map {
	public static List<Torch> torches;
	public boolean lit;
	public int sizeX = 20;
	public int sizeY = 12;
	boolean initLit = false;
	public static boolean finish;
	int transiMax = 5000;
	int transi;
	float transiPercent;

	public DungeonMap(int nb_x, int nb_y, Entity p1, Entity p2) {
		super(nb_x, nb_y, p1, p2);
		this.transi = transiMax;

		this.setSurfaceBackground(0, 0, nb_x, nb_y, "BlackTile");
		this.setSurfaceBackground(1, 1, sizeX, sizeY, "RockDungeon");
		this.setDongeonWalls(1, 1, sizeX, sizeY);

		this.setPlayer(4, 5, p1);
		this.setPlayer(4, 6, p2);

		this.torches = new LinkedList<Torch>();
		int x, y;
		Location loc;
		Random r = new Random();
		for (int i = 0; i < EntitiesConst.NUMBER_OF_TORCHES; i++) {
			do {
				x = r.nextInt(sizeX - 2) + 1;
				y = r.nextInt(sizeY - 2) + 1;
			} while (map[x][y].entity != null);
			if (i < 3)
				loc = new Location(x, y);
			else
				loc = new Location(x, sizeY - 1 - y);

			Torch t = new Torch(loc);
			torches.add(t);
			this.map[x][(int) loc.getY()].entity = t;
		}

		EntitiesConst.MAP = this;
		EntitiesConst.MAP_MATRIX = this.map;
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
					this.initLit = false;
					return false;
				}
			}
			this.initLit = true;
			for (Torch torch : torches) {
				EntitiesConst.MAP_MATRIX[(int) torch.location.getX()][(int) torch.location.getY()].entity = null;
			}
			EntitiesConst.MAP_MATRIX[20][6].entity = new Boss(new Location(20, 6));
			System.out.println("Boss spawned");
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
				}
			}
		}
	}
}
