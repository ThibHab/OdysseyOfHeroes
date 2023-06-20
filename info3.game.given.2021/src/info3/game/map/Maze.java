package info3.game.map;

import java.util.Random;

public class Maze {
	private Maze_tile tiles[][];
	private boolean n_s[][];
	private boolean e_w[][];
	private int nb_tile;

	public final class Maze_tile {
		private Maze_tile[] neighbors;
		private int idx;

		public Maze_tile(int size) {
			neighbors = new Maze_tile[size * size + 1]; //array of the connected tiles
			idx = 0;
			neighbors[idx++] = this;
		}

		public int get_idx() {
			return idx;
		}
		
		public void add_neighbors(Maze_tile[] n, int length) {
			for (int i = 0; i < length; i++) { 
				neighbors[idx++] = n[i];
			}
		}
		

		public boolean is_neighbor(Maze_tile t) {
			for (int i = 0; i < idx; i++) {
				if (neighbors[i].equals(t))
					return true;
			}
			return false;
		}

		public void set_neighbors(Maze_tile[] n, int length) {
			for (int i = 0; i < length; i++)
				neighbors = n;
			idx = length;
		}

		public Maze_tile[] get_neighbors() {
			return neighbors;
		}
	}

	public Maze(int size) {
		nb_tile = size;
		tiles = new Maze_tile[nb_tile][nb_tile];
		e_w = new boolean[nb_tile - 1][nb_tile];
		n_s = new boolean[nb_tile][nb_tile - 1];
		for (int i = 0; i < nb_tile; i++) {
			for (int j = 0; j < nb_tile; j++) {
				tiles[i][j] = new Maze_tile(nb_tile);
				if (i != nb_tile - 1) {
					e_w[i][j] = true;
				}
				if (j != nb_tile - 1) {
					n_s[i][j] = true;
				}
			}
		}
		this.generate_labyrinthe();
	}

	private boolean is_complete() {
		if (tiles[0][0].get_idx() == nb_tile * nb_tile)
			return true;
		return false;
	}

	private void generate_labyrinthe() {
		Random rand = new Random();
		boolean destroy = false;
		int x, y;

		while (!this.is_complete()) {
			Maze_tile tile_2 = null, tile_1 = null;
			int axis = rand.nextInt(2);

			if (axis == 0) { // tile n_s
				x = rand.nextInt(nb_tile);
				y = rand.nextInt(nb_tile - 1);
				if (n_s[x][y]) {
					tile_2 = tiles[x][y];
					tile_1 = tiles[x][y + 1];
					if (!tile_2.is_neighbor(tile_1)) {
						n_s[x][y] = false;
						destroy = true;
					}
				}
			} else { // tile e_w
				x = rand.nextInt(nb_tile - 1);
				y = rand.nextInt(nb_tile);
				if (e_w[x][y]) {
					tile_2 = tiles[x][y];
					tile_1 = tiles[x + 1][y];
					if (!tile_2.is_neighbor(tile_1)) {
						e_w[x][y] = false;
						destroy = true;
					}
				}
			}
			if (destroy) {
				tile_2.add_neighbors(tile_1.get_neighbors(), tile_1.get_idx());
				Maze_tile[] parcours = tile_2.get_neighbors();
				int idx = tile_2.get_idx();
				for (int i = 0; i < idx; i++) {
					parcours[i].set_neighbors(parcours, idx);
				}
				destroy = false;
			}
		}
	}

	public boolean[][] get_matrice(int width) {
		boolean[][] res = new boolean[nb_tile * (width + 1) + 1][nb_tile * (width + 1) + 1];
		for (int i = 0; i < nb_tile * (width + 1) + 1; i++) {
			for (int j = 0; j < nb_tile * (width + 1) + 1; j++) {
				if (i == 0 || j == 0 || i == nb_tile * (width + 1) || j == nb_tile * (width + 1)) { // only if borders
					res[i][j] = true;
				} else if (j % (width + 1) == 0 && i % (width + 1) == 0) { // only if walls junction
					res[i][j] = true;
				} else if (i % (width + 1) == 0) { // only if a wall e_w
					res[i][j] = e_w[i / (width + 1) - 1][j / (width + 1)];
				} else if (j % (width + 1) == 0) { // only if a wall n_s
					res[i][j] = n_s[i / (width + 1)][j / (width + 1) - 1];
				} else { // only if interior
					res[i][j] = false;
				}
			}
		}
		return res;
	}

	public void pretty_print(int width) {
		boolean[][] res = this.get_matrice(width);
		for (int i = 0; i < this.nb_tile * (width + 1) + 1; i++) {
			for (int j = 0; j < this.nb_tile * (width + 1) + 1; j++) {
				if (res[i][j])
					System.out.print("██");
				else
					System.out.print("  ");
			}
			System.out.print("\n");
		}
	}

	public static void main(String[] args) {
		int nb_case = 20, print_size = 3; // modify these values to change the maze
		Maze m = new Maze(nb_case);
		m.pretty_print(print_size);
	}
}
