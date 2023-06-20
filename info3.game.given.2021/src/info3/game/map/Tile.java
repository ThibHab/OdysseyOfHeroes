package info3.game.map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import info3.game.Sound;
import info3.game.entity.Entity;
import info3.game.entity.Location;
import info3.game.entity.TransparencyBlock;

public abstract class Tile implements ITile {
	public boolean walkable;
	public Entity entity;
	public float opacity;
	public Sound sound;
	public Image image;
	public Location location;
	public TransparencyBlock tpBlock;
	
	
	public Tile(Location location,boolean walkable,float opacity,BufferedImage img) {
		this.walkable=walkable;
		this.location=location;
		this.opacity=opacity;
		this.image=img;
		//TODO Ajouter Sound au constructeur
	}

	@Override
	public void paint(Graphics g,float screenPosX,float screenPosY,int size) {
		g.drawImage(this.image, (int)screenPosX, (int)screenPosY, size, size, null);
		g.setColor(Color.red);
		g.drawString(screenPosX+";"+screenPosY, (int)screenPosX, (int)screenPosY+size/2);
		if(entity==null) {
			if((location.getX()+location.getY())%2==0) {
				g.setColor(new Color(0,255,255,50));
			}else {
				g.setColor(new Color(0,255,0,50));
			}
		}else {
			g.setColor(new Color(255,0,0,50));
		}
		g.fillRect((int)screenPosX, (int)screenPosY, size, size);
	}
}
