package info3.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import info3.game.Game;
import info3.game.automata.*;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Villager extends NPC {
	
	public LinkedList<String> dialogs;
	public boolean talking;
	public int dialogIndex;
	
	public Villager(Location l) {
		super();
		this.name = "Rock";
		this.location = l;
		this.talking = false;
		this.dialogIndex = 0;
		this.dialogs = new LinkedList<>();
		this.dialogs.add("SALUT TOI");
		this.dialogs.add("BAISE TA MERE");

		// --- TODO manage automaton ---
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		// -----------------------------
		this.category = Aut_Category.T;

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.MINER;
		this.imageIndex = 0;
		this.scale = EntitiesConst.VILLAGER_SCALE;
		// -----------------------------------
		this.hitbox = new Hitbox(this, (float)0.80,(float)0.90);
	}
	
	public void talks() {
		EntitiesConst.MAP.bubbles.clear();
		if (this.dialogs.size() <= this.dialogIndex) {
			EntitiesConst.MAP.bubbles.clear();
			this.dialogIndex = 0;
		}else {
			EntitiesConst.MAP.bubbles.add(new SpeechBubble(this, this.dialogs.get(dialogIndex++)));
		}
	}
	
	public void paint(Graphics g, int tileSize, float screenPosX, float screenPosY) {
		BufferedImage img = sprites[imageIndex];
		Location pixel = EntitiesConst.GAME.render.gridToPixel(location, true);
		int dimension = (int) (scale * tileSize);
		float shiftXY = ((scale - 1) / 2) * tileSize;
		int positionX = (int) (pixel.getX() - shiftXY);
		int positionY = (int) (pixel.getY() - shiftXY);
		g.drawImage(img, positionX, positionY, dimension, dimension, null);
		Location l = EntitiesConst.GAME.render.gridToPixel(this.hitbox.location, true);

		if (EntitiesConst.GAME.debug) {
			g.drawRect((int) l.getX(), (int) l.getY(), (int) (tileSize * this.hitbox.width),
					(int) (tileSize * this.hitbox.height));
		}
	}
}
