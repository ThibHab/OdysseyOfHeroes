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
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Projectile extends Entity {
	public Entity owner;
	public int tilesCrossed;

	public Projectile(Entity owner, Aut_Direction d) {
		super();
		this.direction = d;
		this.category = Aut_Category.M;
		this.name = "Projectile";
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				this.automaton = next;
		}
		this.currentState = this.automaton.initial;
		this.owner = owner;
		this.tilesCrossed = 0;
		this.currentState = this.automaton.initial;

		this.action = Action.M;

		this.location.setX((this.owner.location.getX()));
		this.location.setY((this.owner.location.getY()));

		this.hitbox = new Hitbox(this, (float) 0.30, (float) 0.40);
	}

	public Projectile(Entity owner, Aut_Direction d, Location location) {
		super();
		this.direction = d;
		this.category = Aut_Category.M;
		this.name = "Projectile";
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				this.automaton = next;
		}
		this.currentState = this.automaton.initial;
		this.owner = owner;
		this.tilesCrossed = 0;
		this.currentState = this.automaton.initial;

		this.action = Action.M;
		Action acts[] = new Action[] { Action.M };
		this.anim = new Animation(this, ImagesConst.FIREBALL, null, acts);

		this.location.setX(location.getX());
		this.location.setY(location.getY());

		this.hitbox = new Hitbox(this, (float) 0.30, (float) 0.40);
		this.scale = EntitiesConst.ENERGYBALL_SCALE;
		EntitiesConst.MAP.projectiles.add(this);
	}

	@Override
	public void Move(Aut_Direction d) {
		if (!EntitiesConst.GAME.inMenu.isPaused) {
			if (!this.frozen) {
				this.tilesCrossed++;
				this.frozen = true;

				if (d == null) {
					d = this.direction;
				}
				this.anim.changeAction(Action.M);

				this.destLocation = new Location(this.location.getX(), this.location.getY());
				originLocation = new Location(this.location.getX(), this.location.getY());
				relativeMouv = new Location(0, 0);
				switch (this.direction) {
				case N:
					destLocation.setY((this.location.getY() + EntitiesConst.MAP.lenY - 1) % EntitiesConst.MAP.lenY);
					relativeMouv.setY(-1);
					break;
				case S:
					destLocation.setY((this.location.getY() + EntitiesConst.MAP.lenY + 1) % EntitiesConst.MAP.lenY);
					relativeMouv.setY(1);
					break;
				case W:
					destLocation.setX((this.location.getX() + EntitiesConst.MAP.lenX - 1) % EntitiesConst.MAP.lenX);
					relativeMouv.setX(-1);
					break;
				case E:
					destLocation.setX((this.location.getX() + EntitiesConst.MAP.lenX + 1) % EntitiesConst.MAP.lenX);
					relativeMouv.setX(1);
					break;
				default:
					break;
				}

			}
		}
	}

	@Override
	public void paint(Graphics g, int tileSize, float screenPosX, float screenPosY) {
		BufferedImage img = anim.getFrame();
		Location pixel = EntitiesConst.GAME.render.gridToPixel(location, true);
		int dimension = (int) (scale * tileSize);
		float shiftXY = ((scale - 1) / 2) * tileSize;
		int positionX = (int) (pixel.getX() - shiftXY);
		int positionY = (int) (pixel.getY() - shiftXY);
		g.drawImage(img, positionX, positionY, dimension, dimension, null);
		if (EntitiesConst.GAME.debug) {
			g.setColor(Color.red);
			Location l = EntitiesConst.GAME.render.gridToPixel(this.hitbox.location, true);
			g.drawRect((int) l.getX(), (int) l.getY(), (int) (EntitiesConst.GAME.render.tileSize * this.hitbox.width),
					(int) (EntitiesConst.GAME.render.tileSize * this.hitbox.height));
		}
	}

	@Override
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
							float progress = (float) this.actionIndex / EntitiesConst.MOUVEMENT_INDEX_MAX_PROJ;
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
		return this.actionIndex >= EntitiesConst.MOUVEMENT_INDEX_MAX_PROJ;
	}

}
