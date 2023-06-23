package info3.game.map;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
		this.camera = new Location(0, 0);
	}

	float roundDeci(float val, int nbDec) {
		long factor = (long) Math.pow(10, nbDec);
		return (float) Math.round(val * factor) / factor;
	}

	public void updateCam(Melee player1, Range player2, int w, int h) {
		this.camera = map.mid(player1.location, player2.location);
		float viewX = map.diffX(player2.location.getX(), player1.location.getX()) + bufferTile;
		float viewY = map.diffY(player2.location.getY(), player1.location.getY()) + bufferTile;
		if (viewX < EntitiesConst.MAX_DIFFX * 2 + bufferTile && viewY < EntitiesConst.MAX_DIFFY * 2 + bufferTile) {
			Location upLeft = new Location((camera.getX() - viewX / 2 + map.lenX) % map.lenX,
					(camera.getY() - viewY / 2 + map.lenY) % map.lenY);
			nbTileX = (int) Math.ceil(map.diffX((float) Math.floor(upLeft.getX()),
					(float) Math.ceil(upLeft.getX() + viewX) % map.lenX)) + 1;
			nbTileY = (int) Math.ceil(map.diffY((float) Math.floor(upLeft.getY()),
					(float) Math.ceil(upLeft.getY() + viewY) % map.lenY)) + 1;
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

	public boolean moveDooable(Location loc, Aut_Direction dir, int h, int w) {
		Location pix = this.gridToPixel(loc, true);
		switch (dir) {
		case N:
			return pix.getY() + tileSize / 2 >= 0;
		case S:
			return pix.getY() + tileSize / 2 < h;
		case W:
			return pix.getX() + tileSize / 2 >= 0;
		case E:
			return pix.getX() + tileSize / 2 < w;
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
	
	float opacity(float d) {
		float opa=1-(float)(Math.pow(Math.E,-(d*d/2)));
		if(opa>0.95) {
			return 0.95f;
		}
		return opa;
	}

	void paintBackground(Graphics g) {
		for (int j = 0; j < nbTileY; j++) {
			for (int i = 0; i < nbTileX; i++) {
				int mapX = (int) (i + this.camera.getX() + map.lenX - nbTileX / 2) % map.lenX;
				int mapY = (int) (j + this.camera.getY() + map.lenY - nbTileY / 2) % map.lenY;
				Tile renderTile = map.map[mapX][mapY];
				renderTile.paint(g, roundDeci((i + this.offset.getX()) * tileSize, 3),
						roundDeci((j + this.offset.getY()) * tileSize, 3), tileSize);
			}
		}
	}

	void paintEffect(Graphics g) {

	}

	void paintEntity(Graphics g) {
		List<TransparentDecorElement> transparent = new LinkedList<TransparentDecorElement>();
		for (int j = 0; j < nbTileY; j++) {
			for (int i = 0; i < nbTileX; i++) {
				int mapX = (int) (i + this.camera.getX() + map.lenX - nbTileX / 2) % map.lenX;
				int mapY = (int) (j + this.camera.getY() + map.lenY - nbTileY / 2) % map.lenY;
				Tile renderTile = map.map[mapX][mapY];
				if (renderTile.entity != null && !(renderTile.entity instanceof TransparentDecorElement)) {
					if (renderTile.entity instanceof Hero) {
						((Hero) renderTile.entity).paint(g, tileSize);
					} else {
						renderTile.entity.paint(g, tileSize, roundDeci((i + this.offset.getX()) * tileSize, 3),
								roundDeci((j + this.offset.getY()) * tileSize, 3));
					}
				}
				boolean isTDE = (renderTile.entity instanceof TransparentDecorElement
						&& !transparent.contains(renderTile.entity));
				if (isTDE) {
					transparent.add((TransparentDecorElement) renderTile.entity);
				}
				if (renderTile.tpBlock != null) {
					Iterator it = renderTile.tpBlock.target.iterator();
					while (it.hasNext()) {
						TransparentDecorElement tde = (TransparentDecorElement) it.next();
						tde.checkTransparent();
						if (!transparent.contains(tde)) {
							transparent.add(tde);
						}
					}
				}
			}
		}
		Iterator it = transparent.iterator();
		while (it.hasNext()) {
			TransparentDecorElement tde = (TransparentDecorElement) it.next();
			Location l = this.gridToPixel(tde.location, true);
			tde.paint(g, tileSize, l.getX(), l.getY());
		}
	}
	
	void paintProj(Graphics g) {
		for (int i = 0; i < EntitiesConst.MAP.projectiles.size(); i++) {
			EntitiesConst.MAP.projectiles.get(i).paint(g, tileSize, roundDeci((this.offset.getX()) * tileSize, 3),
					roundDeci((this.offset.getY()) * tileSize, 3));
		}
	}
	
	void paintDark(Graphics g) {
		for (int j = 0; j < nbTileY; j++) {
			for (int i = 0; i < nbTileX; i++) {
				int mapX = (int) (i + this.camera.getX() + map.lenX - nbTileX / 2) % map.lenX;
				int mapY = (int) (j + this.camera.getY() + map.lenY - nbTileY / 2) % map.lenY;
				for(int sj=0;sj<2;sj++) {
					for(int si=0;si<2;si++) {
						int Xscreen=(int)roundDeci((i + this.offset.getX()+(float)si/2) * tileSize, 3);
						int Yscreen=(int)roundDeci((j + this.offset.getY()+(float)sj/2) * tileSize, 3);
						Location tile=map.add(new Location(mapX,mapY), new Location(si*0.5f+0.25f,sj*0.5f+0.25f));
						Location player=map.add(game.player1.location,new Location(0.5f,0.5f));
						float dist=map.dist(player, tile);
						g.setColor(new Color(0,0,0,(int)(opacity(dist)*255)));
						g.fillRect(Xscreen, Yscreen, tileSize/2, tileSize/2);
//						g.setColor(Color.red);
//						g.drawString(""+opacity(dist), Xscreen, Yscreen+tileSize/8);
					}
				}
				
			}
		}
	}

	public void paint(Graphics g) {
		updateCam(game.player1, game.player2, game.m_canvas.getWidth(), game.m_canvas.getHeight());
		setOffsetCam();

		// BACKGGROUND
		paintBackground(g);
		// EFFECT
		paintEffect(g);
		paintProj(g);
		// DECOR & PLAYER
		paintEntity(g);
		// NIGHT
		//paintDark(g);
	}

}
