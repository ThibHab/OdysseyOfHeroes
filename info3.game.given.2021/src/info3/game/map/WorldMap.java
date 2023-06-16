package info3.game.map;

import info3.game.entity.*;

public class WorldMap extends Map {

	public WorldMap(int nb_x, int nb_y, Entity p1, Entity p2) {
		super(nb_x, nb_y, p1, p2);
		Location lp1 = new Location(0, 4);
		this.player1.location.setX(lp1.getX());
		this.player1.location.setY(lp1.getY());
		GrassTile grass = new GrassTile(null);
		this.setSurfaceBackground(0, 0, lenX, lenY, grass);
		WaterTile water = new WaterTile(null);
//		this.setSurfaceBackground(12, 12, 3, 3, water);
		this.setCircleWaterBackground(12, 12, water, 3);
		this.setCircleWaterBackground(14, 14, water, 3);
//		DirtTile dirt = new DirtTile(null);
		this.setVillage(0, 4, 12);
//		this.setSurfaceBackground(0, 0, 4, 1, dirt);
//		this.setSurfaceBackground(4, 0, 1, 5, dirt);
//		Tree forest = new Tree(null);
//		this.setEntityRandomly(20, 20, 40, 0, forest, 2, 2);
		Bush bush = new Bush(null);
		this.setEntityRandomly(0, 0, lenX - 2, 2, bush, 2, 6);
		Rock rock = new Rock(null);
		this.setEntityRandomly(0, 0, lenX - 2, 2, rock, 2, 15);
		Tree tree = new Tree(null);
		this.setEntityRandomly(0, 0, lenX - 2, 2, tree, 2, 8);
		map[(int)lp1.getX()][(int)lp1.getY()].entity = player1;
	}

}
