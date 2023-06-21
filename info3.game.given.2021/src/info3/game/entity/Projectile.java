package info3.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.automata.*;
import info3.game.constants.Action;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;
import info3.game.map.Tile;

public class Projectile extends Entity {
	public Entity owner;
	public int tilesCrossed;

	public Projectile(Entity owner, Aut_Direction d) {
		super();
		this.direction = d;
		this.category = Aut_Category.M;
		this.name = "EnergyBall";
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				this.automaton = next;
		}
		this.owner = owner;
		this.tilesCrossed = 0;
		this.sprites = ImagesConst.ENERGYBALL;
		this.currentState = this.automaton.initial;
		this.location.setX((float)(this.owner.location.getX()));
		this.location.setY((float)(this.owner.location.getY()));
		
		this.speed = 2;
		this.hitbox = new Hitbox(this, (float)0.30, (float)0.40);
		this.scale = EntitiesConst.ENERGYBALL_SCALE;
		EntitiesConst.MAP.projectiles.add(this);
	}

	@Override
	public void Move(Aut_Direction d) {
		if (!this.frozen) {
			this.tilesCrossed++;
			this.frozen = true;
			this.moving = true;

			if (d == null) {
				d = this.direction;
			}
			if (this.action != Action.M) {
				this.imageIndex = 0;
				this.action = Action.M;
			}

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

		} else {
			this.mouvementIndex = 0;
		}

	}
	
	@Override
	public void Wizz(Aut_Direction d, Aut_Category c) {
		// TODO Auto-generated method stub
		System.out.println("WIZZZZZ");
		EntitiesConst.MAP_MATRIX[(int) this.location.getX()][(int) this.location.getY()].entity = null;
//		super.Wizz(d,c);
//		Location location = this.frontTileLocation(d);
//		Entity target = EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity;
//		if (this.owner.category == Aut_Category.A) {
//			if (target.category == Aut_Category.AT) {
//				if (this.direction == null) {
//					this.direction = d;
//				}
//				if (this.hitboxOverlap(target)) {
//					target.health -= this.owner.weaponDamage;
//				}
//			}
//			// TODO destroy projectile
//		} else if (this.owner.category == Aut_Category.AT) {
//			if (target.category == Aut_Category.A) {
//				if (this.direction == null) {
//					this.direction = d;
//				}
//				if (this.hitboxOverlap(target)) {
//					target.health -= this.owner.weaponDamage;
//				}
//			}
//		}
	}
	// TODO destroy projectile
	
	@Override
	public void paint(Graphics g, int tileSize, float screenPosX, float screenPosY) {
		BufferedImage img = this.sprites[this.imageIndex];
		Location pixel=EntitiesConst.GAME.render.gridToPixel(location,true);
		int dimension = (int) (scale * tileSize);
		float shiftXY = ((scale - 1) / 2) * tileSize;
		int positionX = (int) (pixel.getX() - shiftXY);
		int positionY = (int) (pixel.getY() - shiftXY);
		g.drawImage(img, positionX, positionY, dimension, dimension, null);g.setColor(Color.red);
		Location l = EntitiesConst.GAME.render.gridToPixel(this.hitbox.location, true);
		g.drawRect((int) l.getX(), (int) l.getY(), (int) (EntitiesConst.GAME.render.tileSize * this.hitbox.width),
				(int) (EntitiesConst.GAME.render.tileSize * this.hitbox.height));
	}
	
	public void tick(long elapsed) {
		if (this.owner.range < this.tilesCrossed) {
			EntitiesConst.MAP.projectiles.remove(this);
			System.out.println("dead proj");
			return;
		}
		this.automaton.step(this, EntitiesConst.GAME);
		Entity e = EntitiesConst.MAP_MATRIX[(int)this.destLocation.getX()][(int)this.destLocation.getY()].entity;
		if (e != null && e != this.owner) {
			if (this.hitboxOverlap(e)) {
				e.takeDamage(this.owner);
				System.out.println(this.name + " de " + this.owner.name + " a touché " + e.name);
				EntitiesConst.MAP.projectiles.remove(this);
			}
		}
		if (this.frozen && this.moving) {
			this.mouvementIndex += elapsed;
			if (this.mouvementIndex >= EntitiesConst.MOUVEMENT_INDEX_MAX_PROJ) {
				this.frozen = false;
				this.moving = false;
				this.mouvementIndex = 0;
				this.imageIndex = (this.imageIndex + 1) % this.sprites.length;
				this.location.setX(destLocation.getX());
				this.location.setY(destLocation.getY());
				this.hitbox.update();
			} else {
				if (mouvementIndex != 0) {
					this.imageIndex = (this.imageIndex + 1) % this.sprites.length;
					float progress = (float) this.mouvementIndex / EntitiesConst.MOUVEMENT_INDEX_MAX_PROJ;
					this.location
							.setX((this.originLocation.getX() + EntitiesConst.MAP.lenX + progress * relativeMouv.getX())
									% EntitiesConst.MAP.lenX);
					this.location
							.setY((this.originLocation.getY() + EntitiesConst.MAP.lenY + progress * relativeMouv.getY())
									% EntitiesConst.MAP.lenY);
					this.hitbox.update();
				}
			}
		}
	}
		
	}
