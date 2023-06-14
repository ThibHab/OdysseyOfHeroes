package info3.game.entity;

import info3.game.Game;

public class Range extends Hero {
	public Range(String name, Game game) {
		super();
		// TODO complete null attributes (in super() too)
		this.game = game;
		this.automaton = null;
		this.sprites = null;
		this.health = 100;
		this.name = name;
		this.weaponDamages = 15;
		this.weaponRange = 3;
	}
}
