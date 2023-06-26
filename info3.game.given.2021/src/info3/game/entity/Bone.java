package info3.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import animations.Animation;
import animations.SmokeEffect;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Category;
import info3.game.automata.Aut_Direction;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Bone extends Projectile {

	public Bone(Entity owner, Aut_Direction d) {
		super(owner, d);
		Action acts[] = new Action[] { Action.M };
		this.anim = new Animation(this, ImagesConst.BONE, null, acts);

		this.scale = EntitiesConst.BONE_SCALE;
	}

	public void tick(long elapsed) {
		if (this.owner.weaponRange < this.tilesCrossed) {
			EntitiesConst.MAP.projectiles.remove(this);
			return;
		}
		if (!EntitiesConst.GAME.inMenu.isPaused && !EntitiesConst.GAME.endGameFreeze) {
			this.automaton.step(this, EntitiesConst.GAME);
			this.anim.step(elapsed);
			Entity e = EntitiesConst.MAP_MATRIX[(int) this.destLocation.getX()][(int) this.destLocation.getY()].entity;
			if (e != null && e != this.owner) {
				if (this.hitboxOverlap(e)) {
					new SmokeEffect(this.location);
					e.takeDamage(this.owner);
					System.out.println(this.name + " de " + this.owner.name + " a touché " + e.name);
					EntitiesConst.MAP.projectiles.remove(this);
				}
			}
			if (this.frozen) {
				if (this.frozen) {
					this.actionIndex += elapsed;
					if (action == Action.M) {
						if (this.isFinished()) {
							this.actionIndex = 0;
							this.frozen = false;
							this.location.setX(destLocation.getX());
							this.location.setY(destLocation.getY());
							this.hitbox.update();
						} else if (actionIndex != 0) {
							float progress = (float) this.actionIndex / EntitiesConst.MOUVEMENT_INDEX_MAX_MOB_PROJ;
							this.location.setX((this.originLocation.getX() + EntitiesConst.MAP.lenX
									+ progress * relativeMouv.getX()) % EntitiesConst.MAP.lenX);
							this.location.setY((this.originLocation.getY() + EntitiesConst.MAP.lenY
									+ progress * relativeMouv.getY()) % EntitiesConst.MAP.lenY);
							this.hitbox.update();
						}
					}
				}
			}
		}
	}

	@Override
	public boolean isFinished() {
		return this.actionIndex >= EntitiesConst.MOUVEMENT_INDEX_MAX_MOB_PROJ;
	}

	@Override
	public int getNbActionSprite(Action a) {
		switch (a) {
		case M:
			return AnimConst.BONE_M;
		case H:
			return 0;
		case T:
			return 0;
		case D:
			return 0;
		case S:
			return 0;
		default:
			return 0;
		}
	}

	@Override
	public int totSrpitePerDir() {
		return AnimConst.BONE_M;
	}

}
