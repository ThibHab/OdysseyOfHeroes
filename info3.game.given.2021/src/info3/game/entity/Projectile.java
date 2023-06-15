package info3.game.entity;

import info3.game.automata.Category;
import info3.game.automata.Direction;
import info3.game.constants.EntitiesConst;

public class Projectile extends Entity {
	public Entity owner;

	public Projectile(Entity owner) {
		super();
		this.owner = owner;
	}

	@Override
	public void Wizz(Direction d, Category c) {
		// TODO Auto-generated method stub
		super.Wizz(d, c);
		if (this.owner.category == Category.A) {
			if (c == Category.AT) {
				if (this.direction == null) {
					this.direction = d;
				}
				
				Location location = this.frontTileLocation(d);
				Entity target = EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity;
				// TODO fonction calcul hitbox overlap
				// if (overlapped) : target.health -= this.owner.weaponDamage
				EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity.health -= this.owner.weaponDamage;
			}
			// TODO destroy projectile
		} else if (this.owner.category == Category.AT) {
			if (c == Category.A) {
				if (this.direction == null) {
					this.direction = d;
				}
				
				Location location = this.frontTileLocation(d);
				Entity target = EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity;
				// TODO fonction calcul hitbox overlap
				// if (overlapped) : target.health -= this.owner.weaponDamage
				EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity.health -= this.owner.weaponDamage;
			}
			// TODO destroy projectile
		}
	}
}
