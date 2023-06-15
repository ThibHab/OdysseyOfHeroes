package info3.game.entity;

import info3.game.automata.Category;
import info3.game.automata.Direction;
import info3.game.constants.ImagesConst;

public class Boss extends Mob {
	public static int BOSS_HEALTH = 20, BOSS_WIDTH = 2, BOSS_HEIGHT = 2, BOSS_DAMAGE = 4, BOSS_RANGE = 2, BOSS_SPEED = 2;

	public Boss(Location l) {
		super();
		this.name = "Boss";
		this.location = l;
		this.width = Boss.BOSS_WIDTH;
		this.height = Boss.BOSS_HEIGHT;
		this.health = Boss.BOSS_HEALTH;
		this.weaponDamage = Boss.BOSS_DAMAGE;
		this.weaponRange = Boss.BOSS_RANGE;
		this.speed = Boss.BOSS_SPEED;

		// --- TODO manage automaton ---
		this.automaton = null;
		this.currentState = null;
		// -----------------------------
		this.category = Category.A;

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.BOSS;
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
