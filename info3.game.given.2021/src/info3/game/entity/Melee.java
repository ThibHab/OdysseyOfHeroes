package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import info3.game.automata.Category;
import info3.game.automata.Direction;
import info3.game.constants.ImagesConst;

public class Melee extends Hero {
	public static int MeleeHealth = 20, MeleeDamage = 2, MeleeRange = 2;

	public Melee(String name, Location l) {
		super();
		this.name = name;
		this.location = l;
		this.weaponDamage = Melee.MeleeDamage;
		this.weaponRange = Melee.MeleeRange;
		this.health = Melee.MeleeHealth;

		// --- TODO manage automaton ---
		this.automaton = null;
		this.currentState = null;
		// -----------------------------

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.MELEE;
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

	public void paint(Graphics g, int TileSize) {
		BufferedImage img = sprites[imageIndex];
		g.drawImage(img, (int) location.getX(), (int) location.getY(), (int) scale * TileSize, (int) scale * TileSize,
				null);
	}
}
