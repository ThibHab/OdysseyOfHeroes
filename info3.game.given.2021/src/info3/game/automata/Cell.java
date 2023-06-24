package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;
import info3.game.map.Map;
import info3.game.map.Tile;

public class Cell extends Aut_Condition {

	Aut_Direction dir;
	Aut_Category cat;
	int distance;

	public Cell(Aut_Direction direction, Aut_Category cat, int distance) {
		this.dir = direction;
		this.cat = cat;
		if (distance == 0) {
			distance = 1;
		}
		this.distance = distance;
	}

	@Override
	public boolean eval(Entity e, Game g) {
		// TODO Waiting for attribute tile in Entity and Map pushed
		for (int i = 1; i <= distance; i++) {
			int x = (int) e.location.getX();
			int y = (int) e.location.getY();
			Tile[][] map = ((Map) g.map).map;

			switch (dir) {
			case N:
				y -= i;
				break;
			case S:
				y += i;
				break;
			case E:
				x += i;
				break;
			case W:
				x -= i;
				break;
			case F:
				switch (e.direction) {
				case N:
					y -= i;
					break;
				case S:
					y += i;
					break;
				case E:
					x += i;
					break;
				case W:
					x -= i;
					break;
				}
				break;
			case B:
				switch (e.direction) {
				case S:
					y -= i;
					break;
				case N:
					y += i;
					break;
				case W:
					x += i;
					break;
				case E:
					x -= i;
					break;
				}
				break;
			case L:
				switch (e.direction) {
				case N:
					x -= i;
					break;
				case S:
					x += i;
					break;
				case W:
					y += i;
					break;
				case E:
					y -= i;
					break;
				}
				break;
			case R:
				switch (e.direction) {
				case N:
					x += i;
					break;
				case S:
					x -= i;
					break;
				case W:
					y -= i;
					break;
				case E:
					y += i;
					break;
				}
				break;
			}

			x = (x + ((Map) g.map).lenX) % ((Map) g.map).lenX;
			y = (y + ((Map) g.map).lenY) % ((Map) g.map).lenY;
			if (cat == Aut_Category.V && (map[x][y].entity == null || map[x][y].entity.dead == true))
				return true;
			if (map[x][y].entity != null && map[x][y].entity.category == cat && map[x][y].entity.dead == false)
				return true;
		}
		return false;
	}

}
