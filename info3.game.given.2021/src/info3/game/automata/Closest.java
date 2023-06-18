package info3.game.automata;

import info3.game.Game;
import info3.game.constants.EntitiesConst;
import info3.game.entity.Entity;
import info3.game.map.*;

public class Closest extends Aut_Condition {

	Aut_Direction dir;
	Aut_Category cat;

	public Closest(Aut_Direction dir, Aut_Category cat) {
		this.dir = dir;
		this.cat = cat;
	}

	public int getXmap(int x) {
		return (x + EntitiesConst.MAP.lenX) % EntitiesConst.MAP.lenX;
	}

	public int getYmap(int y) {
		return (y + EntitiesConst.MAP.lenY) % EntitiesConst.MAP.lenY;
	}

	@Override
	public boolean eval(Entity e, Game g) {
		int range = 1;
		Entity found = null;
		int xEnt = (int) e.location.getX();
		int yEnt = (int) e.location.getY();

		Tile[][] map = EntitiesConst.MAP_MATRIX;
		Tile tile;

		while (e.detectionRadius >= range) {
			int xSearch = xEnt + range;
			int ySearch = yEnt - range;
			tile = map[getXmap(xSearch)][getYmap(ySearch)];
			if (tile.entity != null && tile.entity.category == cat) {
				if (dir == Aut_Direction.N || dir == Aut_Direction.E)
					return true;
				return false;
			}

			for (int i = 0; i < 2 * range -1; i++) {
				xSearch -= 1;
				tile = map[getXmap(xSearch)][getYmap(ySearch)];
				if (tile.entity != null && tile.entity.category == cat) {
					if (dir == Aut_Direction.N)
						return true;
					return false;
				}
			}
			
			xSearch -= 1;
			tile = map[getXmap(xSearch)][getYmap(ySearch)];
			if (tile.entity != null && tile.entity.category == cat) {
				if (dir == Aut_Direction.N || dir == Aut_Direction.W)
					return true;
				return false;
			}
			
			for (int i = 0; i < 2 * range-1; i++) {
				ySearch += 1;
				tile = map[getXmap(xSearch)][getYmap(ySearch)];
				if (tile.entity != null && tile.entity.category == cat) {
					if (dir == Aut_Direction.W)
						return true;
					return false;
				}
			}
			
			ySearch += 1;
			tile = map[getXmap(xSearch)][getYmap(ySearch)];
			if (tile.entity != null && tile.entity.category == cat) {
				if (dir == Aut_Direction.S || dir == Aut_Direction.W)
					return true;
				return false;
			}
			
			for (int i = 0; i < 2 * range-1; i++) {
				xSearch += 1;
				tile = map[getXmap(xSearch)][getYmap(ySearch)];
				if (tile.entity != null && tile.entity.category == cat) {
					if (dir == Aut_Direction.S)
						return true;
					return false;
				}
			}
			
			xSearch += 1;
			tile = map[getXmap(xSearch)][getYmap(ySearch)];
			if (tile.entity != null && tile.entity.category == cat) {
				if (dir == Aut_Direction.S || dir == Aut_Direction.E)
					return true;
				return false;
			}
			
			for (int i = 0; i < 2 * range -1; i++) {
				ySearch -= 1;
				tile = map[getXmap(xSearch)][getYmap(ySearch)];
				if (tile.entity != null && tile.entity.category == cat) {
					if (dir == Aut_Direction.E)
						return true;
					return false;
				}
			}
			range++;
		}
		return false;
	}

}
