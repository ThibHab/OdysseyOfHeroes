package animations;

import java.awt.image.BufferedImage;

import info3.game.automata.Aut_Direction;
import info3.game.constants.Action;
import info3.game.constants.EntitiesConst;
import info3.game.entity.Entity;

public class Animation {
	public Entity owner;
	public BufferedImage sprites[];
	public int imageIndex;
	public Aut_Direction[] dirOrder;
	public Action[] actOrder;

	public Animation(Entity e, BufferedImage[] spr, Aut_Direction[] dirs, Action[] acts) {
		this.owner = e;
		this.sprites = spr;
		this.imageIndex = 0;
		if (dirs != null) {
			dirOrder = dirs;
		}
		actOrder = acts;
	}

	public void step(long elapsed) {
		if (this.nextAnim(elapsed)) {
			updateIndex();
		}
	}

	public boolean nextAnim(long elapsed) {
		int divide, nb_sprite;
		nb_sprite = owner.getNbActionSprite(this.owner.action);
		if (nb_sprite > 0)
			divide = EntitiesConst.getActionIndexMax(this.owner.action) / nb_sprite;
		else
			divide = 1;
		return (owner.actionIndex - elapsed) / divide < owner.actionIndex / divide;
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
			if (this.owner.action == actOrder[i]) {
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

	public void printAction() {
		switch (owner.action) {
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

	public BufferedImage getFrame() {
		return sprites[imageIndex];
	}

	public void changeAction(Action a) {
		if (this.owner.action != Action.S)
			this.imageIndex = sprites.length - 1;
		updateIndex();
	}
}
