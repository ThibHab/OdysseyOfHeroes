package info3.game.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
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
		Action acts[] = new Action[] { Action.S };
		this.anim = new Animation(this, ImagesConst.SPEECHBUBBLE, null, acts);
	}
	
	@Override
	public int getNbActionSprite(Action a) {
		switch (a) {
		case M:
			return AnimConst.SPEECHBUBBLE_S;
		case S:
			return 0;
		default:
			return 0;
		}
	}
	
	public void paint(Graphics g, int tileSize, float screenPosX, float screenPosY) {
		BufferedImage img = anim.getFrame();
		Location pixel = EntitiesConst.GAME.render.gridToPixel(this.v.location, true);
		int dimension = (int) (scale * tileSize);
		float shiftXY = ((scale - 1) / 2) * tileSize;
		int positionX = (int) (pixel.getX() - shiftXY);
		int positionY = (int) (pixel.getY() - shiftXY);
		g.drawImage(img, positionX - tileSize + tileSize/3, positionY - tileSize - tileSize/4, dimension * 4, (dimension * 3)/2, null);
		Font f = new Font(null,0,tileSize/5);
		g.setFont(f);
		g.setColor(Color.black);
		String[] sentences = dialog.split("\n");
		int y = positionY - tileSize - tileSize/4 + tileSize/3;
		for (int i = 0; i < sentences.length; i++) {
			g.drawString(sentences[i], positionX - tileSize + tileSize/2, y);
			y += tileSize/5;
		}
	}
}
