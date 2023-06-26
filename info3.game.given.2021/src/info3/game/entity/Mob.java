package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.RandomAccessFile;

import info3.game.constants.Action;
import info3.game.constants.EntitiesConst;
import info3.game.sound.RandomFileInputStream;

public abstract class Mob extends Entity {
	public Mob() {
		super();
		this.attackSpeed = 1000;
		this.hitbox = new Hitbox(this, (float) 0.50, (float) 0.65);
	}
	
	
	@Override
	public void tick(long elapsed) {
		if (!EntitiesConst.GAME.inMenu.isPaused) {
			if (currentState.name.equals("")) {
				this.die();
				this.dead = true;
				EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity = null;
			}
			this.automaton.step(this, EntitiesConst.GAME);
			if (this.hitFrozen) {
				this.hitIndex += elapsed;
				if (this.hitIndex > this.attackSpeed) {
					this.hitFrozen = false;
					this.hitIndex = 0;
				}
			}

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
						float progress = (float) this.actionIndex / EntitiesConst.MOUVEMENT_INDEX_MAX_MOB;
						this.location.setX(
								(this.originLocation.getX() + EntitiesConst.MAP.lenX + progress * relativeMouv.getX())
										% EntitiesConst.MAP.lenX);
						this.location.setY(
								(this.originLocation.getY() + EntitiesConst.MAP.lenY + progress * relativeMouv.getY())
										% EntitiesConst.MAP.lenY);
						this.hitbox.update();
					}
				} else if (action == Action.H) {
					if (this.isFinished()) {
						this.frozen = false;
						this.actionIndex = 0;
					}
				} else if (action == Action.I) {
					if (this.isFinished()) {
						this.frozen = false;
						this.actionIndex = 0;
					}
				} else if (action == Action.T) {
					if (this.isFinished()) {
						this.frozen = false;
						this.actionIndex = 0;
					}
				}else  if (timer != Integer.MIN_VALUE) {
					this.timer -= elapsed;
					if (timer < 0) {
						this.frozen = false;
						timer = Integer.MIN_VALUE;
						waited();
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
				if (!this.dead)
					this.anim.changeAction(action);
			}
			this.anim.step(elapsed);
		}
	}
	
	@Override
	public boolean isFinished() {
		switch (this.action) {
		case S:
			return this.actionIndex >= EntitiesConst.STAND_INDEX_MAX;
		case M:
			return this.actionIndex >= EntitiesConst.MOUVEMENT_INDEX_MAX_MOB;
		case H:
			return this.actionIndex >= EntitiesConst.HIT_INDEX_MAX_MOB;
		case D:
			return this.actionIndex >= EntitiesConst.DIE_INDEX_MAX;
		case T:
			return this.actionIndex >= EntitiesConst.TOUCHED_INDEX_MAX;
		case I:
			return this.actionIndex >= EntitiesConst.INTERACT_INDEX_MAX;
		default:
			return true;
		}
	}
	
	@Override
	public void die() {
		if (this.action != Action.D) {
			this.action = Action.D;

			Hero.addExperience(this);

			if (EntitiesConst.GAME.debug) {
				System.out.println(this.name + " has died");
			}
		}
		
	}
	
	
	public void paint(Graphics g, int tileSize, float screenPosX, float screenPosY) {
		BufferedImage img = anim.getFrame();
		Location pixel = EntitiesConst.GAME.render.gridToPixel(location, true);
		int dimension = (int) (scale * tileSize);
		float shiftXY = ((scale - 1) / 2) * tileSize;
		int positionX = (int) (pixel.getX() - shiftXY);
		int positionY = (int) (pixel.getY() - shiftXY);
		g.drawImage(img, positionX, positionY, dimension, dimension, null);
	}
	
	@Override
	public void takeDamage(Entity attacker) {
		super.takeDamage(attacker);
//		try {
//			RandomAccessFile file = new RandomAccessFile("resources/damage.ogg", "r");
//			RandomFileInputStream fis = new RandomFileInputStream(file);
//			EntitiesConst.GAME.m_canvas.playSound("damage",fis, 0, 0.7F);
//		} catch (Throwable th) {
//			th.printStackTrace(System.err);
//			System.exit(-1);
//		}
	}
}
