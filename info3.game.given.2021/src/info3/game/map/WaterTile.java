package info3.game.map;

import info3.game.constants.ImagesConst;
import info3.game.entity.Location;

public class WaterTile extends Tile {

	public WaterTile(Location location) {
		super(location, false, 0.0f, ImagesConst.WATER_TILE);
	}

}
