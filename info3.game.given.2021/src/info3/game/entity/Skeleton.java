package info3.game.entity;

import info3.game.automata.*;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Skeleton extends Mob {
	public Skeleton(Location l) {
		super();
		this.name = "Skeleton";
		this.location = l;
		this.health = EntitiesConst.SKELETON_HEALTH;
		this.weaponDamage = EntitiesConst.SKELETON_DAMAGE;
		this.weaponRange = EntitiesConst.SKELETON_RANGE;
		this.speed = EntitiesConst.SKELETON_SPEED;
		this.scale = EntitiesConst.SKELETON_SCALE;

		// --- TODO manage automaton ---
		this.automaton = null;
		this.currentState = null;
		// -----------------------------
		this.category = Aut_Category.A;

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.SKELETON;
		this.imageIndex = 0;
		// -----------------------------------
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
}
