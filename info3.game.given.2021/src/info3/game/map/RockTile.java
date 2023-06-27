package info3.game.map;

import info3.game.constants.ImagesConst;
import info3.game.entity.Location;

public class RockTile extends Tile {

	public RockTile(Location location) {
		super(location, false, 0.0f, ImagesConst.ROCK_TILE);
	}

}
