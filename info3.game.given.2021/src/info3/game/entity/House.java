package info3.game.entity;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import animations.Action;
import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Direction;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class House extends TransparentDecorElement {
	
	
	public House(Location l) {
		super(3);
		this.name = "House";
		this.location = l;
		this.hitbox.update();

		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		
		Action acts[] = new Action[] { Action.S };
		this.anim = new Animation(this,ImagesConst.HOUSE, null, acts);

		this.width = 3;
		this.height = 3;

		this.scale = EntitiesConst.HOUSE_SCALE;
	}
	
	public void paint(Graphics g, int tileSize, float screenPosX, float screenPosY) {
		BufferedImage img=sprites[0];
		if(this.transparent) {
			Graphics2D gr=(Graphics2D)g;
			gr.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,(float)(EntitiesConst.HOUSE_OPACITY/this.opacityDiv)));
			gr.drawImage(img, (int)(screenPosX-tileSize), (int)(screenPosY-2*tileSize), (int)(tileSize*scale*width), (int)(tileSize*scale*height), null);
			gr.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1));
		}else {
			g.drawImage(img, (int)(screenPosX-tileSize), (int)(screenPosY-2*tileSize), (int)(tileSize*scale*width), (int)(tileSize*scale*height), null);
		}
	}
}
