package info3.game.map;

import java.awt.Graphics;
import java.awt.Image;

import info3.game.Sound;
import info3.game.entity.Entity;

public abstract class Tile implements ITile {
	public boolean walkable;
	public Entity entity;
	public float opacity;
	public Sound sound;
	public Image image;
	
	public Tile() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub

	}
}
