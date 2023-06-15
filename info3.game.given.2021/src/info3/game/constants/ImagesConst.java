package info3.game.constants;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImagesConst {

	public static BufferedImage grassTile, waterTile, dirtTile, rockTile;
	public static BufferedImage house, rock, tree;
	public static BufferedImage coin, healingPotion, strengthPotion;
	public static BufferedImage[] boss, goblin, skeleton, merchant, villager;
	public static BufferedImage[] bush, chest;
	public static BufferedImage[] melee, range;

	public ImagesConst() throws IOException {
		File imageFile = new File("resources/grass_placeholder.png");
		if (imageFile.exists()) {
			grassTile = ImageIO.read(imageFile);
		}
		imageFile = new File("resources/water_placeholder.png");
		if (imageFile.exists()) {
			waterTile = ImageIO.read(imageFile);
		}
		imageFile = new File("resources/dirt_placeholder.png");
		if (imageFile.exists()) {
			dirtTile = ImageIO.read(imageFile);
		}
		imageFile = new File("resources/rock_placeholder.png");
		if (imageFile.exists()) {
			rockTile = ImageIO.read(imageFile);
		}
		//TODO initialize properly all the sprites
	}
}
