package animations;

import info3.game.automata.Aut_Direction;
import info3.game.constants.AnimConst;
import info3.game.entity.Location;

public class SwordEffect extends Effect{
	
	public SwordEffect(Location l, Aut_Direction d) {
		super(l, d);
		this.sprites = null;
		this.totalTime = AnimConst.TIME_SWORD_EFFECT;
	}
}
