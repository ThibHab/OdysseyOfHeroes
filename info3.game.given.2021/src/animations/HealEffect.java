package animations;

import info3.game.automata.Aut_Direction;
import info3.game.constants.AnimConst;
import info3.game.constants.ImagesConst;
import info3.game.entity.Location;

public class HealEffect extends Effect{
	public HealEffect(Location l) {
		super(l, null);
		this.sprites = ImagesConst.HEAL_EFFECT;
		this.totalTime = AnimConst.TIME_HEAL_EFFECT;
		this.height = 1f;
		this.width = 1f;
	}
}
