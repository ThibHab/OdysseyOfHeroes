package info3.game.map;

import info3.game.constants.EntitiesConst;
import info3.game.entity.*;

public class WorldMap extends Map {
	
	public static SaveTile saveTile1, saveTile2;

	public WorldMap(int nb_x, int nb_y, Entity p1, Entity p2) {
		super(nb_x, nb_y, p1, p2);
		
		this.setSurfaceBackground(0, 0, lenX, lenY, "Grass");
		this.setLac(9, 12, 3);
		this.setVillage(30, 30, 25);
		this.setForest(70, 70, 30, 2);
		
		this.setEntityRandomly(0, 0, lenX - 2, 2, "Tree", 2, 30);
		this.setEntityRandomly(0, 0, lenX - 2, 2, "Bush", 2, 10);
		this.setEntityRandomly(0, 0, lenX - 2, 2, "Rock", 2, 15);
		
		this.setDungeonEntrance(EntitiesConst.DUNGEON_ENTRANCE_X_POS, EntitiesConst.DUNGEON_ENTRANCE_Y_POS);
		this.setMazeEntrance(EntitiesConst.MAZE_ENTRANCE_X_POS, EntitiesConst.MAZE_ENTRANCE_Y_POS);
		
		this.setEntityRandomly(0, 0, lenX - 2, 2, "Portal", 2, 15);
		
		if (EntitiesConst.MAP == null && !EntitiesConst.GAME.reload) {
			setPlayer(29, 31, p1);
			setPlayer(31, 31, p2);
		}
		
		EntitiesConst.MAP = this;
		EntitiesConst.MAP_MATRIX = this.map;
		
		Villager v = new VillagerGirl(new Location(30,31));
		Villager m = new Miner(new Location(29,30));
		Villager h = new Hermit(new Location(78,66));
		map[(int)v.location.getX()][(int)v.location.getY()].entity = v;
		map[(int)m.location.getX()][(int)m.location.getY()].entity = m;
		map[(int)h.location.getX()][(int)h.location.getY()].entity = h;
		
		
        
		this.delTree((int)WorldMap.saveTile1.location.getX(), (int)WorldMap.saveTile1.location.getY());
		WorldMap.saveTile1.entity = null;
		this.delTree((int)WorldMap.saveTile2.location.getX(), (int)WorldMap.saveTile2.location.getY());
		WorldMap.saveTile2.entity = null;
	}
}
