package info3.game.entity;

import animations.Animation;
import info3.game.Game;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Category;
import info3.game.automata.Aut_Direction;
import info3.game.automata.ast.Direction;
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
	public void Pop(Aut_Direction d, Aut_Category c) {
		// TODO Auto-generated method stub
		super.Pop(d, c);
	}

	@Override
	public void Wizz(Aut_Direction d, Aut_Category c) {
		// TODO Auto-generated method stub
		super.Wizz(d, c);
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

		if (Hero.level % 2 == 0) {
			this.maxHealth += 1;
		}

		this.health = this.maxHealth;
	}

	@Override
	public int totSrpitePerDir() {
		return AnimConst.RANGE_TOT;
	}
}
