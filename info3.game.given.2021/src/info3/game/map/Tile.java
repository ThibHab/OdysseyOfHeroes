package info3.game.map;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import info3.game.Sound;
import info3.game.constants.EntitiesConst;
import info3.game.entity.Entity;
import info3.game.entity.Location;

public abstract class Tile implements ITile {
	public boolean walkable;
	public Entity entity;
	public float opacity;
	public Sound sound;
	public Image image;
	public Location location;

	public Tile(Location location, boolean walkable, float opacity, BufferedImage img) {
		this.walkable = walkable;
		this.location = location;
		this.opacity = opacity;
		this.image = img;
		// TODO Ajouter Sound au constructeur
	}

	@Override
	public void paint(Graphics g, float screenPosX, float screenPosY, int size) {
		g.drawImage(this.image, (int) screenPosX, (int) screenPosY, size, size, null);
		if (EntitiesConst.GAME.debug) {
			g.setColor(Color.red);
			g.drawString(screenPosX + ";" + screenPosY, (int) screenPosX, (int) screenPosY + size / 2);
		}
	}
}
