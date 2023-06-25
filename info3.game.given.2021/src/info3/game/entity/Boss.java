package info3.game.entity;

import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Category;
import info3.game.automata.Aut_Direction;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Boss extends Mob {
	public Boss(Location l) {
		super();
		this.name = "Boss";
		this.location = l;
		this.health = EntitiesConst.BOSS_HEALTH;
		this.weaponDamage = EntitiesConst.BOSS_DAMAGE;
		this.weaponRange = EntitiesConst.BOSS_RANGE;

		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		this.category = Aut_Category.A;
		
		Aut_Direction dirs[] = new Aut_Direction[] { Aut_Direction.N, Aut_Direction.S, Aut_Direction.E,
				Aut_Direction.W };
		Action acts[] = new Action[] { Action.M };
		this.anim = new Animation(this, ImagesConst.BOSS, dirs, acts);
	}

	@Override
	public void Hit(Aut_Direction d) {
		// TODO Auto-generated method stub
		super.Hit(d);
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
	public void Power() {
		// TODO Auto-generated method stub
		super.Power();
	}

	@Override
	public void Throw(Aut_Direction d, Aut_Category category) {
		// TODO Auto-generated method stub
		super.Throw(d, category);
	}

	@Override
	public int getNbActionSprite(Action a) {
		switch (a) {
		case M:
			return AnimConst.BOSS_M;
		case H:
			return AnimConst.BOSS_H;
		case T:
			return AnimConst.BOSS_T;
		case D:
			return AnimConst.BOSS_D;
		case S:
			return AnimConst.BOSS_S;
		default:
			return 0;
		}
	}

	@Override
	public int totSrpitePerDir() {
		return AnimConst.BOSS_TOT;
	}
}
