package info3.game.entity;

import animations.Animation;
import animations.SwordEffect;
import info3.game.Game;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Direction;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Melee extends Hero {
	public Melee(String name, Game g) {
		super();
		this.name = name;
		this.weaponDamage = EntitiesConst.MELEE_DAMAGE;
		this.weaponRange = EntitiesConst.MELEE_RANGE;
		this.attackSpeed = 300;
		this.health = 12;
		this.maxHealth = this.health;
		this.healingPotions = EntitiesConst.HEALING_POTIONS;

		for (Aut_Automaton next : g.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;

		Aut_Direction dirs[] = new Aut_Direction[] { Aut_Direction.S, Aut_Direction.E, Aut_Direction.N,
				Aut_Direction.W };
		Action acts[] = new Action[] { Action.S, Action.M, Action.H, Action.T, Action.D };
		this.anim = new Animation(this, ImagesConst.MELEE, dirs, acts);
	}

	@Override
	public void Power() {
		if (this.healingPotions > 0) {
			Range otherPlayer = EntitiesConst.GAME.player2;
			Location loc = frontTileLocation(Aut_Direction.F.rightDirection(this));
			if (EntitiesConst.MAP_MATRIX[(int) loc.getX()][(int) loc.getY()].entity == otherPlayer
					&& otherPlayer.dead) {
				this.Wait(1000);
			} else {
				if (this.health < this.maxHealth) {
					this.heal();
					this.healingPotions--;
					Wait(200);
				}
			}
		}
	}

	@Override
	public void waited() {
		this.actionIndex = 0;
		Range otherPlayer = EntitiesConst.GAME.player2;
		Location loc = frontTileLocation(Aut_Direction.F.rightDirection(this));
		if (EntitiesConst.MAP_MATRIX[(int) loc.getX()][(int) loc.getY()].entity == otherPlayer && otherPlayer.dead) {
			this.healingPotions--;
			otherPlayer.revive();
		}
	}

	@Override
	public int getNbActionSprite(Action a) {
		switch (a) {
		case M:
			return AnimConst.MELEE_M;
		case H:
			return AnimConst.MELEE_H;
		case T:
			return AnimConst.MELEE_T;
		case D:
			return AnimConst.MELEE_D;
		case S:
			return AnimConst.MELEE_S;
		default:
			return 0;
		}
	}

	@Override
	public int totSrpitePerDir() {
		return AnimConst.MELEE_TOT;
	}

	@Override
	public void updateStats() {
		this.weaponDamage += 1;

		if (Hero.level % 2 == 0 && this.maxHealth < 20) {
			this.maxHealth += 1;
		}

		if (!this.dead)
			this.health = this.maxHealth;
	}

	@Override
	public void attackEffect(Location t) {
		new SwordEffect(t, this.direction);
	}
}
