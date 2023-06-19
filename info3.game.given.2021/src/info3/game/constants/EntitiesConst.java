package info3.game.constants;

import info3.game.Game;
import info3.game.map.Map;
import info3.game.map.Tile;

public class EntitiesConst {
	
	// GLOBAL ENTITY CONSTANTS
	public static Game GAME;
	public static Map MAP;
	public static Tile[][] MAP_MATRIX;	
	
	public static int LEVEL = 1, EXPERIENCE = 0, LEVEL_UP = 10, COINS = 0;
	public static int MOUVEMENT_INDEX_MAX = 200;
	public static int MOUVEMENT_INDEX_MAX_PROJ = 100;
	public static int HIT_INDEX_MAX = 300;
	public static int STAND_INDEX_MAX = 50;
    
	public static int MAX_DIFFX=7;
	public static int MAX_DIFFY=3;
	// HERO CONSTANTS
	public static int HERO_SPEED = 2;
	
	// MELEE CONSTANTS
	public static int MELEE_DAMAGE = 3;
	public static int MELEE_RANGE = 1;
	
	// RANGE CONSTANTS
	public static int RANGE_DAMAGE = 5;
	public static int RANGE_RANGE = 3;
	
	// SKELETON CONSTANTS
	public static int SKELETON_HEALTH = 20;
	public static int SKELETON_DAMAGE = 2;
	public static int SKELETON_RANGE = 3;
	public static int SKELETON_SPEED = 2;
	
	// GOBLIN CONSTANTS
	public static int GOBLIN_HEALTH = 20;
	public static int GOBLIN_DAMAGE = 2;
	public static int GOBLINE_RANGE = 2;
	public static int GOBLIN_SPEED = 2;
	
	// BOSS CONSTANTS
	public static int BOSS_HEALTH = 20;
	public static int BOSS_DAMAGE = 4;
	public static int BOSS_RANGE = 2;
	public static int BOSS_SPEED = 2;
	
	// HOUSE CONSTANTS
	public static float HOUSE_SCALE = 1.1f;
	
	// TREE CONSTANTS
	public static float TREE_SCALE = 1.2f;
	
	// BUSH CONSTANTS
	public static int BUSH_HEALTH = 1;
	public static float BUSH_SCALE = 1.3f;
	
	// ROCK CONSTANTS
	public static float ROCK_SCALE = 1.3f;
	
	// HEROES CONSTANTS
	public static float HEROES_SCALE = 1.3f;
	
	// COWBOY CONSTANTS
	public static float COWBOY_SCALE = 1.3f;
	
	// ENERGYBALL CONSTANTS
	public static float ENERGYBALL_SCALE = 1.2f;
	
	// CAVE WALL CONSTANTS
	public static float CAVE_WALL_SCALE = 1.0f;
}
