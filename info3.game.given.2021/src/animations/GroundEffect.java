package animations;

import info3.game.constants.AnimConst;
import info3.game.constants.ImagesConst;
import info3.game.entity.Location;

public class GroundEffect extends Effect {
	public GroundEffect(Location l) {
		super(l, null);
		this.sprites = ImagesConst.GROUND_EFFECT;
		this.totalTime = AnimConst.TIME_GROUND_EFFECT;
		this.height = 1f;
		this.width = 1f;
	}
}
