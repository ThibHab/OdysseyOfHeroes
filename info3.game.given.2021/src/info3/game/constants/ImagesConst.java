package info3.game.constants;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImagesConst {
	String path;

	public static BufferedImage GRASS_TILE, WATER_TILE, DIRT_TILE, ROCK_TILE, ROCK_DUNGEON;
	public static BufferedImage GRASS_TILE2, WATER_TILE2, DIRT_TILE2, ROCK_TILE2;
	public static BufferedImage RED_HEART, BLUE_HEART, BLACK_HEART;

	public static BufferedImage[] HOUSE, ROCK, TREE, STATUE;
	public static BufferedImage[] COIN, HEALING_POTION, STRENGTH_POTION;
	public static BufferedImage[] BOSS, GOBLIN, SKELETON, MERCHANT, VILLAGER;
	public static BufferedImage[] BUSH, CHEST, TORCH, MAZE_WALL, MAZE_ENTRANCE, DUNGEON_WALL, DUNGEON_ENTRANCE;
	public static BufferedImage[] MELEE, RANGE;
	public static BufferedImage[] ENERGYBALL;
	public static BufferedImage[] BOMB;
	public static BufferedImage[] SAVE_TILES;

	public ImagesConst() throws IOException {
		path = "resources/sprites/";

		GRASS_TILE = loadTile("Grass");
		WATER_TILE = loadTile("water_placeholder");
		DIRT_TILE = loadTile("Dirt");
		ROCK_TILE = loadTile("rock_placeholder");
		GRASS_TILE2 = loadTile("Grass2");
		WATER_TILE2 = loadTile("");
		DIRT_TILE2 = loadTile("");
		ROCK_TILE2 = loadTile("");
		ROCK_DUNGEON = loadTile("rock_tile_boss");
		SAVE_TILES = loadSprite("SaveTiles", 2, 1);

		HOUSE = loadSprite("House", 1, 1);
		ROCK = loadSprite("Rock", 2, 2);
		TREE = loadSprite("Tree", 2, 1);
		STATUE = loadSprite("Statue", 1, 1);
		
		COIN = loadSprite("Coin", 3, 2);
		HEALING_POTION = loadSprite("Healing", 3, 3);
		STRENGTH_POTION = loadSprite("Strength", 3, 3);
		BOMB = loadSprite("bomb", 1, 1);

		BOSS = loadSprite("Boss", 1, 1);
		GOBLIN = loadSprite("Orc", 8, 8);
		SKELETON = loadSprite("", 1, 1);
		MERCHANT = loadSprite("", 1, 1);
		VILLAGER = loadSprite("", 1, 1);
		
		BUSH = loadSprite("Bush", 3, 2);
		CHEST = loadSprite("Chest", 2, 2);
		TORCH = loadSprite("torch", 2, 3);
		MAZE_WALL = loadSprite("MazeWall", 1, 1);
		MAZE_ENTRANCE = loadSprite("MazeEntrance", 1, 1);
		DUNGEON_WALL = loadSprite("DungeonWall", 3, 2);
		DUNGEON_ENTRANCE = loadSprite("DungeonEntrance", 1, 1);

		MELEE = loadSprite("Melee", 8, 7);
		RANGE = loadSprite("Range", 8, 7);
		
		ENERGYBALL = loadSprite("EnergyBall",1,4);
		
		RED_HEART = loadTile("red_heart");
		BLUE_HEART = loadTile("blue_heart");
		BLACK_HEART = loadTile("empty_heart");
	}

	public BufferedImage[] loadSprite(String filename, int nrows, int ncols) throws IOException {
		File imageFile = new File(this.path + filename + ".png");
		if (!imageFile.exists()) {
			imageFile = new File(this.path + "default.png");
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
	}

	public BufferedImage loadTile(String filename) throws IOException {
		File imageFile = new File(this.path + filename + ".png");
		if (!imageFile.exists()) {
			imageFile = new File(this.path + "default.png");
		}
		return ImageIO.read(imageFile);
	}
}
