package info3.game.map;

import java.awt.Graphics;
import java.util.Random;

import info3.game.entity.*;

public abstract class Map implements IMap {
	public Tile[][] map;
	int lenX, lenY;
	Entity player1, player2;

	public Map(int nb_x, int nb_y, Entity p1, Entity p2) {
		this.lenX = nb_x;
		this.lenY = nb_y;
		this.player1 = p1;
		this.player2 = p2;
		this.map = new Tile[lenX][lenY];
	}

	public void setEntityRandomly(int x, int y, int areaSize, int spaceBetween, Entity ent, long seed) {
		if (!(ent instanceof Bush) && !(ent instanceof Rock) && !(ent instanceof Tree)) {
			return;
		}
		Random r = new Random(seed);
		for (int i = x; i < x + areaSize; i++) {
			for (int j = y; j < y + areaSize; j++) {
				int n = r.nextInt(5);
				if (n == 1) {
					boolean already = false;
					int rec_x = i - spaceBetween;
					int rec_y = j - spaceBetween;
					while (rec_x < 0) {
						while (rec_y < 0) {
							if (map[lenX + rec_x][lenY + rec_y].entity != null) {

								already = true;
							}
							System.out.println("Carré: (" + rec_x + ";" + rec_y + ")");
							rec_y++;
						}
						while (rec_y <= j + spaceBetween) {
							if (map[lenX + rec_x][rec_y].entity != null) {
								already = true;
							}
							System.out.println("Carré: (" + rec_x + ";" + rec_y + ")");
							rec_y++;
						}
						rec_x++;
					}
					while (rec_x <= i + spaceBetween) {
						rec_y = j - spaceBetween;
						while (rec_y < 0) {
							if (map[rec_x][lenY + rec_y].entity != null) {
								already = true;
							}
							System.out.println("Carré: (" + rec_x + ";" + rec_y + ")");
							rec_y++;
						}
						while (rec_y <= j + spaceBetween) {
							if (map[rec_x][rec_y].entity != null) {
								already = true;
							}
							System.out.println("Carré: (" + rec_x + ";" + rec_y + ")");
							rec_y++;
						}
						rec_x++;
					}
					if (!already) {
						if (ent instanceof Bush) {
							map[i][j].entity = new Bush(new Location(i, j));
						} else if (ent instanceof Rock) {
							map[i][j].entity = new Rock(new Location(i, j));
						} else {
							map[i][j].entity = new Tree(new Location(i, j));
						}

						System.out.println("Objet créé en: (" + i + ";" + j + ")");
					}
				}
			}
		}
	}
}
