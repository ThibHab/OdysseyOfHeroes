package info3.game.map;

import info3.game.constants.EntitiesConst;
import info3.game.entity.*;

public class WorldMap extends Map {

	public WorldMap(int nb_x, int nb_y, Entity p1, Entity p2) {
		super(nb_x, nb_y, p1, p2);
		
		this.setSurfaceBackground(0, 0, lenX, lenY, "Grass");
		this.setLac(9, 12, 3);
		this.setVillage(30, 30, 25);
		this.setForest(70, 70, 30, 2);
			
		
		this.setEntityRandomly(0, 0, lenX - 2, 2, "Tree", 2, 30);
		this.setEntityRandomly(0, 0, lenX - 2, 2, "Bush", 2, 10);
		this.setEntityRandomly(0, 0, lenX - 2, 2, "Rock", 2, 15);
		
		setPlayer(29, 31, p1);
		setPlayer(31, 31, p2);
		Coin c = new Coin(new Location(0,0));
		Villager v = new Villager(new Location(0,1));
		map[(int)c.location.getX()][(int)c.location.getY()].entity = c;
		map[(int)v.location.getX()][(int)v.location.getY()].entity = v;
		
	}

}
