package info3.game.entity;

import java.io.RandomAccessFile;

import animations.Animation;
import animations.SpearEffect;
import animations.SwordEffect;
import info3.game.Game;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Category;
import info3.game.automata.Aut_Direction;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;
import info3.game.constants.MapConstants;
import info3.game.map.Map;
import info3.game.map.MapRender;
import info3.game.sound.RandomFileInputStream;

public class Melee extends Hero {
	public Melee(String name, Game g) {
		super();
		this.name = name;
		this.weaponDamage = EntitiesConst.MELEE_DAMAGE;
		this.weaponRange = EntitiesConst.MELEE_RANGE;
		this.attackSpeed = 300;
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
		this.hitbox = new Hitbox(this, (float) 0.50, (float) 0.65);
	}

	@Override
	public void tick(long elapsed) {
		if (this.mazeCounterActivated) {
			this.mazeCounter += elapsed;
			if (this.mazeCounter >= EntitiesConst.MAZE_COUNTER_LIMIT) {
				EntitiesConst.GAME.map = MapConstants.WORLD_MAP;
				EntitiesConst.MAP = (Map) EntitiesConst.GAME.map;
				EntitiesConst.MAP_MATRIX = EntitiesConst.MAP.map;
				if (EntitiesConst.GAME.previousMap == 1) {
					EntitiesConst.MAP.setPlayer(EntitiesConst.MAZE_ENTRANCE_X_POS - 1,
							EntitiesConst.MAZE_ENTRANCE_Y_POS + 1, this);
					EntitiesConst.MAP.setPlayer(EntitiesConst.MAZE_ENTRANCE_X_POS + 1,
							EntitiesConst.MAZE_ENTRANCE_Y_POS + 1, EntitiesConst.GAME.player2);
				} else if (EntitiesConst.GAME.previousMap == 2) {
					EntitiesConst.MAP.setPlayer(EntitiesConst.DUNGEON_ENTRANCE_X_POS - 1,
							EntitiesConst.DUNGEON_ENTRANCE_Y_POS + 1, this);
					EntitiesConst.MAP.setPlayer(EntitiesConst.DUNGEON_ENTRANCE_X_POS + 1,
							EntitiesConst.DUNGEON_ENTRANCE_Y_POS + 1, EntitiesConst.GAME.player2);
				}
				EntitiesConst.GAME.render = new MapRender(EntitiesConst.MAP, EntitiesConst.GAME);
				EntitiesConst.GAME.render.updateCam(this, EntitiesConst.GAME.player2,
						EntitiesConst.GAME.m_canvas.getWidth(), EntitiesConst.GAME.m_canvas.getHeight());
				EntitiesConst.GAME.render.setOffsetCam();
				this.mazeCounterActivated = false;
				this.mazeCounter = 0;
			}
		}

		super.tick(elapsed);
	}

	@Override
	public void Power() {
		if (this.healingPotions > 0) {
			Range otherPlayer = EntitiesConst.GAME.player2;
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

		if (this.dead == false)
			this.health = this.maxHealth;
	}

	@Override
	public void attackEffect(Location t) {
		new SwordEffect(t, this.direction);
	}
}
