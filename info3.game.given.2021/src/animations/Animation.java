package animations;

import java.awt.image.BufferedImage;

import info3.game.automata.Aut_Direction;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.entity.Entity;

public class Animation {
	public Entity owner;
	public BufferedImage sprites[];
	public Action action;
	public long mouvementIndex;
	public int imageIndex;
	public Aut_Direction direction;
	public Aut_Direction[] dirOrder;
	public Action[] actOrder;

	public Animation(Entity e, BufferedImage[] spr, Aut_Direction[] dirs, Action[] acts) {
		this.owner = e;
		this.sprites = spr;
		this.action = Action.S;
		this.mouvementIndex = 0;
		this.imageIndex = 0;
		this.direction = Aut_Direction.N;
		if (dirs != null) {
			dirOrder = dirs;
		}
		actOrder = acts;
	}

	public BufferedImage get_frame() {
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
		if (a != this.action) {
			this.action = a;
			if (a != Action.S) {
				this.imageIndex = this.sprites.length -1;
				owner.frozen = true;
				updateIndex();
			}
			if (EntitiesConst.GAME.debug) {
				this.printAction();
			}
		}
	}

	public void step(long elapsed) {
		this.mouvementIndex += elapsed;
		if (nextAnim(elapsed)) {
			updateIndex();
		}
	}

	public boolean isFinished() {
		switch (this.action) {
		case M:
			return this.mouvementIndex >= EntitiesConst.MOUVEMENT_INDEX_MAX;
		case H:
			return this.mouvementIndex >= EntitiesConst.HIT_INDEX_MAX;
		case D:
			return this.mouvementIndex >= EntitiesConst.DIE_INDEX_MAX;
		case T:
			return this.mouvementIndex >= EntitiesConst.TOUCHED_INDEX_MAX;
		case S:
			return this.mouvementIndex >= EntitiesConst.STAND_INDEX_MAX;
		default:
			return true;
		}
	}

	public void resetAnim() {
		mouvementIndex = 0;
		owner.frozen = false;
	}

	public boolean nextAnim(long elapsed) {
		int divide, nb_sprite;
		switch (this.action) {
		case S:
			nb_sprite = owner.getNbActionSprite(Action.S);
			if (nb_sprite > 0)
				divide = EntitiesConst.MOUVEMENT_INDEX_MAX / nb_sprite;
			else
				divide = 1;
			break;
		case M:
			nb_sprite = owner.getNbActionSprite(Action.M);
			if (nb_sprite > 0)
				divide = EntitiesConst.MOUVEMENT_INDEX_MAX / nb_sprite;
			else
				divide = 1;
			break;
		case D:
			nb_sprite = owner.getNbActionSprite(Action.D);
			if (nb_sprite > 0)
				divide = EntitiesConst.MOUVEMENT_INDEX_MAX / nb_sprite;
			else
				divide = 1;
			break;
		case T:
			nb_sprite = owner.getNbActionSprite(Action.T);
			if (nb_sprite > 0)
				divide = EntitiesConst.MOUVEMENT_INDEX_MAX / nb_sprite;
			else
				divide = 1;
			break;
		case H:
			nb_sprite = owner.getNbActionSprite(Action.H);
			if (nb_sprite > 0)
				divide = EntitiesConst.MOUVEMENT_INDEX_MAX / nb_sprite;
			else
				divide = 1;
			break;
		default:
			return true;
		}
		return (mouvementIndex - elapsed) / divide < mouvementIndex / divide;
	}

	public void updateIndex() {
		int idx = 0;
		if (dirOrder != null) {
			if (this.direction.compareTo(dirOrder[1]) == 0) {
				idx += (1 * this.owner.totSrpitePerDir());
			} else if (this.direction.compareTo(dirOrder[2]) == 0) {
				idx += (2 * this.owner.totSrpitePerDir());
			} else if (this.direction.compareTo(dirOrder[3]) == 0) {
				idx += (3 * this.owner.totSrpitePerDir());
			}
		}
		for (int i = 0; i < this.actOrder.length; i++) {
			if (this.action.compareTo(actOrder[i]) == 0) {
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
