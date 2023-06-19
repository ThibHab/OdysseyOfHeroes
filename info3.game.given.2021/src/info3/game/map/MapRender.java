package info3.game.map;

import java.awt.Color;
import java.awt.Graphics;

import info3.game.Game;
import info3.game.automata.Aut_Direction;
import info3.game.constants.EntitiesConst;
import info3.game.entity.*;
//import info3.game.entity.Location;

public class MapRender {

	Game game;
	Map map;
	public int nbTileX, nbTileY;
	public int tileSize;
	int bufferTile = 6;

	public Location camera;
	public Location offset = new Location(0.0f, 0.0f);

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

	float roundDeci(float val, int nbDec) {
		long factor = (long) Math.pow(10, nbDec);
		return (float) Math.round(val * factor) / factor;
	}

	public void updateCam(Cowboy player1, Cowboy player2, int w, int h) {
		this.camera = mid(player1.location, player2.location);
		float viewX = diff(player2.location.getX(), player1.location.getX(), map.lenX) + bufferTile;
		float viewY = diff(player2.location.getY(), player1.location.getY(), map.lenY) + bufferTile;
		if(viewX<EntitiesConst.MAX_DIFFX*2+bufferTile && viewY<EntitiesConst.MAX_DIFFY*2+bufferTile) {
			Location upLeft = new Location((camera.getX() - viewX / 2 + map.lenX) % map.lenX,
					(camera.getY() - viewY / 2 + map.lenY) % map.lenY);
			nbTileX = (int) Math.ceil(
					diff((float) Math.floor(upLeft.getX()), (float) Math.ceil(upLeft.getX() + viewX) % map.lenX, map.lenX))
					+ 1;
			nbTileY = (int) Math.ceil(
					diff((float) Math.floor(upLeft.getY()), (float) Math.ceil(upLeft.getY() + viewY) % map.lenY, map.lenY))
					+ 1;
			double tempx = w / viewX;
			double tempy = h / viewY;
			if (tempx > tempy) {
				nbTileX = (int) Math.ceil(nbTileY * (double) w / h);
			}
			if (tempy > tempx) {
				nbTileY = (int) Math.ceil(nbTileX * (double) h / w);
			}
			this.tileSize = (int) Math.min(Math.ceil(tempy), Math.ceil(tempx));
		}
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

	public boolean moveDooable(Location loc,Aut_Direction dir,int h, int w) {
		Location pix=this.gridToPixel(loc, true);
		switch(dir) {
		case N:
			return pix.getY()+tileSize/2>=0;
		case S:
			return pix.getY()+tileSize/2<h;
		case W:
			return pix.getX()+tileSize/2>=0;
		case E:
			return pix.getX()+tileSize/2<w;
		default:
			return false;
		}
		}

	public void setOffsetCam() {
		Location camTemp = gridToPixel(camera, false);
		offset.setX((((float) game.m_canvas.getWidth() / 2) - camTemp.getX()) / this.tileSize);
		offset.setY((((float) game.m_canvas.getHeight() / 2) - camTemp.getY()) / this.tileSize);
		offset.setX(roundDeci(offset.getX(), 3));
		offset.setY(roundDeci(offset.getY(), 3));
	}

	public void paint(Graphics g) {
		updateCam(game.player1, game.player2, game.m_canvas.getWidth(), game.m_canvas.getHeight());
		setOffsetCam();
		for (int j = 0; j < nbTileY; j++) {
			for (int i = 0; i < nbTileX; i++) {
				int mapX = (int) (i + this.camera.getX() + map.lenX - nbTileX / 2) % map.lenX;
				int mapY = (int) (j + this.camera.getY() + map.lenY - nbTileY / 2) % map.lenY;
				Tile renderTile = map.map[mapX][mapY];
				renderTile.paint(g, roundDeci((i + this.offset.getX()) * tileSize, 3),
						roundDeci((j + this.offset.getY()) * tileSize, 3), tileSize);
				renderTile.location.setX(mapX);
				renderTile.location.setY(mapY);
			}
		}
	}

}
