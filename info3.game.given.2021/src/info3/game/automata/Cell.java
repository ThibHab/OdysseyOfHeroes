package info3.game.automata;

import info3.game.Game;
import info3.game.entity.Entity;
import info3.game.map.Map;
import info3.game.map.Tile;

public class Cell extends Aut_Condition {

	Aut_Direction dir;
	Aut_Category cat;

	public Cell(Aut_Direction direction, Aut_Category cat) {
		this.dir = direction;
		this.cat = cat;
	}

	@Override
	public boolean eval(Entity e, Game g) {
		// TODO Waiting for attribute tile in Entity and Map pushed
		int x = (int)e.location.getX();
		int y = (int)e.location.getY();
		Tile[][] map = ((Map)g.map).map;
		switch(dir) {
		case N:
			y -= 1;
			break;
		case S:
			y += 1;
			break;
		case E:
			x += 1;
			break;
		case W:
			x -= 1;
			break;
		case F:
			switch(e.direction) {
			case N:
				y -= 1;
				break;
			case S:
				y += 1;
				break;
			case E:
				x += 1;
				break;
			case W:
				x -= 1;
				break;				
			}
			break;
		case B:
			switch(e.direction) {
			case S:
				y -= 1;
				break;
			case N:
				y += 1;
				break;
			case W:
				x += 1;
				break;
			case E:
				x -= 1;
				break;				
			}
			break;
		case L:
			switch(e.direction) {
			case N:
				x -= 1;
				break;
			case S:
				x += 1;
				break;
			case W:
				y += 1;
				break;
			case E:
				y -= 1;
				break;				
			}
			break;
		case R:
			switch(e.direction) {
			case N:
				x += 1;
				break;
			case S:
				x -= 1;
				break;
			case W:
				y -= 1;
				break;
			case E:
				y += 1;
				break;				
			}
			break;
		}
		if (map[x][y] != null && map[x][y].entity.category == cat)
			return true;
		return false;
	}

}
