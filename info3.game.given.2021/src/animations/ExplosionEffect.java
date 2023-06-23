package animations;

import info3.game.constants.AnimConst;
import info3.game.constants.ImagesConst;
import info3.game.entity.Location;

public class ExplosionEffect extends Effect {
	public ExplosionEffect(Location l) {
		super(l, null);
		this.sprites = ImagesConst.EXPLOSION_EFFECT;
		this.totalTime = AnimConst.TIME_EXPLOSION_EFFECT;
		this.height = 3f;
		this.width = 3f;
	}
}
