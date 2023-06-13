package info3.game.entity;

public class Range extends Hero {
	public Range(String name) {
		super();
		// TODO complete null attributes (in super() too)
		this.automaton = null;
		this.sprites = null;
		this.health = 100;
		this.name = name;
		this.weaponDamages = 15;
		this.weaponRange = 3;
	}
}
