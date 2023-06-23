package info3.game.map;

import java.awt.image.BufferedImage;

import info3.game.constants.ImagesConst;
import info3.game.entity.Location;

public class SaveTile extends Tile {

	public SaveTile(Location location) {
		super(location, true, 0f, ImagesConst.SAVE_TILES[0]);
	}
	
	public void changeTile(boolean saved) {
		if (saved)
			this.image = ImagesConst.SAVE_TILES[1];
		else
			this.image = ImagesConst.SAVE_TILES[0];
	}

}
