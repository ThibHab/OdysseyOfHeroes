package info3.game.map;

import java.awt.Graphics;
import java.util.Random;

import info3.game.constants.EntitiesConst;
import info3.game.constants.MapConstants;
import info3.game.entity.CaveWall;
import info3.game.entity.Chest;
import info3.game.entity.Entity;
import info3.game.entity.Goblin;
import info3.game.entity.Location;

public class MazeMap extends Map {
	public Maze maze;
	
	public MazeMap(int nb_x, int nb_y, Entity p1, Entity p2) {
		super(nb_x, nb_y, p1, p2);
		this.maze = new Maze(MapConstants.MAZE_MAP_SIZE);
		
		Location lp1 = new Location(1, 1);
		Location lp2 = new Location(1, 2);
		this.player1.location.setX(lp1.getX());
		this.player1.location.setY(lp1.getY());
		this.player2.location.setX(lp2.getX());
		this.player2.location.setY(lp2.getY());
		this.player1.hitbox.update();
		this.player2.hitbox.update();
		
		boolean[][] mazeMatrix = this.maze.get_matrice(MapConstants.MAZE_MAP_CORRIDOR_SIZE);
		int nbChestSpawned = 0;
		for (int row = 0; row < mazeMatrix.length; row++) {
			for (int column = 0; column < mazeMatrix[row].length; column++) {
				Location location = new Location(row, column);
				this.map[row][column] = new DirtTile(location);
				if (mazeMatrix[row][column]) {
					this.map[row][column].entity = new CaveWall(location);
				} else {
					Random random = new Random();
					int randomSpawn = random.nextInt(100);
					if (randomSpawn < 3) {
						this.map[row][column].entity = new Goblin(location);
					}
					else  if (nbChestSpawned < 10 && randomSpawn >= 5 && randomSpawn < 10) {
						this.map[row][column].entity = new Chest(location);
						nbChestSpawned++;
					}
				}
			}
		}
		
		map[(int)lp1.getX()][(int)lp1.getY()].entity = player1;
		map[(int)lp2.getX()][(int)lp2.getY()].entity = player2;
		
		this.maze.pretty_print(MapConstants.MAZE_MAP_CORRIDOR_SIZE);
		
		EntitiesConst.MAP = this;
		EntitiesConst.MAP_MATRIX = this.map;
	}
}
