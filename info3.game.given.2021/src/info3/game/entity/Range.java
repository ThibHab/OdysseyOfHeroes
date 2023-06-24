package info3.game.entity;

import animations.Animation;
import info3.game.Game;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Category;
import info3.game.automata.Aut_Direction;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Range extends Hero {
	public Range(String name, Game g) {
		super();
		this.name = name;
		this.weaponDamage = EntitiesConst.RANGE_DAMAGE;
		this.weaponRange = EntitiesConst.RANGE_RANGE;
		this.health = 8;
		this.maxHealth = this.health;
		this.range = 3;

		for (Aut_Automaton next : g.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;

		Aut_Direction dirs[] = new Aut_Direction[] { Aut_Direction.S, Aut_Direction.E, Aut_Direction.N,
				Aut_Direction.W };
		Action acts[] = new Action[] { Action.S, Action.M, Action.H, Action.T, Action.D };
		this.anim = new Animation(this, ImagesConst.RANGE, dirs, acts);
		this.hitbox = new Hitbox(this, (float) 0.50, (float) 0.60);
	}

	@Override
	public void Power() {
		if (this.healingPotions > 0) {
			Melee otherPlayer = EntitiesConst.GAME.player1;
			Location loc = frontTileLocation(Aut_Direction.F.rightDirection(this));
			if (EntitiesConst.MAP_MATRIX[(int) loc.getX()][(int) loc.getY()].entity == otherPlayer && otherPlayer.dead) {
				this.Wait(1000);
			} else {
				this.heal();
			}
		}
	}
	
	@Override
	public void waited() {
		this.actionIndex = 0;
		EntitiesConst.GAME.player1.revive();
	}

	@Override
	public void Hit(Aut_Direction d) {
		if (!this.frozen) {
			this.frozen = true;
			if (d != null) {
				this.direction = d;
			}
			if (this.action != Action.H) {
				this.action = Action.H;
				this.anim.changeAction(action);
			}
			this.hitFrozen = true;
			this.frozen = true;
			Projectile p = new Projectile(this, this.direction);
		}
	}

	@Override
	public int getNbActionSprite(Action a) {
		// TODO
		switch (a) {
		case M:
			return AnimConst.RANGE_M;
		case H:
			return AnimConst.RANGE_H;
		case T:
			return AnimConst.RANGE_T;
		case D:
			return AnimConst.RANGE_D;
		case S:
			return AnimConst.RANGE_S;
		default:
			return 0;
		}
	}

	@Override
	public void updateStats() {
		this.weaponDamage += 2;

		if (Hero.level % 2 == 0 && this.maxHealth < 13) {
			this.maxHealth += 1;
		}

		if (Hero.level % 5 == 0) {
			this.range += 1;
		}

		if (this.dead = false)
			this.health = this.maxHealth;
	}

	@Override
	public int totSrpitePerDir() {
		return AnimConst.RANGE_TOT;
	}
	
	public static void unlockFire() {
		Hero.firePowerUnlocked = true;
		ImagesConst.loadFire();
		EntitiesConst.ENERGYBALL_SCALE = 0.8f;
	}
}
