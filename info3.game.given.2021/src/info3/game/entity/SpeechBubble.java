package info3.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.automata.Aut_Automaton;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class SpeechBubble extends Entity {
	
	public float scale = EntitiesConst.SPEECHBUBBLE_SCALE;
	public String dialog;
	public Villager v;
	
	
	
	public SpeechBubble(Villager e, String dialog) {
		this.name = "Rock";
		this.dialog = dialog;
		this.v = e;
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		this.sprites = ImagesConst.SPEECHBUBBLE;
		EntitiesConst.MAP.bubbles.add(this);
	}
	
	public void paint(Graphics g, int tileSize, float screenPosX, float screenPosY) {
		BufferedImage img = this.sprites[0];
		Location pixel = EntitiesConst.GAME.render.gridToPixel(this.v.location, true);
		int dimension = (int) (scale * tileSize);
		float shiftXY = ((scale - 1) / 2) * tileSize;
		int positionX = (int) (pixel.getX() - shiftXY);
		int positionY = (int) (pixel.getY() - shiftXY);
		g.drawImage(img, positionX - tileSize/2 + tileSize/5, positionY - tileSize/2 -tileSize/4, dimension * 3, (dimension * 2)/2, null);
		g.setColor(Color.black);
		g.drawString(dialog, positionX - tileSize/2 + tileSize/3, positionY - tileSize/2);
	}
}
