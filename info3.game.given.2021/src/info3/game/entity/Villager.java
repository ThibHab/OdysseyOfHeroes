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

public abstract class Villager extends NPC {
	
	public LinkedList<String> dialogs;
	public boolean talking;
	public int dialogIndex;
	
	public Villager(Location l) {
		super();
		this.location = l;
		this.talking = false;
		this.dialogIndex = 0;
		this.dialogs = new LinkedList<>();

		// --- TODO manage automaton ---
		// -----------------------------
		this.category = Aut_Category.T;

		// --- TODO manage sprite properly ---
		this.imageIndex = 0;
		this.scale = EntitiesConst.VILLAGER_SCALE;
		// -----------------------------------
		this.hitbox = new Hitbox(this, (float)0.80,(float)0.90);
	}
	
	public void talks() {
		if(dialogIndex > 0) {
			for (int i = 0; i < EntitiesConst.MAP.bubbles.size() ; i++) {
				SpeechBubble bubble = EntitiesConst.MAP.bubbles.get(i);
				if (bubble.v == this) {
					EntitiesConst.MAP.bubbles.remove(i);
				}
			}
		}
		if (this.dialogs.size() <= this.dialogIndex) {
			for (int i = 0; i < EntitiesConst.MAP.bubbles.size() ; i++) {
				SpeechBubble bubble = EntitiesConst.MAP.bubbles.get(i);
				if (bubble.v == this) {
					EntitiesConst.MAP.bubbles.remove(i);
				}
			}
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
	
	@Override
	public void Move(Aut_Direction d) {
		float x = this.location.getX();
		float y =this.location.getY();
		Aut_Direction dir = d.rightDirection(this);
		if (x > 35 && dir == Aut_Direction.E  || x < 25 && dir == Aut_Direction.W || y > 35 && dir == Aut_Direction.S || y < 25 && dir == Aut_Direction.N) {
			dir = Aut_Direction.B;
			dir = dir.rightDirection(this);
			super.Move(dir);
		}else {
			super.Move(d);
		}
		if(dialogIndex > 0) {
			for (int i = 0; i < EntitiesConst.MAP.bubbles.size() ; i++) {
				SpeechBubble bubble = EntitiesConst.MAP.bubbles.get(i);
				if (bubble.v == this) {
					EntitiesConst.MAP.bubbles.remove(i);
				}
			}
		}
		this.dialogIndex = 0;
	}
}
