package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Hero extends Entity {
	public Hero() {
		super();
		this.width = 100;
		this.height = 100;
		this.coins = 0;
		Hero.level = 1;
		Hero.experience = 0;
		this.speed = 1;
	}
	
	public void paint(Graphics g, int TileSize) {
		BufferedImage img = sprites[imageIndex];
		g.drawImage(img, (int) location.getX(), (int) location.getY(), (int) scale*TileSize, (int) scale*TileSize, null);
	}
}
