package info3.game.entity;

import animations.Animation;
import info3.game.automata.Aut_Direction;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.ImagesConst;

public class EnergyBall extends Projectile {

	public EnergyBall(Entity owner, Aut_Direction d) {
		super(owner, d);
		Action acts[] = new Action[] { Action.M };
		this.anim = new Animation(this, ImagesConst.ENERGYBALL, null, acts);
	}
	
	@Override
	public int getNbActionSprite(Action a) {
		switch (a) {
		case M:
			return AnimConst.ENERGY_B_M;
		case H:
			return 0;
		case T:
			return 0;
		case D:
			return 0;
		case S:
			return 0;
		default:
			return 0;
		}
	}

	@Override
	public int totSrpitePerDir() {
		return AnimConst.ENERGY_B_M;
	}

}
