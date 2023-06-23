package animations;

import info3.game.constants.AnimConst;
import info3.game.constants.ImagesConst;
import info3.game.entity.Location;

public class SmokeEffect extends Effect{
	public SmokeEffect(Location l) {
		super(l, null);
		this.sprites = ImagesConst.SMOKE_EFFECT;
		this.totalTime = AnimConst.TIME_SMOKE_EFFECT;
		this.height = 1f;
		this.width = 1f;
	}
}
