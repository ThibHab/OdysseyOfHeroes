package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.automata.Aut_Automaton;
import info3.game.constants.Action;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Bush extends DecorElement {
	public Bush(Location l) {
		super();
		this.name = "Bush";
		this.location = l;
		this.health = EntitiesConst.BUSH_HEALTH;

		// --- TODO manage automaton ---
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		// -----------------------------

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.BUSH;
		this.imageIndex = 0;
		// -----------------------------------
		
		this.width = 1;
		this.height = 1;
		
		if(this.location != null) {
			this.hitbox = new Hitbox(this, (float)0.90, (float)0.90);
		}
		
		this.scale = EntitiesConst.BUSH_SCALE;
		
		
	}
	
	@Override
	public void takeDamage(int dmg) {
		System.out.println("HEHO CA FAIT MALEUH");
		if (this.health - dmg > 0) {
			this.health -= dmg;
			if (this.action != Action.T) {
				if (EntitiesConst.GAME.debug) {
					System.out.println(this.name + " is touched");
				}
				this.imageIndex = this.sprites.length;
				this.action = Action.T;
				this.updateSpriteIndex();
			}
		} else {
			this.health = 0;
			this.die();
		}
	}
	public void paint(Graphics g, int tileSize, float screenPosX, float screenPosY) {
		BufferedImage img=sprites[1];
		int diff=(int) (tileSize*(scale-1))/2;
		g.drawImage(img, (int)screenPosX-diff, (int)screenPosY-diff, (int)(tileSize*scale), (int)(tileSize*scale), null);
	}
		
}
