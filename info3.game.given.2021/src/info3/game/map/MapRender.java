package info3.game.map;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import animations.Effect;
import info3.game.Game;
import info3.game.automata.Aut_Direction;
import info3.game.constants.EntitiesConst;
//import info3.game.entity.Location;
import info3.game.entity.Hero;
import info3.game.entity.Location;
import info3.game.entity.Melee;
import info3.game.entity.Projectile;
import info3.game.entity.Range;
import info3.game.entity.SpeechBubble;
import info3.game.entity.Torch;
import info3.game.entity.TransparentDecorElement;

public class MapRender {

	Game game;
	Map map;
	public int nbTileX, nbTileY;
	public int tileSize;
	int bufferTile = 6;

	public Location camera = new Location(0.0f, 0.0f);
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

	public void bossCam(int w, int h) {
		DungeonMap dmap = (DungeonMap) map;
		this.camera = map.mid(new Location(dmap.sizeX, dmap.sizeY), new Location(1, 1));
		nbTileY = dmap.sizeY + bufferTile;
		nbTileX = (int) Math.ceil(nbTileY * (double) w / h);
		double tempx = w / dmap.sizeX;
		double tempy = h / (dmap.sizeY + 2);
		this.tileSize = (int) Math.min(Math.ceil(tempy), Math.ceil(tempx));
	}

	public void updateCam(Melee player1, Range player2, int w, int h) {
		if (map instanceof DungeonMap) {
			bossCam(w, h);
		} else {
			this.camera = map.mid(player1.location, player2.location);
			float viewX = map.diffX(player2.location.getX(), player1.location.getX()) + bufferTile;
			float viewY = map.diffY(player2.location.getY(), player1.location.getY()) + bufferTile;
			if (viewX < EntitiesConst.MAX_DIFFX * 2 + bufferTile && viewY < EntitiesConst.MAX_DIFFY * 2 + bufferTile) {
				Location upLeft = new Location((camera.getX() - viewX / 2 + map.lenX) % map.lenX,
						(camera.getY() - viewY / 2 + map.lenY) % map.lenY);
				nbTileX = (int) Math.ceil(map.diffX((float) Math.floor(upLeft.getX()),
						(float) Math.ceil(upLeft.getX() + viewX) % map.lenX)) + 3;
				nbTileY = (int) Math.ceil(map.diffY((float) Math.floor(upLeft.getY()),
						(float) Math.ceil(upLeft.getY() + viewY) % map.lenY)) + 3;
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
		float opa = 1 - (float) (Math.pow(Math.E, -(d * d * 10 / 26)));
		if (opa > 0.90) {
			return 0.99f;
		}
		return opa;
	}

	float closestLight(Location tile) {
		Location player = map.add(game.player1.location, new Location(0.5f, 0.5f));
		float min = map.dist(player, tile);
		for (Torch torch : DungeonMap.torches) {
			if (torch.lit) {
				Location locTorch = map.add(torch.location, new Location(0.5f, 0.5f));
				min = Math.min(min, map.dist(locTorch, tile));
			}
		}
		return min;
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
		Iterator<Effect> it = this.map.effects.iterator();
		while (it.hasNext()) {
			it.next().paint(g, tileSize, roundDeci((this.offset.getX()) * tileSize, 3),
					roundDeci((this.offset.getY()) * tileSize, 3));
		}
	}

	void paintEntity(Graphics g) {
		List<TransparentDecorElement> transparent = new LinkedList<>();
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
					if (EntitiesConst.GAME.debug) {
						renderTile.tpBlock.paint(g, tileSize, roundDeci((i + this.offset.getX()) * tileSize, 3),
								roundDeci((j + this.offset.getY()) * tileSize, 3));
					}
					for (TransparentDecorElement tde : renderTile.tpBlock.target) {
						tde.checkTransparent();
						if (!transparent.contains(tde)) {
							transparent.add(tde);
						}
					}
				}
			}
		}
		Iterator<TransparentDecorElement> it = transparent.iterator();
		while (it.hasNext()) {
			TransparentDecorElement tde = it.next();
			Location l = this.gridToPixel(tde.location, true);
			tde.paint(g, tileSize, l.getX(), l.getY());
		}
	}

	void paintProj(Graphics g) {
		for (Projectile element : EntitiesConst.MAP.projectiles) {
			element.paint(g, tileSize, roundDeci((this.offset.getX()) * tileSize, 3),
					roundDeci((this.offset.getY()) * tileSize, 3));
		}
	}

	void paintBubbles(Graphics g) {
		for (SpeechBubble element : EntitiesConst.MAP.bubbles) {
			element.paint(g, tileSize, roundDeci((this.offset.getX()) * tileSize, 3),
					roundDeci((this.offset.getY()) * tileSize, 3));
		}
	}

	void paintDark(Graphics g, DungeonMap dmap) {
		int resolution = 3;
		for (int j = 0; j < nbTileY; j++) {
			for (int i = 0; i < nbTileX; i++) {
				int mapX = (int) (i + this.camera.getX() + map.lenX - nbTileX / 2) % map.lenX;
				int mapY = (int) (j + this.camera.getY() + map.lenY - nbTileY / 2) % map.lenY;
				for (int sj = 0; sj < resolution; sj++) {
					for (int si = 0; si < resolution; si++) {
						float Xscreen = (i + this.offset.getX() + (float) si / resolution) * tileSize;
						float Yscreen = (j + this.offset.getY() + (float) sj / resolution) * tileSize;
						float d = 2 * resolution;
						Location tile = map.add(new Location(mapX, mapY),
								new Location((float) si / resolution + 1 / d, (float) sj / resolution + 1 / d));
						int opa = (int) (opacity(closestLight(tile)) * 255 * dmap.transiPercent);
						g.setColor(new Color(0, 0, 0, opa));
						int sizeX = 1 + tileSize / resolution;
						int sizeY = 1 + tileSize / resolution;
						g.fillRect((int) Xscreen, (int) Yscreen, sizeX, sizeY);
					}
				}
			}
		}
	}

	public void paint(Graphics g) {
		updateCam(game.player1, game.player2, game.m_canvas.getWidth(), game.m_canvas.getHeight());
		setOffsetCam();
		paintBackground(g);
		paintProj(g);
		paintEntity(g);
		paintEffect(g);
		paintBubbles(g);
		if (map instanceof DungeonMap) {
			DungeonMap dmap = (DungeonMap) map;
			dmap.torchLit();
			if ((!dmap.lit))
				paintDark(g, dmap);
		}
	}

}
