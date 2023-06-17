package info3.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.automata.*;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;
import info3.game.map.Tile;

public class Projectile extends Entity {
	public Entity owner;

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
		this.sprites = ImagesConst.ENERGYBALL;
		this.currentState = this.automaton.initial;
		Location l = this.owner.frontTileLocation(d);
		this.location = l;
		Tile destTile = EntitiesConst.MAP_MATRIX[(int) l.getX()][(int) l.getY()];
		if (destTile.entity == null) {
			destTile.entity = this;
		}
		EntitiesConst.GAME.entities.add(this);
		System.out.println("ENERGYBALL LANCEE vers le " + this.direction);
	}

	@Override
	public void Move(Aut_Direction d) {
		super.Move(d);
	}
	
	@Override
	public void Wizz(Aut_Direction d, Aut_Category c) {
		// TODO Auto-generated method stub
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
			public void paint(Graphics g, int TileSize, float screenPosX, float screenPosY) {
				BufferedImage img = this.sprites[0];
				Location pixel=EntitiesConst.GAME.render.gridToPixel(location,true);
				g.drawImage(img, (int) (pixel.getX() - (((scale - 1) / 2 )* TileSize)), (int) (pixel.getY() - (((scale - 1) / 2 )* TileSize)), (int) (TileSize * scale), (int) (TileSize * scale), null);
			}
		
	}
