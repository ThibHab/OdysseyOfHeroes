package info3.game.map;

import java.awt.Color;
import java.awt.Graphics;

import info3.game.Game;
import info3.game.entity.*;
//import info3.game.entity.Location;

public class MapRender {
	public Location camera;
	Map map;
	int nbTileX, nbTileY;
	int tileSize;
	int bufferTile = 4;
	Game game;

	public MapRender(Map map, Game game) {
		this.map = map;
		this.game = game;

	}

	Location mid(Location a, Location b) {
		Location res = new Location(0, 0);
		float tmp = (a.getX() + b.getX()) / 2;
		float tmp2 = (Math.min(a.getX(), b.getX()) + map.lenX + Math.max(a.getX(), b.getX())/2);
		if (diff(tmp, a.getX(), map.lenX) < diff(tmp, b.getX(), map.lenX)) {
			res.setX((tmp+map.lenX)%map.lenX);
		} else {
			res.setX((tmp2+map.lenX)%map.lenX);
		}
		tmp = (a.getY() + b.getY()) / 2;
		tmp2 = (Math.min(a.getY(), b.getY()) + map.lenY + Math.max(a.getY(), b.getY())/2);
		if (diff(tmp, a.getY(), map.lenY) < diff(tmp, b.getY(), map.lenY)) {
			res.setY((tmp+map.lenY)%map.lenY);
		} else {
			res.setY((tmp2+map.lenY)%map.lenY);
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
		this.camera = mid(player1.location, player2.location);
		if (diff(player1.location.getX(), player2.location.getX(), map.lenX) < 7
				&& diff(player1.location.getY(), player2.location.getY(), map.lenY) < 7) {
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
	
	public Location gridToPixel(Location loc) {
		Location res=new Location(0,0);
		if(this.camera.getX()-loc.getX()<0) {
			res.setX((loc.getX() - this.camera.getX() + this.nbTileX / 2) % map.lenX * this.tileSize);
		}else {
			res.setX((loc.getX() - (this.camera.getX()-map.lenX) + this.nbTileX / 2) % map.lenX * this.tileSize);
		}
		if(this.camera.getY()-loc.getY()<0) {
			res.setY((loc.getY() - this.camera.getY() + this.nbTileY / 2) % map.lenY * this.tileSize);
		}else {
			res.setY((loc.getY() - (this.camera.getY()-map.lenY) + this.nbTileY / 2) % map.lenY * this.tileSize);
		}
		return res;
	}

	public void paint(Graphics g) {
		updateCam(game.player1, game.player2, game.m_canvas.getWidth(), game.m_canvas.getHeight());
		for (int j = 0; j < nbTileY; j++) {
			for (int i = 0; i < nbTileX; i++) {
				// TODO toute les histoire de float a prendre en compte (decalage)
				map.map[(int) (i + this.camera.getX() + map.lenX - nbTileX / 2)% map.lenX]
						[(int) (j + this.camera.getY() + map.lenY - nbTileY / 2) % map.lenY]
						.paint(g, i, j, tileSize);
			}
		}
		g.setColor(Color.red);
		g.fillOval(tileSize*nbTileX/2,tileSize*nbTileY/2, 5, 5);
		g.setColor(Color.magenta);
		g.fillOval(game.m_canvas.getWidth()/2, game.m_canvas.getHeight()/2,5,5);
	}

}
