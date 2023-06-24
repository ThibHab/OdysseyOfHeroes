package info3.game.map;

import java.util.Random;

import info3.game.Game;
import info3.game.constants.EntitiesConst;
import info3.game.constants.MapConstants;
import info3.game.entity.MazeWall;
import info3.game.entity.Chest;
import info3.game.entity.Entity;
import info3.game.entity.Goblin;
import info3.game.entity.Location;

public class MazeMap extends Map {
	public Maze maze;
	public boolean mazeCounterActivated;
	public int mazeCounter;
	
	public MazeMap(int nb_x, int nb_y, Entity p1, Entity p2) {
		super(nb_x, nb_y, p1, p2);
		this.maze = new Maze(MapConstants.MAZE_MAP_SIZE);		
		boolean[][] mazeMatrix = this.maze.get_matrice(MapConstants.MAZE_MAP_CORRIDOR_SIZE);
		int nbChestSpawned = 0;
		for (int row = 0; row < mazeMatrix.length; row++) {
			for (int column = 0; column < mazeMatrix[row].length; column++) {
				Location location = new Location(row, column);
				this.map[row][column] = new DirtTile(location);
				if (mazeMatrix[row][column]) {
					this.map[row][column].entity = new MazeWall(location);
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
		
		//this.maze.pretty_print(MapConstants.MAZE_MAP_CORRIDOR_SIZE);
		
		this.setPlayer(1, 1, p1);
		this.setPlayer(1, 3, p2);
	
		EntitiesConst.MAP = this;
		EntitiesConst.MAP_MATRIX = this.map;
	}
	
	public void tick(long elapsed) {
		if (this.mazeCounterActivated) {
			this.mazeCounter += elapsed;
			if (this.mazeCounter >= EntitiesConst.MAZE_COUNTER_LIMIT) {
				EntitiesConst.GAME.openMap(Game.WORLD);
				this.mazeCounterActivated = false;
				this.mazeCounter = 0;
			}
		}
	}
}
