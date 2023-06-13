package info3.game.entity;

public class Melee extends Hero {
	public Melee(String name) {
		super();
		// TODO complete null attributes (in super() too)
		this.automaton = null;
		this.sprites = null;
		this.health = 150;
		this.name = name;
		this.weaponDamages = 10;
		this.weaponRange = 1;
	}
}
