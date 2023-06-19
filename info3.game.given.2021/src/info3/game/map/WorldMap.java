package info3.game.map;

import info3.game.entity.*;

public class WorldMap extends Map {

	public WorldMap(int nb_x, int nb_y, Entity p1, Entity p2) {
		super(nb_x, nb_y, p1, p2);
		Location lp1 = new Location(0, 4);
		Location lp2 = new Location(1, 4);
		this.player1.location.setX(lp1.getX());
		this.player1.location.setY(lp1.getY());
		this.player2.location.setX(lp2.getX());
		this.player2.location.setY(lp2.getY());
		GrassTile grass = new GrassTile(null);
		this.setSurfaceBackground(0, 0, lenX, lenY, "Grass");
		this.setCircleWaterBackground(12, 12, 3);
		this.setCircleWaterBackground(14, 14, 3);
		this.setVillage(0, 4, 12);		
		
		this.setEntityRandomly(0, 0, lenX - 2, 2, "Bush", 2, 6);
		this.setEntityRandomly(0, 0, lenX - 2, 2, "Rock", 2, 15);
		this.setEntityRandomly(0, 0, lenX - 2, 2, "Tree", 2, 8);
		Location lgob = new Location(0, 0);
		Goblin gob = new Goblin(lgob);
		map[0][0].entity = gob;
		map[(int)lp1.getX()][(int)lp1.getY()].entity = player1;
		map[(int)lp2.getX()][(int)lp2.getY()].entity = player2;
	}

}
