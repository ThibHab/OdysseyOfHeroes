package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import info3.game.automata.Aut_Automaton;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;
import info3.game.map.Tile;

public class Tree extends DecorElement {
	
	public TransparencyBlock[] tpBlock;
	
	public Tree(Location l,Tile tile) {
		super();
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
		this.sprites =  ImagesConst.TREE;
		this.imageIndex = 0;
		// -----------------------------------
		
		this.width = 3;
		this.height = 3;
		
		this.scale = EntitiesConst.TREE_SCALE;
		int ii=0;
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				int x=(int)(location.getX()-1+i);
				int y=(int)(location.getY()-2+j);
				if(location.getX()!=x && location.getY()!=y) {
					tile.tpBlock=new TransparencyBlock(x,y,this);
				}
			}
		}
	}
	
	public void paint(Graphics g, int tileSize, float screenPosX, float screenPosY) {
		BufferedImage img=sprites[imageIndex];
		g.drawImage(img, (int)(screenPosX-tileSize), (int)(screenPosY-2*tileSize), (int)(tileSize*scale*width), (int)(tileSize*scale*height), null);
	}
}
