package info3.game.constants;

import info3.game.Game;
import info3.game.map.Map;
import info3.game.map.Tile;

public class EntitiesConst {
	
	// GLOBAL ENTITY CONSTANTS
	public static Game GAME;
	public static Map MAP;
	public static Tile[][] MAP_MATRIX;	
	
	public static final int LEVEL = 1, EXPERIENCE = 0, LEVEL_UP = 10, COINS = 0;
	public static final int MOUVEMENT_INDEX_MAX = 200;
	public static final int MOUVEMENT_INDEX_MAX_PROJ = 100;
	public static final int HIT_INDEX_MAX = 300;
	public static final int STAND_INDEX_MAX = 50;
	public static final int DEATH_EXPERIENCE_GIVEN = 5;
    
	public static final int MAX_DIFFX=7;
	public static final int MAX_DIFFY=3;
	
	// HERO CONSTANTS
	public static final int HERO_SPEED = 2;
	public static final int HEALING_POTIONS = 0;
	public static final int STRENGTH_POTIONS = 0;
	
	// MELEE CONSTANTS
	public static final int MELEE_DAMAGE = 3;
	public static final int MELEE_RANGE = 1;
	
	// RANGE CONSTANTS
	public static final int RANGE_DAMAGE = 5;
	public static final int RANGE_RANGE = 3;
	
	// BOMB CONSTANTS
	public static int BOMB_TIMER = 3000;
	public static float BOMB_RADIUS =2f;
	
	// SKELETON CONSTANTS
	public static final int SKELETON_HEALTH = 20;
	public static final int SKELETON_DAMAGE = 2;
	public static final int SKELETON_RANGE = 3;
	public static final int SKELETON_SPEED = 2;
	public static final float SKELETON_SCALE = 1.3f;
	
	// GOBLIN CONSTANTS
	public static final int GOBLIN_HEALTH = 20;
	public static final int GOBLIN_DAMAGE = 2;
	public static final int GOBLINE_RANGE = 2;
	public static final int GOBLIN_SPEED = 2;
	public static final float GOBLIN_SCALE = 1.3f;
	
	// BOSS CONSTANTS
	public static final int BOSS_HEALTH = 20;
	public static final int BOSS_DAMAGE = 4;
	public static final int BOSS_RANGE = 2;
	public static final int BOSS_SPEED = 2;
	
	// HOUSE CONSTANTS
	public static final float HOUSE_SCALE = 1f;
	public static final float HOUSE_OPACITY = 0.45f;
	
	// TREE CONSTANTS
	public static final float TREE_SCALE = 1f;
	public static final float TREE_OPACITY = 0.75f;
	
	// BUSH CONSTANTS
	public static final float BUSH_SCALE = 1.4f;
	public static final int BUSH_HEALTH = 1;
	
	// ROCK CONSTANTS
	public static final float ROCK_SCALE = 1.3f;
	
	// HEROES CONSTANTS
	public static final float HEROES_SCALE = 1.45f;
	
	// COWBOY CONSTANTS
	public static final float COWBOY_SCALE = 1.3f;
	
	// ENERGYBALL CONSTANTS
	public static final float ENERGYBALL_SCALE = 1.2f;
	
	// CAVE WALL CONSTANTS
	public static float CAVE_WALL_SCALE = 1.0f;
	
	// STATUE CONSTANTS
	public static float STATUE_SCALE = 4.0f;
}
