package info3.game.entity;

public abstract class Hero extends Creature {
	public static int level;
	public static int experience;

	public int coins;
	public int weaponDamages;
	public int weaponRange;

	public Hero() {
		super();
		this.width = 100;
		this.height = 100;
		this.coins = 0;
		Hero.level = 1;
		Hero.experience = 0;
		this.speed = 1;
	}
	
}
