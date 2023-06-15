package info3.game.constants;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImagesConst {
	String path;

	public static BufferedImage[] GRASS_TILE, WATER_TILE, DIRT_TILE, ROCK_TILE;
	public static BufferedImage[] HOUSE, ROCK, TREE;
	public static BufferedImage[] COIN, HEALING_POTION, STRENGTH_POTION;
	public static BufferedImage[] BOSS, GOBLIN, SKELETON, MERCHANT, VILLAGER;
	public static BufferedImage[] BUSH, CHEST;
	public static BufferedImage[] MELEE, RANGE;

	public ImagesConst() throws IOException {
		path = "ressources/sprites/";

		GRASS_TILE = loadSprite("grass_placeholder", 1, 1);
		WATER_TILE = loadSprite("water_placeholder", 1, 1);
		DIRT_TILE = loadSprite("dirt_placeholder", 1, 1);
		ROCK_TILE = loadSprite("rock_placeholder", 1, 1);

		BUSH = loadSprite("bush_placeholder", 1, 1);

		RANGE = loadSprite("range", 8, 7);
	}

	public BufferedImage[] loadSprite(String filename, int nrows, int ncols) throws IOException {
		File imageFile = new File(this.path + filename + ".png");
		if (imageFile.exists()) {
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
		return null;
	}
}
