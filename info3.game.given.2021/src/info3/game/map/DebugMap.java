package info3.game.map;

import info3.game.entity.Entity;
import info3.game.entity.Location;

public class DebugMap extends Map {

	public DebugMap(int nb_x, int nb_y, Entity p1, Entity p2) {
		super(nb_x, nb_y, p1, p2);
		Location lp1 = new Location(0, 0);
		Location lp2 = new Location(1, 1);
		this.player1.location.setX(lp1.getX());
		this.player1.location.setY(lp1.getY());
		this.player2.location.setX(lp2.getX());
		this.player2.location.setY(lp2.getY());
		for (int i = 0; i < nb_x; i++) {
			for (int j = 0; j < nb_y; j++) {
				Location l = new Location(i, j);
				this.map[i][j] = new GrassTile(l);
			}
		}
	}

}
