package info3.game.map;

import info3.game.constants.ImagesConst;
import info3.game.entity.Location;

public class DirtTile extends Tile {

	public DirtTile(Location location) {
		super(location, true, 0.0f,ImagesConst.dirtTile);
	}

}
