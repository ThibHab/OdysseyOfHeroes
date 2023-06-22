package info3.game.map;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import info3.game.Game;
import info3.game.constants.EntitiesConst;
import info3.game.entity.*;

public abstract class Map implements IMap {
	public Tile[][] map;
	public int lenX, lenY;
	Entity player1, player2;
	public LinkedList<Projectile> projectiles;
	public LinkedList<SpeechBubble> bubbles;

	public Map(int nb_x, int nb_y, Entity p1, Entity p2) {
		this.lenX = nb_x;
		this.lenY = nb_y;
		this.player1 = p1;
		this.player2 = p2;
		this.map = new Tile[lenX][lenY];
		this.projectiles = new LinkedList<>();
		this.bubbles = new LinkedList<>();
	}
	
	void createTree(int x,int y) {
		Tree tr=new Tree(new Location(x,y));
		map[x][y].entity=tr;
		int count=0;
		for(int tr_j=0;tr_j<3;tr_j++) {
			for(int tr_i=0;tr_i<3;tr_i++) {
				int tr_x=(x-1+tr_i+lenX)%lenX;
				int tr_y=(y-2+tr_j+lenY)%lenY;
				if(x!=tr_x || y!=tr_y) {
					if(map[tr_x][tr_y].tpBlock!=null) {
						map[tr_x][tr_y].tpBlock.add(tr);
						tr.liste[count]=map[tr_x][tr_y];
						count++;
					}else {
						TransparencyBlock tb=new TransparencyBlock(tr_x,tr_y);
						tb.add(tr);
						map[tr_x][tr_y].tpBlock=tb;
						tr.liste[count]=map[tr_x][tr_y];
						count++;
					}
				}
			}
		}
	}
	
	void createHouse(int x,int y) {
		House h=new House(new Location(x,y));
		h.hitbox.update();
		int count=0;
		for(int hj=0;hj<3;hj++) {
			for(int hi=0;hi<3;hi++) {
				int hx=(x-1+hi+lenX)%lenX;
				int hy=(y-2+hj+lenY)%lenY;
				if(hj==0) {
					if(map[hx][hy].tpBlock!=null) {
						map[hx][hy].tpBlock.add(h);
						h.liste[count]=map[hx][hy];
						count++;
					}else {
						TransparencyBlock tb=new TransparencyBlock(hx,hy);
						tb.add(h);
						map[hx][hy].tpBlock=tb;
						h.liste[count]=map[hx][hy];
						count++;
					}
				}else {
					map[hx][hy].entity=h;
				}
			}
		}
	}
	
	void createRock(int x, int y) {
		Rock r = new Rock(new Location(x,y));
		map[x][y].entity = r;
	}
	
	void createBush(int x, int y) {
		Bush b = new Bush(new Location(x,y));
		map[x][y].entity = b;
	}

	/**
	 * Generate randomly with a chosen space between the chosen entity.
	 * 
	 * @param rareness     the bigger the less entity it creates
	 * @param seed         to set the random
	 * @param spaceBetween the distance between two entity
	 */
	public void setEntityRandomly(int x, int y, int areaSize, int spaceBetween, String ent, long seed, int rareness) {
		if (!(ent.equals("Bush")) && !(ent.equals("Rock")) && !(ent.equals("Tree"))) {
			return;
		}
		Random r = new Random(seed);
		for (int i = x; i < x + areaSize; i++) {
			for (int j = y; j < y + areaSize; j++) {
				if (map[i][j].entity == null && !(map[i][j] instanceof WaterTile) && !(map[i][j] instanceof DirtTile)) {
					int n = r.nextInt(rareness);
					if (n == 1) {
						boolean already = false;
						int rec_x = i - spaceBetween;
						int rec_y = j - spaceBetween;
						while (rec_x < 0) {
							while (rec_y < 0) {
								if (map[lenX + rec_x][lenY + rec_y].entity != null) {
									already = true;
								}
								rec_y++;
							}
							while (rec_y <= j + spaceBetween) {
								if (map[lenX + rec_x][rec_y].entity != null) {
									already = true;
								}
								rec_y++;
							}
							rec_x++;
						}
						while (rec_x <= i + spaceBetween) {
							rec_y = j - spaceBetween;
							while (rec_y < 0) {
								if (map[rec_x][lenY + rec_y].entity != null) {
									already = true;
								}
								rec_y++;
							}
							while (rec_y <= j + spaceBetween) {
								if (map[rec_x][rec_y].entity != null) {
									already = true;
								}
								rec_y++;
							}
							rec_x++;
						}
						if (!already) {
							if (ent.equals("Bush")) {
								this.createBush(i, j);
							} else if (ent.equals("Rock")) {
								this.createRock(i, j);
							} else {
								createTree(i,j);
							}
						}
					}
				}
			}
		}
	}

	public void setSurfaceBackground(int x, int y, int width, int height, String tile) {
		for (int i = x; i < x + width; i++) {
			for (int j = y; j < y + height; j++) {
				Location l = new Location(j, j);
				if (tile.equals("Water")) {
					map[i][j] = new WaterTile(l);
				} else if (tile.equals("Rock")) {
					map[i][j] = new RockTile(l);
				} else if (tile.equals("Grass")) {
					map[i][j] = new GrassTile(l);
				} else {
					map[i][j] = new DirtTile(l);
				}
			}
		}
	}

	boolean checkinradius(float x, float y, int c_x, int c_y, float radius) {
		return (x - c_x) * (x - c_x) + (y - c_y) * (y - c_y) < radius * radius;
	}

	public void setCircleWaterBackground(int x, int y, int radius) {
		for (int i = x - (2 * radius); i < x + (2 * radius); i++) {
			for (int j = y - (2 * radius); j < y + (2 * radius); j++) {
				if (checkinradius(i, j, x, y, radius + 0.3f)) {
					Location l = new Location(j, j);
					map[i][j] = new WaterTile(l);
				}
			}
		}
	}

	public void setVillage(int x, int y, int areaSize) {
		setSurfaceBackground(x, y, areaSize, 1, "Dirt");
		setSurfaceBackground(x + 2, y + 4, areaSize - 3, 1, "Dirt");
		setSurfaceBackground(x + (areaSize / 2), y, 1, areaSize, "Dirt");
		for (int indexHouse = x + 2; indexHouse < areaSize; indexHouse += 4) {
			setSurfaceBackground(indexHouse, y - 1, 1, 1, "Dirt");
		}
		int i = x;
		while (i < areaSize) {
			Location l = new Location(i+2, y - 2);
			Location l2 = new Location(i+2, y + 3);

			createHouse((int)l.getX(),(int)l.getY());
			createHouse((int)l2.getX(),(int)l2.getY());
			
			i += 4;
		}
	}
	
	public void tickEntities(int x, int y, long elapsed) {
		MapRender rend = EntitiesConst.GAME.render;
		int nbTileY = rend.nbTileY + 4;
		int nbTileX = rend.nbTileX + 4;
		int tabSize = 50;
		Entity tab[] = new Entity[tabSize];
		int indexTab = 0;
		boolean alreadyTicked = false;
		
		for (int j = 0; j < nbTileY; j++) {
			for (int i = 0; i < nbTileX; i++) {
				int mapX = (int) (i + rend.camera.getX() + lenX - nbTileX / 2) % lenX;
				int mapY = (int) (j + rend.camera.getY() + lenY - nbTileY / 2) % lenY;
				Tile renderTile = map[mapX][mapY];
				Entity ent = renderTile.entity;
				if (ent != null) {
					for (int k = 0; k < indexTab; k++) {
						if (ent == tab[k]) {
							alreadyTicked = true;
						}
					}
					if (!alreadyTicked) {
						if (ent instanceof House || ent instanceof Tree || ent instanceof Hero) {
							tab[indexTab++] = ent;
						}
						ent.tick(elapsed);
					}
					alreadyTicked = false;
				}
			}
		}
	}
}
