	package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Category;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Bush extends DecorElement {
	public Bush(Location l) {
		super();
		this.name = "Bush";
		this.location = l;
		this.health = EntitiesConst.BUSH_HEALTH;
		this.category = Aut_Category.C;

		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;

		Action acts[] = new Action[] { Action.S, Action.H };
		this.anim = new Animation(this,ImagesConst.BUSH, null, acts);

		this.width = 1;
		this.height = 1;

		if (this.location != null) {
			this.hitbox = new Hitbox(this, (float) 0.90, (float) 0.90);
		}
		
		this.scale = EntitiesConst.BUSH_SCALE;		
	}
	
	@Override
	public void takeDamage(Entity attacker) {	
		System.out.println("HEHO CA FAIT MALEUH");
		if (this.health - attacker.weaponDamage > 0) {
			this.health -= attacker.weaponDamage;
			if (this.action != Action.T) {
				if (EntitiesConst.GAME.debug) {
					System.out.println(this.name + " is touched");
				}
			}
		} else {
			this.health = 0;
			this.die();
		}
	}
	public void paint(Graphics g, int tileSize, float screenPosX, float screenPosY) {
		BufferedImage img=anim.getFrame();
		int diff=(int) (tileSize*(scale-1))/2;
		g.drawImage(img, (int)screenPosX-diff, (int)screenPosY-diff, (int)(tileSize*scale), (int)(tileSize*scale), null);
	}
		
	
	@Override
	public int getNbActionSprite(Action a) {
		//TODO
		switch (a) {
		case M:
			return 0;
		case H:
			return AnimConst.BUSH_H;
		case T:
			return 0;
		case D:
			return 0;
		case S:
			return AnimConst.BUSH_S;
		default:
			return 0;
		}
	}

	@Override
	public int totSrpitePerDir() {
		//TODO
		return AnimConst.BUSH_TOT;
	}
}
