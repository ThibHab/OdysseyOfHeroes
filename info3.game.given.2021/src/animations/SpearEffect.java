package animations;

import info3.game.automata.Aut_Direction;
import info3.game.constants.AnimConst;
import info3.game.constants.ImagesConst;
import info3.game.entity.Location;

public class SpearEffect extends Effect {
	public SpearEffect(Location l, Aut_Direction d) {
		super(l, d);
		if(d !=null && (d == Aut_Direction.N || d == Aut_Direction.S)) {
			this.sprites = Effect.rotateckwise(ImagesConst.SPEAR_EFFECT);
		}else {
			this.sprites = ImagesConst.SPEAR_EFFECT;
		}
		this.totalTime = AnimConst.TIME_SPEAR_EFFECT;
		this.height = 1f;
		this.width = 1f;
	}
}
