package info3.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.Game;
import info3.game.automata.*;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Melee extends Hero {
	public Melee(String name, Location l) {
		super();
		this.name = name;
		this.location = l;
		this.weaponDamage = EntitiesConst.MELEE_DAMAGE;
		this.weaponRange = EntitiesConst.MELEE_RANGE;
		this.health = EntitiesConst.MELEE_HEALTH;

		// --- TODO manage automaton ---
		this.automaton = null;
		this.currentState = null;
		// -----------------------------

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.MELEE;
		this.imageIndex = 0;
		// -----------------------------------
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
	public void updateSpriteIndex() {
		int spr_dir = AnimConst.MELEE_D + AnimConst.MELEE_H + AnimConst.MELEE_M + AnimConst.MELEE_S + AnimConst.MELEE_T;

		int idx = 0;
		switch (this.direction) {
		case S:
			break;
		case E:
			idx += (1 * spr_dir);
			break;
		case N:
			idx += (2 * spr_dir);
			break;
		case W:
			idx += (3 * spr_dir);
			break;
		default:
			break;
		}
		if (this.action == Action.S) {
			if(idx + this.imageIndex < idx + AnimConst.MELEE_S) {
				this.imageIndex = idx + this.imageIndex + 1;
			}
			this.imageIndex = idx;
		}
		idx += AnimConst.MELEE_S;
		if (this.action == Action.M) {
			if(idx + this.imageIndex < idx + AnimConst.MELEE_M) {
				this.imageIndex = idx + this.imageIndex + 1;
			}
			this.imageIndex = idx;
		}
		idx += AnimConst.MELEE_M;
		if (this.action == Action.H) {
			if(idx + this.imageIndex < idx + AnimConst.MELEE_H) {
				this.imageIndex = idx + this.imageIndex + 1;
			}
			this.imageIndex = idx;
		}
		idx += AnimConst.MELEE_H;
		if (this.action == Action.T) {
			if(idx + this.imageIndex < idx + AnimConst.MELEE_T) {
				this.imageIndex = idx + this.imageIndex + 1;
			}
			this.imageIndex = idx;
		}
		idx += AnimConst.MELEE_T;
		if(idx + this.imageIndex < idx + AnimConst.MELEE_D) {
			this.imageIndex = idx + this.imageIndex + 1;
		}
		this.imageIndex = idx;
	}
}
