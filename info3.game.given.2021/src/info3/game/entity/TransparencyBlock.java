package info3.game.entity;

import java.awt.Color;
import java.awt.Graphics;

public class TransparencyBlock extends DecorElement {
	
	public TransparentDecorElement target;
	
	public TransparencyBlock(int x,int y,TransparentDecorElement target){
		super();
		this.location=new Location(x,y);
		this.target=target;
	}
	
	public void paint(Graphics g, int tileSize, float screenPosX ,float screenPosY) {
		int res=99;
		for(int i=0;i<target.lenListe;i++) {
			if(this==target.liste[i].tpBlock) {
				res=i;
			}
		}
		g.setColor(new Color(255, 175, 175,150));
		g.fillRect((int)screenPosX, (int)screenPosY, tileSize, tileSize);
		g.setColor(Color.black);
		g.drawString(""+res,(int)screenPosX, (int)screenPosY+tileSize/2);
	}
	
}