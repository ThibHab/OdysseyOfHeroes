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
		
		if (EntitiesConst.MAP == null) {
			setPlayer(29, 31, p1);
			setPlayer(31, 31, p2);
		}
		
		EntitiesConst.MAP = this;
		EntitiesConst.MAP_MATRIX = this.map;
        
		WorldMap.saveTile1.entity = null;
		WorldMap.saveTile2.entity = null;
	}
}
