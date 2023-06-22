package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.automata.*;
import info3.game.constants.Action;
import info3.game.automata.Aut_Category;
import info3.game.constants.EntitiesConst;

public abstract class DecorElement extends Entity {
	public int width, height;

	public DecorElement() {
		super();
		this.category = Aut_Category.O;
	}

	public void paint(Graphics g, int tileSize, float screenPosX, float screenPosY) {
		BufferedImage img=sprites[0];
		int diff=(int) (tileSize*(scale-1))/2;
		g.drawImage(img, (int)screenPosX-diff, (int)screenPosY-diff, (int)(tileSize*scale), (int)(tileSize*scale), null);
	}
	
	@Override
	public void takeDamage(Entity attacker) {
		System.out.println("HEHO CA FAIT MALEUH");
		
	}
}
