package info3.game.entity;

import info3.game.automata.Category;
import info3.game.automata.Direction;
import info3.game.constants.ImagesConst;

public class Skeleton extends Mob {
	public static int SkeletonHealth = 20, SkeletonDamage = 2, SkeletonRange = 2, SkeletonSpeed = 2;

	public Skeleton(Location l) {
		super();
		this.name = "Skeleton";
		this.location = l;
		this.health = Skeleton.SkeletonHealth;
		this.weaponDamage = Skeleton.SkeletonDamage;
		this.weaponRange = Skeleton.SkeletonRange;
		this.speed = Skeleton.SkeletonSpeed;

		// --- TODO manage automaton ---
		this.automaton = null;
		this.currentState = null;
		// -----------------------------
		this.category = Category.A;

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.skeleton;
		this.imageIndex = 0;
		// -----------------------------------
	}

	@Override
	public void Hit(Direction d) {
		// TODO Auto-generated method stub
		super.Hit(d);
	}

	@Override
	public void Pop(Direction d, Category c) {
		// TODO Auto-generated method stub
		super.Pop(d, c);
	}

	@Override
	public void Wizz(Direction d, Category c) {
		// TODO Auto-generated method stub
		super.Wizz(d, c);
	}

	@Override
	public void Power() {
		// TODO Auto-generated method stub
		super.Power();
	}

	@Override
	public void Throw(Direction d, Category category) {
		// TODO Auto-generated method stub
		super.Throw(d, category);
	}
}
