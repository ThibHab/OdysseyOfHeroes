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

	void updateCam(Cowboy player1, Cowboy player2, int w, int h) {
		//TODO fix when updating size window
		this.camera = mid(player1.location, player2.location);
		if ((diff(player1.location.getX(), player2.location.getX(), map.lenX) < 7
				&& diff(player1.location.getY(), player2.location.getY(), map.lenY) < 7) || nbTileX == 0
				|| nbTileY == 0) {
			nbTileX = (int) diff(player1.location.getX(), player2.location.getY(), map.lenX) + bufferTile * 2;
			nbTileY = (int) diff(player1.location.getY(), player2.location.getY(), map.lenY) + bufferTile * 2;
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

	public Location gridToPixel(Location loc, float offsetX,float offsetY) {
		Location res = new Location(0, 0);
		res.setY(((loc.getY() - (int) this.camera.getY() + map.lenY + this.nbTileY / 2) % map.lenY +offsetY)
				* this.tileSize);
		res.setX(((loc.getX() - (int) this.camera.getX() + map.lenX + this.nbTileX / 2) % map.lenX +offsetX)
				* this.tileSize);
		return res;
	}
	

	void setOffsetCam() {
		Location camTemp=gridToPixel(camera,0,0);
		offset.setX((((float)game.m_canvas.getWidth() / 2) -camTemp.getX())/this.tileSize);
		offset.setY((((float)game.m_canvas.getHeight() / 2) -camTemp.getY())/this.tileSize);
	}

	public void paint(Graphics g) {
		updateCam(game.player1, game.player2, game.m_canvas.getWidth(), game.m_canvas.getHeight());
		setOffsetCam();
		for (int j = 0; j < nbTileY; j++) {
			for (int i = 0; i < nbTileX; i++) {
				map.map[(int) (i + this.camera.getX() + map.lenX - nbTileX / 2)
						% map.lenX][(int) (j + this.camera.getY() + map.lenY - nbTileY / 2) % map.lenY]
						.paint(g, i +this.offset.getX(), j+this.offset.getY() , tileSize);
			}
		}
		g.setColor(Color.red);
		Location center = gridToPixel(camera,this.offset.getX(),this.offset.getY());
		g.fillOval((int) (center.getX()), (int) (center.getY()), 5, 5);
	}

}
