package animations;

import java.awt.image.BufferedImage;

import info3.game.automata.Aut_Direction;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.entity.Entity;
import info3.game.entity.Melee;

public class Animation {
	public Entity owner;
	public BufferedImage sprites[];
	public Action action;
	public long actionIndex;
	public int imageIndex;
	public Aut_Direction[] dirOrder;
	public Action[] actOrder;

	public Animation(Entity e, BufferedImage[] spr, Aut_Direction[] dirs, Action[] acts) {
		this.owner = e;
		this.sprites = spr;
		this.action = Action.S;
		this.actionIndex = 0;
		this.imageIndex = 0;
		if (dirs != null) {
			dirOrder = dirs;
		}
		actOrder = acts;
	}

	public BufferedImage getFrame() {
		return sprites[imageIndex];
	}

	public void printAction() {
		switch (action) {
		case M:
			System.out.println(owner.name + " is moving.");
			break;
		case H:
			System.out.println(owner.name + " is hitting.");
			break;
		case D:
			System.out.println(owner.name + " is dying.");
			break;
		case T:
			System.out.println(owner.name + " is touched.");
			break;
		case S:
			System.out.println(owner.name + " is standing.");
			break;
		}
	}

	public void changeAction(Action a) {
		if (owner instanceof Melee) {
			int b = 1 + 1;
		}
		if (a != this.action) {
			if (a != Action.S) {
				owner.frozen = true;
			}
			System.out.println(a.toString());
			this.action = a;
			actionIndex = 0;
			this.imageIndex = sprites.length;
			updateIndex();
			if (EntitiesConst.GAME.debug) {
				this.printAction();
			}
		}
	}

	public void step(long elapsed) {
		this.actionIndex += elapsed;
		if (this.nextAnim(elapsed)) {
			updateIndex();
		}
	}

	public boolean isFinished() {
		switch (this.action) {
		case S:
			return this.actionIndex >= EntitiesConst.STAND_INDEX_MAX;
		case M:
			return this.actionIndex >= EntitiesConst.MOUVEMENT_INDEX_MAX;
		case H:
			return this.actionIndex >= EntitiesConst.HIT_INDEX_MAX;
		case D:
			return false;
		case T:
			return this.actionIndex >= EntitiesConst.TOUCHED_INDEX_MAX;
		default:
			return true;
		}
	}

	public boolean nextAnim(long elapsed) {
		int divide, nb_sprite;
		nb_sprite = owner.getNbActionSprite(this.action);
		if (nb_sprite > 0)
			divide = EntitiesConst.getActionIndexMax(this.action) / nb_sprite;
		else
			divide = 1;
		return (actionIndex - elapsed) / divide < actionIndex / divide;
	}

	public void updateIndex() {
		int idx = 0;
		if (dirOrder != null) {
			if (this.owner.direction == dirOrder[1]) {
				idx += (this.owner.totSrpitePerDir());
			} else if (this.owner.direction == dirOrder[2]) {
				idx += (2 * this.owner.totSrpitePerDir());
			} else if (this.owner.direction == dirOrder[3]) {
				idx += (3 * this.owner.totSrpitePerDir());
			}
		}
		for (int i = 0; i < this.actOrder.length; i++) {
			if (this.action == actOrder[i]) {
				if (this.imageIndex + 1 < idx + owner.getNbActionSprite(actOrder[i])) {
					this.imageIndex = this.imageIndex + 1;
				} else {
						this.imageIndex = idx;
				}
			} else {
				idx += owner.getNbActionSprite(actOrder[i]);
			}
		}
	}
}
