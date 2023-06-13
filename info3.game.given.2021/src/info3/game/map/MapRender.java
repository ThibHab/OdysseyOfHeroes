package info3.game.map;

import info3.game.Game;
import info3.game.entity.Location;

public class MapRender {
	Location camera;
	Map map;
	int nbTileX, nbTileY;
	int tileSize;
	int bufferTile = 4;
	Game game;

	MapRender(Map map, Game game) {
		this.map = map;
		this.game = game;

	}

	Location mid(Location a, Location b, int len) {
		Location res = new Location(0, 0);
		float tmp = (a.getX() + b.getX()) / 2;
		float tmp2 = (Math.min(a.getX(), b.getX()) + len + Math.max(a.getX(), b.getX()));
		if (diff(tmp, a.getX(), len) < diff(tmp, b.getX(), len)) {
			res.setX(tmp);
		} else {
			res.setX(tmp2);
		}
		tmp = (a.getY() + b.getY()) / 2;
		tmp2 = (Math.min(a.getY(), b.getY()) + len + Math.max(a.getY(), b.getY()));
		if (diff(tmp, a.getY(), len) < diff(tmp, b.getY(), len)) {
			res.setY(tmp);
		} else {
			res.setY(tmp2);
		}
		return res;
	}

	float diff(float a, float b, int len) {
		float tmp = Math.abs(a - b);
		float tmp2 = Math.min(a, b) + len - Math.max(a, b);
		if (tmp < tmp2) {
			return tmp;
		}
		return tmp2;
	}

	int roundup(int a, double b) {
		double tmp = Math.ceil(a / b);
		return (int) tmp;
	}

	void updateCam(Hero player1, Hero player2, int w, int h) {
		this.camera = mid(player1.location, player2.location);
		if (diff(player1.x, player2.x) < 7 && diff(player1.y, player2.y) < 7) {
			nbTileX = diff(player1.x, player2.x, map.lenX) + bufferTile * 2;
			nbTileY = diff(player1.y, player2.y, map.lenY) + bufferTile * 2;
			int tempx = (int) Math.ceil(w / nbTileX);
			int tempy = (int) Math.ceil(h / nbTileY);
			if (tempx > tempy) {
				nbTileX = roundup(w, tempy);
				nbTileY = roundup(h, tempy);
			} else {
				nbTileY = roundup(h, tempx);
				nbTileX = roundup(w, tempx);
			}
			this.tileSize = Math.min(tempx, tempy);
		}
	}

}
