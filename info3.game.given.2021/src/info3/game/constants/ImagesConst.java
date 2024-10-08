package info3.game.constants;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImagesConst {
	static String path;

	public static BufferedImage GRASS_TILE, WATER_TILE, DIRT_TILE, ROCK_TILE, ROCK_DUNGEON;
	public static BufferedImage GRASS_TILE2, WATER_TILE2, DIRT_TILE2, ROCK_TILE2;
	public static BufferedImage RED_HEART, BLUE_HEART, BLACK_HEART;
	public static BufferedImage MENU_PICTURE, CREDIT_PICTURE, CONTROL_PICTURE, MINIMAP_PICTURE, LOGO;

	public static BufferedImage[] HOUSE, ROCK, TREE, STATUE, CURSOR, BUSH, CHEST, TORCH;
	public static BufferedImage[] COIN, HEALING_POTION, STRENGTH_POTION;
	public static BufferedImage[] BOSS, GOBLIN, SKELETON, MERCHANT, VILLAGERGIRL, HERMIT, MINER;
	public static BufferedImage[] MAZE_WALL, MAZE_ENTRANCE, DUNGEON_WALL, DUNGEON_ENTRANCE_OPEN,
			DUNGEON_ENTRANCE_CLOSED, PORTAL;
	public static BufferedImage[] MELEE, RANGE, BOMB, SWORD, FIRE_POWER;
	public static BufferedImage[] ENERGYBALL, FIREBALL, BONE;
	public static BufferedImage[] SPEECHBUBBLE;
	public static BufferedImage[] SAVE_TILES;
	public static BufferedImage[] SWORD_EFFECT, SPEAR_EFFECT, HEAL_EFFECT, EXPLOSION_EFFECT, SMOKE_EFFECT, BLOOD_EFFECT,
			GROUND_EFFECT;

	public ImagesConst() throws IOException {
		path = "resources/sprites/";

		GRASS_TILE = loadTile("Grass");
		WATER_TILE = loadTile("Water");
		DIRT_TILE = loadTile("Dirt");
		ROCK_TILE = loadTile("Stone");
		ROCK_DUNGEON = loadTile("rock_tile_boss");
		SAVE_TILES = loadSprite("SaveTiles", 2, 1);

		HOUSE = loadSprite("House", 1, 1);
		ROCK = loadSprite("Rock", 1, 1);
		TREE = loadSprite("Tree", 2, 1);
		STATUE = loadSprite("Statue", 1, 1);
		BUSH = loadSprite("bush", 1, 1);
		CHEST = loadSprite("Chest", 2, 2);
		TORCH = loadSprite("TorchNoLight", 2, 3);

		COIN = loadSprite("Coin", 3, 2);
		HEALING_POTION = loadSprite("Healing", 3, 3);
		STRENGTH_POTION = loadSprite("Strength", 3, 3);
		BOMB = loadSprite("bomb", 1, 1);
		SWORD = loadSprite("weapon", 1, 1);
		FIRE_POWER = loadSprite("fire_power", 1, 1);

		BOSS = loadSprite("Boss", 4, 3);
		GOBLIN = loadSprite("Orc", 8, 8);
		SKELETON = loadSprite("Skeleton", 6, 6);
		HERMIT = loadSprite("Hermit", 4, 3);
		VILLAGERGIRL = loadSprite("VillagerGirl", 4, 4);
		MINER = loadSprite("Miner", 4, 4);

		MAZE_WALL = loadSprite("MazeWall", 1, 1);
		MAZE_ENTRANCE = loadSprite("MazeEntrance", 1, 1);
		DUNGEON_WALL = loadSprite("DungeonWall", 3, 2);
		DUNGEON_ENTRANCE_OPEN = loadSprite("portalRing", 1, 5);
		DUNGEON_ENTRANCE_CLOSED = loadSprite("PortalRingClosed", 3, 2);
		PORTAL = loadSprite("portal", 2, 2);

		MELEE = loadSprite("Melee", 8, 7);
		RANGE = loadSprite("Range", 8, 7);

		ENERGYBALL = loadSprite("EnergyBall", 1, 4);
		FIREBALL = loadSprite("FireEnergyBall", 2, 2);
		BONE = loadSprite("Bone", 1, 3);

		RED_HEART = loadTile("red_heart");
		BLUE_HEART = loadTile("blue_heart");
		BLACK_HEART = loadTile("empty_heart");

		MENU_PICTURE = loadTile("menu_picture");
		CREDIT_PICTURE = loadTile("Credits_projet");
		CONTROL_PICTURE = loadTile("controls");
		MINIMAP_PICTURE = loadTile("Minimap");
		LOGO = loadTile("Logo");
		CURSOR = loadSprite("agrou_vert", 1, 1);

		SPEECHBUBBLE = loadSprite("SpeechBubble", 1, 1);

		SWORD_EFFECT = loadSprite("SwordAttackV2", 5, 1);
		SPEAR_EFFECT = loadSprite("SpearAttack", 3, 2);
		HEAL_EFFECT = loadSprite("Heal", 4, 4);
		EXPLOSION_EFFECT = loadSprite("Explosion", 3, 2);
		SMOKE_EFFECT = loadSprite("Smoke", 3, 2);
		BLOOD_EFFECT = loadSprite("BloodV2", 5, 2);
		GROUND_EFFECT = loadSprite("GroundV3", 4, 1);
	}

	public static BufferedImage[] loadSprite(String filename, int nrows, int ncols) {
		try {
			File imageFile = new File(path + filename + ".png");
			if (!imageFile.exists()) {
				imageFile = new File(path + "default.png");
			}
			BufferedImage image = ImageIO.read(imageFile);
			int width = image.getWidth(null) / ncols;
			int height = image.getHeight(null) / nrows;

			BufferedImage[] images = new BufferedImage[nrows * ncols];
			for (int i = 0; i < nrows; i++) {
				for (int j = 0; j < ncols; j++) {
					int x = j * width;
					int y = i * height;
					images[(i * ncols) + j] = image.getSubimage(x, y, width, height);
				}
			}
			return images;
		} catch (Exception e) {
			System.out.println("failed to load sprite sheet");
			return null;
		}
	}

	public static BufferedImage loadTile(String filename) {
		try {
			File imageFile = new File(path + filename + ".png");
			if (!imageFile.exists()) {
				imageFile = new File(path + "default.png");
			}
			return ImageIO.read(imageFile);
		} catch (Exception e) {
			System.out.println("failed to load sprite");
			return null;
		}
	}

	public static void loadFire() {
		try {
			ImagesConst.ENERGYBALL = loadSprite("FireEnergyBall", 2, 2);
			ImagesConst.DUNGEON_ENTRANCE_CLOSED = ImagesConst.DUNGEON_ENTRANCE_OPEN;
		} catch (Exception e) {
			System.out.println("failed to load fire sprites");
		}
	}
}
