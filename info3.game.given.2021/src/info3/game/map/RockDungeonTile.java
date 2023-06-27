package info3.game.map;

import info3.game.constants.ImagesConst;
import info3.game.entity.Location;

public class RockDungeonTile extends Tile {

	public RockDungeonTile(Location location) {
		super(location, true, 1.0f, ImagesConst.ROCK_DUNGEON);
	}

}
