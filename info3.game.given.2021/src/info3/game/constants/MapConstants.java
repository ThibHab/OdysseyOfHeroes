package info3.game.constants;

import info3.game.map.DungeonMap;
import info3.game.map.IMap;
import info3.game.map.MazeMap;
import info3.game.map.WorldMap;

public class MapConstants {
	// WORLD MAP CONSTANTS
	public static IMap WORLD_MAP = new WorldMap(100, 100, EntitiesConst.GAME.player1, EntitiesConst.GAME.player2);
	
	// DUNGEON MAP CONSTANTS
	public static IMap DUNGEON_MAP = new DungeonMap(32, 32, EntitiesConst.GAME.player1, EntitiesConst.GAME.player2);
	
	// MAZE MAP CONSTANTS
	public static IMap MAZE_MAP = new MazeMap(
			MapConstants.MAZE_MAP_SIZE * (MapConstants.MAZE_MAP_CORRIDOR_SIZE + 1) + 1,
			MapConstants.MAZE_MAP_SIZE * (MapConstants.MAZE_MAP_CORRIDOR_SIZE + 1) + 1,
			EntitiesConst.GAME.player1, EntitiesConst.GAME.player2);
	public static final int MAZE_MAP_CORRIDOR_SIZE = 1;
	public static final int MAZE_MAP_SIZE = 20;
}
