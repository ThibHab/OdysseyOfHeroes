package info3.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import info3.game.Game;
import info3.game.automata.*;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public abstract class Villager extends NPC {

	public LinkedList<String> dialogs;
	public int dialogIndex;

	public Villager(Location l) {
		super();
		this.location = l;
		this.dialogIndex = 0;
		this.dialogs = new LinkedList<>();

		// --- TODO manage automaton ---
		// -----------------------------
		this.category = Aut_Category.T;

		// --- TODO manage sprite properly ---
		this.scale = EntitiesConst.VILLAGER_SCALE;
		// -----------------------------------
		this.hitbox = new Hitbox(this, (float) 0.80, (float) 0.90);
	}

	@Override
	public void tick(long elapsed) {
		if (!EntitiesConst.GAME.inMenu.isPaused) {
			this.automaton.step(this, EntitiesConst.GAME);
			if (this.frozen) {
				this.actionIndex += elapsed;
				if (action == Action.M) {
					if (this.isFinished()) {
						this.actionIndex = 0;
						this.frozen = false;
						this.location.setX(destLocation.getX());
						this.location.setY(destLocation.getY());
						this.hitbox.update();
						EntitiesConst.MAP_MATRIX[(int) this.originLocation.getX()][(int) this.originLocation
								.getY()].entity = null;
					} else if (actionIndex != 0) {
						float progress = (float) this.actionIndex / EntitiesConst.MOUVEMENT_INDEX_MAX_VILLAGER;
						this.location.setX(
								(this.originLocation.getX() + EntitiesConst.MAP.lenX + progress * relativeMouv.getX())
										% EntitiesConst.MAP.lenX);
						this.location.setY(
								(this.originLocation.getY() + EntitiesConst.MAP.lenY + progress * relativeMouv.getY())
										% EntitiesConst.MAP.lenY);
						this.hitbox.update();
					}
				}
			} else {
				if (this.action != Action.S) {
					if (EntitiesConst.GAME.debug) {
						System.out.println(this.name + " is standing");
					}
					this.action = Action.S;
					this.anim.changeAction(action);
				}
				if (!this.dead) {
					this.anim.changeAction(action);
				}
				this.anim.step(elapsed);
			}
		}
	}

	public void talks() {

		if (this.dialogIndex > 0) {
			for (int i = 0; i < EntitiesConst.MAP.bubbles.size(); i++) {
				SpeechBubble bubble = EntitiesConst.MAP.bubbles.get(i);
				if (bubble.v == this) {
					EntitiesConst.MAP.bubbles.remove(i);
				}
			}
		}

		if (this.dialogs.size() <= this.dialogIndex) {
			for (int i = 0; i < EntitiesConst.MAP.bubbles.size(); i++) {
				SpeechBubble bubble = EntitiesConst.MAP.bubbles.get(i);
				if (bubble.v == this) {
					EntitiesConst.MAP.bubbles.remove(i);
				}
			}
			this.dialogIndex = 0;
		} else {
			EntitiesConst.MAP.bubbles.add(new SpeechBubble(this, this.dialogs.get(dialogIndex++)));
		}
	}

	@Override
	public int getNbActionSprite(Action a) {
		switch (a) {
		case M:
			return AnimConst.VILLAGERGIRL_M;
		case S:
			return AnimConst.VILLAGERGIRL_S;
		default:
			return 0;
		}
	}

	@Override
	public boolean isFinished() {
		switch (this.action) {
		case S:
			return this.actionIndex >= EntitiesConst.STAND_INDEX_MAX;
		case M:
			return this.actionIndex >= EntitiesConst.MOUVEMENT_INDEX_MAX_VILLAGER;
		default:
			return true;
		}
	}

}
