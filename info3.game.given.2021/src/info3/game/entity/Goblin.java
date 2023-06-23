package info3.game.entity;

import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Category;
import info3.game.automata.Aut_Direction;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Goblin extends Mob {
	public Goblin(Location l) {
		super();
		this.name = "Goblin";
		this.location = l;
		this.health = EntitiesConst.GOBLIN_HEALTH;
		this.weaponDamage = EntitiesConst.GOBLIN_DAMAGE;
		this.weaponRange = EntitiesConst.GOBLINE_RANGE;
		this.speed = EntitiesConst.GOBLIN_SPEED;
		this.scale = EntitiesConst.GOBLIN_SCALE;
		this.detectionRadius = 5;

		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;

		Aut_Direction dirs[] = new Aut_Direction[] { Aut_Direction.S, Aut_Direction.W, Aut_Direction.N,
				Aut_Direction.E };
		Action acts[] = new Action[] { Action.S, Action.M, Action.H, Action.T, Action.D };
		this.anim = new Animation(this,ImagesConst.GOBLIN, dirs, acts);

		this.category = Aut_Category.A;
	}
	
	@Override
	public void tick(long elapsed) {
		if (!this.dead) {
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
					if (this.actionIndex >= this.attackSpeed) {
						this.hitFrozen = false;
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
						if (this.health == 0)
							this.die();
					}
				} else if (action == Action.D) {
					if (this.isFinished()) {
						this.frozen = false;
						this.actionIndex = 0;
						this.dead = true;
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
	public void Hit(Aut_Direction d) {
		// TODO Auto-generated method stub
		super.Hit(d);
	}

	@Override
	public void Pop(Aut_Direction d, Aut_Category c) {
		// TODO Auto-generated method stub
		super.Pop(d, c);
	}

	@Override
	public void Wizz(Aut_Direction d, Aut_Category c) {
		// TODO Auto-generated method stub
		super.Wizz(d, c);
	}

	@Override
	public void Power() {
		// TODO Auto-generated method stub
		super.Power();
	}

	@Override
	public void Throw(Aut_Direction d, Aut_Category category) {
		// TODO Auto-generated method stub
		super.Throw(d, category);
	}
	
	@Override
	public boolean isFinished() {
		switch (this.action) {
		case S:
			return this.actionIndex >= EntitiesConst.STAND_INDEX_MAX;
		case M:
			return this.actionIndex >= EntitiesConst.MOUVEMENT_INDEX_MAX_MOB;
		case H:
			return this.actionIndex >= EntitiesConst.HIT_INDEX_MAX;
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
	public int getNbActionSprite(Action a) {
		//TODO
		switch (a) {
		case M:
			return AnimConst.GOBLIN_M;
		case H:
			return AnimConst.GOBLIN_H;
		case T:
			return AnimConst.GOBLIN_T;
		case D:
			return AnimConst.GOBLIN_D;
		case S:
			return AnimConst.GOBLIN_S;
		default:
			return 0;
		}
	}

	@Override
	public int totSrpitePerDir() {
		return AnimConst.GOBLIN_TOT;
	}
}
