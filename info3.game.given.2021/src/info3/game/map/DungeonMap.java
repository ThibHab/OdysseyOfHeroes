package info3.game.map;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import info3.game.automata.Aut_Direction;
import info3.game.constants.EntitiesConst;
import info3.game.entity.Entity;
import info3.game.entity.Location;
import info3.game.entity.Melee;
import info3.game.entity.Torch;

public class DungeonMap extends Map {
	
	List<Torch> torches;
	public boolean lit;

	public DungeonMap(int nb_x, int nb_y, Entity p1, Entity p2) {
		super(nb_x, nb_y, p1, p2);
		
		this.setSurfaceBackground(0, 0, nb_x, nb_y, "BlackTile");
		this.setSurfaceBackground(1, 1, nb_x - 2, nb_y - 2, "RockDungeon");
		this.setDongeonWalls(1, 1, lenX - 2, lenY - 2);
		
		this.setPlayer(4, 5, p1);
		this.setPlayer(4, 6, p2);
		
		this.torches = new LinkedList<Torch>();
		int x, y;
		Location loc;
		Random r = new Random();
		for (int i = 0; i < 6; i++) {
			do {
				x = r.nextInt(nb_x - 2) + 1;
				y = r.nextInt(3) + 1;
			} while (map[x][y].entity != null);
			if (i < 3)
				loc = new Location(x, y);
			else
				loc = new Location(x, nb_y -1 - y);
			
			Torch t = new Torch(loc);
			torches.add(t);
			this.map[x][(int) loc.getY()].entity = t;
		}
	}
	
	public void setDongeonWalls(int x, int y, int nbTileX, int nbTileY) {
		for (int i = x; i < x+nbTileX; i++) {
			map[i][y-1] = new DungeonWalls(new Location(i, y-1), 1);
		}
		for (int i = x; i < x+nbTileX; i++) {
			map[i][y+nbTileY] = new DungeonWalls(new Location(i, y+nbTileY), 3);
		}
		for (int j = y-1; j < y + nbTileY; j++) {
			map[x-1][j] = new DungeonWalls(new Location(x-1, j), 0);
		}
		for (int j = y-1; j < y + nbTileY; j++) {
			map[x+nbTileX][j] = new DungeonWalls(new Location(x+nbTileX, j), 2);
		}
	}
	

}
