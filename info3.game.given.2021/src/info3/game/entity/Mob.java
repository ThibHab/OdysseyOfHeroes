package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.RandomAccessFile;

import info3.game.constants.EntitiesConst;
import info3.game.sound.RandomFileInputStream;

public abstract class Mob extends Entity {
	public Mob() {
		super();
	}
	
	public void paint(Graphics g, int tileSize, float screenPosX, float screenPosY) {
		BufferedImage img = anim.getFrame();
		Location pixel = EntitiesConst.GAME.render.gridToPixel(location, true);
		int dimension = (int) (scale * tileSize);
		float shiftXY = ((scale - 1) / 2) * tileSize;
		int positionX = (int) (pixel.getX() - shiftXY);
		int positionY = (int) (pixel.getY() - shiftXY);
		g.drawImage(img, positionX, positionY, dimension, dimension, null);
	}
	
	@Override
	public void takeDamage(Entity attacker) {
		super.takeDamage(attacker);
		try {
			RandomAccessFile file = new RandomAccessFile("resources/damage.ogg", "r");
			RandomFileInputStream fis = new RandomFileInputStream(file);
			EntitiesConst.GAME.m_canvas.playSound("damage",fis, 0, 1.0F);
		} catch (Throwable th) {
			th.printStackTrace(System.err);
			System.exit(-1);
		}
	}
}
