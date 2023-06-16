package info3.game.map;

import java.awt.Color;
import java.awt.Graphics;

import info3.game.Game;
import info3.game.entity.*;
//import info3.game.entity.Location;

public class MapRender {
	public Location camera;
	Map map;
	public int nbTileX, nbTileY;
	public int tileSize;
	public Location offset = new Location(0.0f, 0.0f);
	int bufferTile = 4;
	Game game;

	public MapRender(Map map, Game game) {
		this.map = map;
		this.game = game;

	}

	Location mid(Location loc1, Location loc2) {
		Location a = new Location(loc1.getX() + 0.5f, loc1.getY() + 0.5f);
		Location b = new Location(loc2.getX() + 0.5f, loc2.getY() + 0.5f);
		Location res = new Location(0, 0);
		float tmp = (a.getX() + b.getX()) / 2;
		float tmp2 = ((Math.min(a.getX(), b.getX()) + map.lenX + Math.max(a.getX(), b.getX())) / 2);
		if (diff(tmp, a.getX(), map.lenX) < diff(tmp2, a.getX(), map.lenX)) {
			res.setX((tmp + map.lenX) % map.lenX);
		} else {
			res.setX((tmp2 + map.lenX) % map.lenX);
		}
		tmp = (a.getY() + b.getY()) / 2;
		tmp2 = ((Math.min(a.getY(), b.getY()) + map.lenY + Math.max(a.getY(), b.getY())) / 2);
		if (diff(tmp, a.getY(), map.lenY) < diff(tmp2, a.getY(), map.lenY)) {
			res.setY((tmp + map.lenY) % map.lenY);
		} else {
			res.setY((tmp2 + map.lenY) % map.lenY);
		}
		return res;
	}

	public float diff(float a, float b, int len) {
		float tmp = Math.abs(a - b);
		float tmp2 = Math.min(a, b) + len - Math.max(a, b);
		if (tmp < tmp2) {
			return tmp;
		}
		return tmp2;
	}

	int roundup(int a, double b) {
		double tmp;
		tmp = Math.ceil(a / b) + 1;
		return (int) tmp;
	}
	
	float roundDeci(float val, int nbDec) {
		long factor=(long) Math.pow(10, nbDec);
		return (float)Math.round(val*factor)/factor; 
	}

	void updateCam(Cowboy player1, Cowboy player2, int w, int h) {
		this.camera = mid(player1.location, player2.location);
		nbTileX = (int) diff(camera.getX(), player1.location.getX(), map.lenX) + bufferTile * 2;
		nbTileY = (int) diff(camera.getY(), player1.location.getY(), map.lenY) + bufferTile * 2;
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

	public Location gridToPixel(Location loc, boolean offset) {
		Location res = new Location(0, 0);
		if (offset) {
			res.setY(((loc.getY() - (int) this.camera.getY() + map.lenY + this.nbTileY / 2) % map.lenY
					+ this.offset.getY()) * this.tileSize);
			res.setX(((loc.getX() - (int) this.camera.getX() + map.lenX + this.nbTileX / 2) % map.lenX
					+ this.offset.getX()) * this.tileSize);
		} else {
			res.setY(
					((loc.getY() - (int) this.camera.getY() + map.lenY + this.nbTileY / 2) % map.lenY) * this.tileSize);
			res.setX(
					((loc.getX() - (int) this.camera.getX() + map.lenX + this.nbTileX / 2) % map.lenX) * this.tileSize);
		}
		return res;
	}

	void setOffsetCam() {
		Location camTemp = gridToPixel(camera, false);
		offset.setX((((float) game.m_canvas.getWidth() / 2) - camTemp.getX()) / this.tileSize);
		offset.setY((((float) game.m_canvas.getHeight() / 2) - camTemp.getY()) / this.tileSize);
		offset.setX(roundDeci(offset.getX(),3));
		offset.setY(roundDeci(offset.getY(),3));
	}

	public void paint(Graphics g) {
		updateCam(game.player1, game.player2, game.m_canvas.getWidth(), game.m_canvas.getHeight());
		setOffsetCam();
		for (int j = 0; j < nbTileY; j++) {
			for (int i = 0; i < nbTileX; i++) {
				map.map[(int) (i + this.camera.getX() + map.lenX - nbTileX / 2)
						% map.lenX][(int) (j + this.camera.getY() + map.lenY - nbTileY / 2) % map.lenY]
						.paint(g, roundDeci((i + this.offset.getX())*tileSize,3), roundDeci((j + this.offset.getY())*tileSize,3), tileSize);
			}
		}
		
		for (int j = 0; j < nbTileY; j++) {
			for (int i = 0; i < nbTileX; i++) {
				if (map.map[(int) (i + this.camera.getX() + map.lenX - nbTileX / 2)
						% map.lenX][(int) (j + this.camera.getY() + map.lenY - nbTileY / 2) % map.lenY].entity != null) {
					map.map[(int) (i + this.camera.getX() + map.lenX - nbTileX / 2)
							% map.lenX][(int) (j + this.camera.getY() + map.lenY - nbTileY / 2) % map.lenY].entity
							.paint(g, tileSize, roundDeci((i + this.offset.getX())*tileSize,3), roundDeci((j + this.offset.getY())*tileSize,3));
				}
			}
		}
	}

}
