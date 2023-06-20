package info3.game.entity;

import info3.game.map.Tile;

public abstract class TransparentDecorElement extends DecorElement {
	
	public boolean transparent;
	public Tile[] liste;
	public int lenListe;
	public Location tilePainter;
	
	public TransparentDecorElement(int len,Location loc) {
		this.transparent=false;
		this.liste=new Tile[len];
		this.lenListe=len;
		this.tilePainter=loc;
	}
	
	public void checkTransparent() {
		this.transparent=false;
		for(int i=0;i<lenListe;i++) {
			if(liste[i].entity != null && liste[i].entity instanceof Hero) {
				this.transparent=true;
			}
		}
	}
	

}
