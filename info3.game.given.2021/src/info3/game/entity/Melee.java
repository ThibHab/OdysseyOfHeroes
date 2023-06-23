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
		this.weaponDamage++;
		this.maxHealth += 1;
		this.health = this.maxHealth;
	}
	
    // function called only in the dungeon map
	public void lightAround() {
		EntitiesConst.MAP_MATRIX[(int) this.location.getX()][(int) this.location.getY()].opacity = 0f;
		
		int x = (int) this.location.getX() + 1;
		int y = (int) this.location.getY() - 1;
		
		EntitiesConst.MAP_MATRIX[x][y].opacity = 0.5f;
		x--;
		EntitiesConst.MAP_MATRIX[x][y].opacity = 0f;
		x--;
		EntitiesConst.MAP_MATRIX[x][y].opacity = 0.5f;
		y++;
		EntitiesConst.MAP_MATRIX[x][y].opacity = 0f;
		y++;
		EntitiesConst.MAP_MATRIX[x][y].opacity = 0.5f;
		x++;
		EntitiesConst.MAP_MATRIX[x][y].opacity = 0f;
		x++;
		EntitiesConst.MAP_MATRIX[x][y].opacity = 0.5f;
		y--;
		EntitiesConst.MAP_MATRIX[x][y].opacity = 0f;
	}
}
