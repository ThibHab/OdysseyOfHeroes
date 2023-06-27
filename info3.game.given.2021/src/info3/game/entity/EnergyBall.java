package info3.game.entity;

import animations.Animation;
import info3.game.automata.Aut_Direction;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class EnergyBall extends Projectile {

	public EnergyBall(Entity owner, Aut_Direction d) {
		super(owner, d);
		Action acts[] = new Action[] { Action.M };
		if (Hero.firePowerUnlocked) {
			this.anim = new Animation(this, ImagesConst.FIREBALL, null, acts);
			this.scale = EntitiesConst.FIREBALL_SCALE;
		} else {
			this.anim = new Animation(this, ImagesConst.ENERGYBALL, null, acts);
			this.scale = EntitiesConst.ENERGYBALL_SCALE;
		}
	}

	@Override
	public int getNbActionSprite(Action a) {
		switch (a) {
		case M:
			return AnimConst.ENERGY_B_M;
		default:
			return 0;
		}
	}

	@Override
	public int totSrpitePerDir() {
		return AnimConst.ENERGY_B_M;
	}

}
