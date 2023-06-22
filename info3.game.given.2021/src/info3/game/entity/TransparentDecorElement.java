package info3.game.entity;

import info3.game.map.Tile;

public abstract class TransparentDecorElement extends DecorElement {
	
	public boolean transparent;
	public Tile[] liste;
	public int lenListe;
	int opacityDiv;
	
	public TransparentDecorElement(int len) {
		this.transparent=false;
		this.liste=new Tile[len];
		this.lenListe=len;
	}
	
	boolean classEnableTranspa(Entity e) {
		return (e instanceof Hero)||(e instanceof NPC)||(e instanceof Bomb)||(e instanceof Mob);
				}
	
	public void checkTransparent() {
		this.transparent=false;
		int res=0;
		for(int i=0;i<lenListe;i++) {
			if(liste[i].entity != null && (liste[i].entity instanceof Hero || liste[i].entity instanceof NPC)) {
				this.transparent=true;
				res=Math.max(res, liste[i].tpBlock.target.size());
			}
		}
		opacityDiv=res;
	}
	

}
