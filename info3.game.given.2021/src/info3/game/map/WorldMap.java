package info3.game.map;

import info3.game.entity.*;

public class WorldMap extends Map {

	public WorldMap(int nb_x, int nb_y, Entity p1, Entity p2) {
		super(nb_x, nb_y, p1, p2);
		Location lp1 = new Location(0, 0);
		this.player1.location.setX(lp1.getX());
		this.player1.location.setY(lp1.getY());
		GrassTile grass = new GrassTile(null);
		this.setSurfaceBackground(0, 0, lenX, lenY, grass);
		WaterTile water = new WaterTile(null);
		this.setSurfaceBackground(12, 12, 3, 3, water);
		DirtTile dirt = new DirtTile(null);
		this.setSurfaceBackground(0, 0, 4, 1, dirt);
		this.setSurfaceBackground(4, 0, 1, 5, dirt);
		Bush bush = new Bush();
		this.setEntityRandomly(0, 0, lenX - 2, 2, bush, 2, 6);
		map[(int)lp1.getX()][(int)lp1.getY()].entity = player1;
	}

}
