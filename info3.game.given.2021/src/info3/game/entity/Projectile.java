package info3.game.entity;

import info3.game.automata.*;
import info3.game.constants.EntitiesConst;

public class Projectile extends Entity {
	public Entity owner;

	public Projectile(Entity owner) {
		super();
		this.owner = owner;
	}

	@Override
	public void Wizz(Aut_Direction d, Aut_Category c) {
		// TODO Auto-generated method stub
		super.Wizz(d, c);
		if (this.owner.category == Aut_Category.A) {
			if (c == Aut_Category.AT) {
				if (this.direction == null) {
					this.direction = d;
				}
				
				Location location = this.frontTileLocation(d);
				Entity target = EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity;
				if (this.hitboxOverlap(target)) {
					target.health -= this.owner.weaponDamage;
				}
			}
			// TODO destroy projectile
		} else if (this.owner.category == Aut_Category.AT) {
			if (c == Aut_Category.A) {
				if (this.direction == null) {
					this.direction = d;
				}
				
				Location location = this.frontTileLocation(d);
				Entity target = EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity;
				if (this.hitboxOverlap(target)) {
					target.health -= this.owner.weaponDamage;
				}
			}
			// TODO destroy projectile
		}
	}
}
