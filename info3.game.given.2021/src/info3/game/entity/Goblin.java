package info3.game.entity;

import info3.game.automata.Category;
import info3.game.automata.Direction;
import info3.game.constants.ImagesConst;

public class Goblin extends Mob {
	public static int GoblinHealth = 20, GoblinDamage = 2, GoblinRange = 2, GoblinSpeed = 2;

	public Goblin(Location l) {
		super();
		this.name = "Goblin";
		this.location = l;
		this.health = Goblin.GoblinHealth;
		this.weaponDamage = Goblin.GoblinDamage;
		this.weaponRange = Goblin.GoblinRange;
		this.speed = Goblin.GoblinSpeed;

		// --- TODO manage automaton ---
		this.automaton = null;
		this.currentState = null;
		// -----------------------------
		this.category = Category.A;

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.goblin;
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
