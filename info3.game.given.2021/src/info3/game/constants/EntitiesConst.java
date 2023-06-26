package info3.game.constants;

import info3.game.Game;
import info3.game.map.Map;
import info3.game.map.Tile;

public class EntitiesConst {
	
	// GLOBAL ENTITY CONSTANTS
	public static Game GAME;
	public static int SEED;
	public static Map MAP;
	public static Tile[][] MAP_MATRIX;	
	
	public static final int LEVEL = 1, EXPERIENCE = 0, LEVEL_UP = 10, COINS = 0;
	public static final int DEATH_EXPERIENCE_GIVEN = 5;
    
	public static final int MAX_DIFFX=7;
	public static final int MAX_DIFFY=3;
	
	public static final int MOUVEMENT_INDEX_MAX = 200;
	public static final int MOUVEMENT_INDEX_MAX_PROJ = 100;
	public static final int MOUVEMENT_INDEX_MAX_VILLAGER = 1200;
	public static final int MOUVEMENT_INDEX_MAX_HERMIT = 1800;
	public static final int MOUVEMENT_INDEX_MAX_MOB = 1000;
	public static final int MOUVEMENT_INDEX_MAX_MOB_PROJ = 200;
	public static final int HIT_INDEX_MAX = 300;
	public static final int STAND_INDEX_MAX = 300;
	public static final int HIT_INDEX_MAX_MOB = 300;
	public static final int TOUCHED_INDEX_MAX = 200;
	public static final int DIE_INDEX_MAX = 500;
	public static final int ROTATING_INDEX_MAX = 50;
	public static final int INTERACT_INDEX_MAX = 300;
	
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
	public static final int BOMB_TIMER = 3000;
	public static final float BOMB_RADIUS =2f;
	public static final float BOMB_SCALE =0.6f;
	
	// SKELETON CONSTANTS
	public static final int SKELETON_HEALTH = 8;
	public static final int SKELETON_DAMAGE = 1;
	public static final int SKELETON_RANGE = 3;
	public static final int SKELETON_SPEED = 2;
	public static final float SKELETON_SCALE = 0.9f;
	public static int SKELETON_DETECTION = 5;
	
	// GOBLIN CONSTANTS
	public static final int GOBLIN_HEALTH = 12;
	public static final int GOBLIN_DAMAGE = 1;
	public static final int GOBLINE_RANGE = 2;
	public static final int GOBLIN_SPEED = 6;
	public static final float GOBLIN_SCALE = 1.2f;
	public static int GOBLIN_DETECTION = 5;
	
	// BOSS CONSTANTS
	public static final int BOSS_HEALTH = 500;
	public static final int BOSS_BASE_DAMAGE = 4;
	public static final int BOSS_FLAME_ATTACK_DAMAGE = 100;
	public static final int BOSS_RANGE = 18;
	public static final int BOSS_SPEED = 2;
	public static final int BOSS_MOB_SPAWN_RANGE = 1;
	public static final int BOSS_MOB_SPAWN_NUMBER = 3;
	public static final int BOSS_FLAME_ATTACK_SIZE = 3;
	public static final int BOSS_NUMBER_PROJECTILES_TO_BE_FIRED = 10;
	public static final float BOSS_SCALE = 3.0f;
	
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
	public static float ENERGYBALL_SCALE = 1.2f;
	
	// BONE CONSTANTS
	public static final float BONE_SCALE = 0.8f;
	
	// MAZE WALL CONSTANTS
	public static final float MAZE_WALL_SCALE = 1.0f;
	
	// SPEECH CONSTANTS
	public static final float SPEECHBUBBLE_SCALE = 1.0f;
	public static final float VILLAGERGIRL_SCALE = 1.2f;
	public static final float MINER_SCALE = 1.3f;
	public static final float HERMIT_SCALE = 1.0f;
	
	// STATUE CONSTANTS
	public static final float STATUE_SCALE =4f;
	public static final float STATUE_OPACITY =0.75f;
	
	// DUNGEON ENTRANCE CONSTANTS
	public static final float DUNGEON_ENTRANCE_SCALE = 1.0f;
	public static final int DUNGEON_ENTRANCE_X_POS = 70;
	public static final int DUNGEON_ENTRANCE_Y_POS = 30;
	
	// END GAME CONSTANTS
	public static final int SPRING_X_POS = 30;
	public static final int SPRING_Y_POS = 43;
	
	// MAZE ENTRANCE CONSTANTS
	public static final float MAZE_ENTRANCE_SCALE = 1.0f;
	public static final int MAZE_ENTRANCE_X_POS = 30;
	public static final int MAZE_ENTRANCE_Y_POS = 70;
	public static final int MAZE_COUNTER_LIMIT = 120000;
	
	// DUNGEON CONSTANTS
	public static final int NUMBER_OF_TORCHES = 6;
	
	public static int getActionIndexMax(Action a) {
		if(a == null)
			a = Action.S;
		switch (a) {
		case S:
			return STAND_INDEX_MAX;
		case M:
			return MOUVEMENT_INDEX_MAX;
		case H:
			return HIT_INDEX_MAX;
		case T:
			return TOUCHED_INDEX_MAX;
		case D:
			return DIE_INDEX_MAX;
		default:
			return 1;
		}
	}
}
