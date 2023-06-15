package info3.game.map;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import info3.game.Sound;
import info3.game.entity.Entity;
import info3.game.entity.Location;

public abstract class Tile implements ITile {
	public boolean walkable;
	public Entity entity;
	public float opacity;
	public Sound sound;
	public Image image;
	public Location location;
	
	
	public Tile(Location location,boolean walkable,float opacity,BufferedImage img) {
		this.walkable=walkable;
		this.location=location;
		this.opacity=opacity;
		this.image=img;
		//TODO Ajouter Sound au constructeur
	}

	@Override
	public void paint(Graphics g,int screenPosX,int screenPosY,int size) {
		//TODO convertisseur de position grille to position pixel(rajouter le decalage float a droite et a gauche)
		g.drawImage(this.image, screenPosX*size, screenPosY*size, size, size, null);
		if (entity != null) {
			entity.paint(g, size);
		}
	}
}
