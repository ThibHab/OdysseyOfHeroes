package info3.game.entity;

import java.io.RandomAccessFile;

import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Direction;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;
import info3.game.sound.RandomFileInputStream;

public class Hermit extends Villager {

	public Hermit(Location l) {
		super(l);
		this.name = "Hermit";
		this.dialogs.add("Enchanté jeunes guerriers. je\n suis l'hermite suprême de la forêt");
		this.dialogs.add("Pour combattre le dragon, vous \naurez besoin du pouvoir du feu.");
		this.dialogs.add("Je vous transmet ce pouvoir !");
		this.dialogs.add("Bon courage ! Je retourne méditer.");
		this.scale = EntitiesConst.HERMIT_SCALE;
		// TODO Auto-generated constructor stub
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		Aut_Direction dirs[] = new Aut_Direction[] { Aut_Direction.S, Aut_Direction.E, Aut_Direction.N,
				Aut_Direction.W };
		Action acts[] = new Action[] { Action.S, Action.M };
		this.anim = new Animation(this, ImagesConst.HERMIT, dirs, acts);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getNbActionSprite(Action a) {
		switch (a) {
		case M:
			return AnimConst.HERMIT_M;
		case S:
			return AnimConst.HERMIT_S;
		default:
			return 0;
		}
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
						float progress = (float) this.actionIndex / EntitiesConst.MOUVEMENT_INDEX_MAX_HERMIT;
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
	public void talks() {
		super.talks();
		if(this.dialogIndex == 3) {
			try {
				RandomAccessFile file = new RandomAccessFile("resources/sounds/fireUnlocked.ogg", "r");
				RandomFileInputStream fis = new RandomFileInputStream(file);
				EntitiesConst.GAME.m_canvas.playSound("lvlup",fis, 0, 0.8F);
			} catch (Throwable th) {
				th.printStackTrace(System.err);
				System.exit(-1);
			}
			Range.unlockFire();
		}
		if (this.dialogIndex >= 4) {
			this.dialogs.clear();
		}
	}
	
	@Override
	public boolean isFinished() {
		switch (this.action) {
		case S:
			return this.actionIndex >= EntitiesConst.STAND_INDEX_MAX;
		case M:
			return this.actionIndex >= EntitiesConst.MOUVEMENT_INDEX_MAX_HERMIT;
		default:
			return true;
		}
	}

	@Override
	public int totSrpitePerDir() {
		return AnimConst.HERMIT_TOT;
	}

}
