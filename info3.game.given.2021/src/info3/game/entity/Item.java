package info3.game.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import info3.game.automata.Aut_Category;
import info3.game.automata.Aut_Direction;

public abstract class Item extends Entity {
	protected Image image;

	public Item() {
		super();
		// TODO give automata and currentState when Items Automata is implemented
		this.automaton = null;
		this.currentState = null;
		this.speed = 0;
	}

	@Override
	public void Egg(Aut_Direction d, Aut_Category c) {
		// TODO Auto-generated method stub
		super.Egg(d, c);
	}

	@Override
	public void Pick(Aut_Direction d) {
		// TODO Auto-generated method stub
		super.Pick(d);
	}
	
	public void paint(Graphics g, int TileSize) {
		BufferedImage img = sprites[imageIndex];
		g.drawImage(img, (int) location.getX(), (int) location.getY(), (int) scale*TileSize, (int) scale*TileSize, null);
	}
}
