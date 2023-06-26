package info3.game.entity;

import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Category;
import info3.game.automata.Aut_Direction;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Skeleton extends Mob {
	public Skeleton(Location l) {
		super();
		this.name = "Skeleton";
		this.location = l;
		this.health = EntitiesConst.SKELETON_HEALTH;
		this.weaponDamage = EntitiesConst.SKELETON_DAMAGE;
		this.weaponRange = EntitiesConst.SKELETON_RANGE;
		this.scale = EntitiesConst.SKELETON_SCALE;
		this.detectionRadius = EntitiesConst.SKELETON_DETECTION;
		this.attackSpeed = 1000;
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;

		this.category = Aut_Category.A;

		// TODO add sprites and actions
		Aut_Direction dirs[] = new Aut_Direction[] { Aut_Direction.S, Aut_Direction.E, Aut_Direction.W,
				Aut_Direction.N };
		Action acts[] = new Action[] { Action.S, Action.M, Action.H };
		this.anim = new Animation(this, ImagesConst.SKELETON, dirs, acts);
		this.hitbox = new Hitbox(this, (float) 0.50, (float) 0.60);
	}

	@Override
	public void Hit(Aut_Direction d) {
		if (!this.frozen && !this.hitFrozen) {
			this.frozen = true;
			if (d != null) {
				this.direction = d;
			}
			if (this.action != Action.H) {
				this.action = Action.H;
				this.anim.changeAction(action);
			}
			this.hitFrozen = true;
			if (EntitiesConst.GAME.debug)
				System.out.println("skeleton shoots");
			EntitiesConst.MAP.projectiles.add(new Bone(this, this.direction));
		}
	}

	@Override
	public int getNbActionSprite(Action a) {
		// TODO
		switch (a) {
		case M:
			return AnimConst.SKELETON_M;
		case H:
			return AnimConst.SKELETON_H;
		case T:
			return AnimConst.SKELETON_T;
		case D:
			return AnimConst.SKELETON_D;
		case S:
			return AnimConst.SKELETON_S;
		default:
			return 0;
		}
	}

	@Override
	public int totSrpitePerDir() {
		return AnimConst.SKELETON_TOT;
	}
}
