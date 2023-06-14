package info3.game.constants;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImagesConst {
	
	public static BufferedImage grassTile;
	public static BufferedImage waterTile;
	public static BufferedImage dirtTile;
	public static BufferedImage rockTile;
	
	
	
	public ImagesConst() throws IOException {
		File imageFile = new File("resources/grass_placeholder");
		if (imageFile.exists()) {
		      grassTile = ImageIO.read(imageFile);
		}
		imageFile = new File("resources/water_placeholder");
		if (imageFile.exists()) {
		      waterTile = ImageIO.read(imageFile);
		}
		imageFile = new File("resources/dirt_placeholder");
		if (imageFile.exists()) {
		      dirtTile = ImageIO.read(imageFile);
		}
		imageFile = new File("resources/rock_placeholder");
		if (imageFile.exists()) {
		      rockTile = ImageIO.read(imageFile);
		}
	}
}
