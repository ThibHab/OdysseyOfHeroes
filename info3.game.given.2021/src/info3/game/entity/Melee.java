package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

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
	
	public void paint(Graphics g, int TileSize) {
		BufferedImage img = sprites[imageIndex];
		g.drawImage(img, (int) location.getX(), (int) location.getY(), (int) scale*TileSize, (int) scale*TileSize, null);
	}
}
