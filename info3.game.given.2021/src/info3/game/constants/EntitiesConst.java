package info3.game.constants;

import info3.game.Game;
import info3.game.map.Map;
import info3.game.map.Tile;

public class EntitiesConst {
	
	// GLOBAL ENTITY CONSTANTS
	public static int MAX_HEALTH = 10;
	public static Game GAME;
	public static Map MAP;
	public static Tile[][] MAP_MATRIX;	
	
	public static int LEVEL, EXPERIENCE;
	public static int MOUVEMENT_INDEX_MAX=200;
	public static int HIT_INDEX_MAX = 50;
	// HERO CONSTANTS
	public static int HERO_SPEED = 2;
	
	// MELEE CONSTANTS
	public static int MELEE_HEALTH = 20;
	public static int MELEE_DAMAGE = 3;
	public static int MELEE_RANGE = 1;
	
	// RANGE CONSTANTS
	public static int RANGE_HEALTH = 10;
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
}
