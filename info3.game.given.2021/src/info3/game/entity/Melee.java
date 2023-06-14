package info3.game.entity;

import info3.game.Game;

public class Melee extends Hero {
	public Melee(String name, Game game) {
		super();
		// TODO complete null attributes (in super() too)
		this.game = game;
		this.automaton = null;
		this.sprites = null;
		this.health = 150;
		this.name = name;
		this.weaponDamages = 10;
		this.weaponRange = 1;
	}
}
