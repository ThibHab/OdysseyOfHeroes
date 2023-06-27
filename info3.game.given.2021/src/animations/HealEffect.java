package animations;

import info3.game.constants.AnimConst;
import info3.game.constants.ImagesConst;
import info3.game.entity.Location;

public class HealEffect extends Effect{
	public HealEffect(Location l) {
		super(l, null);
		this.sprites = ImagesConst.HEAL_EFFECT;
		this.totalTime = AnimConst.TIME_HEAL_EFFECT;
		this.height = 4f;
		this.width = 4f;
	}
}
