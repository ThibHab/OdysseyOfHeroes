package info3.game.map;

import java.awt.image.BufferedImage;

import info3.game.constants.ImagesConst;
import info3.game.entity.Location;

public class DungeonWalls extends Tile {
	
	Location loc;

	public DungeonWalls(Location location, int index) {
		super(location, false, 0.0f, ImagesConst.DUNGEON_WALL[index]);
	}

}
