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

public class Melee extends Hero{
	public Melee(String name, Game g) {
		super();
		this.name = name;
		this.weaponDamage = EntitiesConst.MELEE_DAMAGE;
		this.weaponRange = EntitiesConst.MELEE_RANGE;
		this.health = 12;
		this.maxHealth = this.health;

		for (Aut_Automaton next : g.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		
		Aut_Direction dirs[] = new Aut_Direction[] { Aut_Direction.S, Aut_Direction.E, Aut_Direction.N,
				Aut_Direction.W };
		Action acts[] = new Action[] { Action.S, Action.M, Action.H, Action.T, Action.D };
		this.anim = new Animation(this, ImagesConst.MELEE, dirs, acts);
		this.hitbox = new Hitbox(this, (float)0.50, (float)0.65);
	}
	
	@Override
	public void Wizz(Aut_Direction d, Aut_Category c) {
		Hero otherPlayer = EntitiesConst.GAME.player2;
		if (otherPlayer.dead && this.healingPotions > 0) {
			this.healingPotions--;
			Wait(1000);
		}
	}
	
	@Override
	public void waited() {
		this.actionIndex = 0;
		EntitiesConst.GAME.player2.revive();
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
		this.weaponDamage += 2;

		if (Hero.level % 2 == 0 && this.maxHealth < 20) {
			this.maxHealth += 1;
		}

		this.health = this.maxHealth;
	}
	
}
