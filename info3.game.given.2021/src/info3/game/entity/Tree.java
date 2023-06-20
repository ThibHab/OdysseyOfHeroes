package info3.game.entity;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import info3.game.automata.Aut_Automaton;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Tree extends TransparentDecorElement {
	
	public Tree(Location l) {
		super(8);
		this.name = "Tree";
		this.location = l;

		// --- TODO manage automaton ---
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		// -----------------------------

		// --- TODO manage sprite properly ---
		if (forest)
			this.sprites = ImagesConst.FOREST_TREE;
		else
			this.sprites = ImagesConst.TREE;
		this.imageIndex = 0;
		// -----------------------------------
		
		this.width = 3;
		this.height = 3;
		
		this.scale = EntitiesConst.TREE_SCALE;
	}
	
	public void paint(Graphics g, int tileSize, float screenPosX, float screenPosY) {
		BufferedImage img=sprites[0];
		if(this.transparent) {
			Graphics2D gr=(Graphics2D)g;
			gr.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,(float)(EntitiesConst.TREE_OPACITY/this.opacityDiv)));
			gr.drawImage(img, (int)(screenPosX-tileSize), (int)(screenPosY-2*tileSize), (int)(tileSize*scale*width), (int)(tileSize*scale*height), null);
			gr.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1));
		}else {
			g.drawImage(img, (int)(screenPosX-tileSize), (int)(screenPosY-2*tileSize), (int)(tileSize*scale*width), (int)(tileSize*scale*height), null);
		}
		
	}
}
