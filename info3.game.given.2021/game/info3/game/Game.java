/*
 * Copyright (C) 2020  Pr. Olivier Gruber
 * Educational software for a basic game development
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  Created on: March, 2020
 *      Author: Pr. Olivier Gruber
 */
package info3.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;

import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Direction;
import info3.game.automata.ast.AST;
import info3.game.automata.ast.AutCreator;
import info3.game.automata.ast.IVisitor;
import info3.game.automata.parser.AutomataParser;
import info3.game.constants.Action;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;
import info3.game.constants.MapConstants;
import info3.game.entity.Boss;
import info3.game.entity.Bush;
import info3.game.entity.Entity;
import info3.game.entity.Hero;
import info3.game.entity.Location;
import info3.game.entity.Melee;
import info3.game.entity.Miner;
import info3.game.entity.Range;
import info3.game.entity.Rock;
import info3.game.entity.Tree;
import info3.game.entity.VillagerGirl;
import info3.game.graphics.GameCanvas;
import info3.game.hud.HudInGame;
import info3.game.hud.InGameMenu;
import info3.game.hud.Menu;
import info3.game.map.DungeonMap;
import info3.game.map.IMap;
import info3.game.map.Map;
import info3.game.map.MapRender;
import info3.game.map.MazeMap;
import info3.game.map.SaveTile;
import info3.game.map.WorldMap;
import info3.game.sound.RandomFileInputStream;

public class Game {

	static Game game;
	static boolean newGame;
	public static final int WORLD = 0;
	public static final int MAZE = 1;
	public static final int BOSS = 2;

	public static void main(String args[]) throws Exception {
		try {
			System.out.println("Game starting...");
			game = new Game();
			System.out.println("Game started.");
		} catch (Throwable th) {
			th.printStackTrace(System.err);
		}
	}

	public boolean debug = false;
	public JFrame m_frame;
	JLabel m_text;
	public GameCanvas m_canvas;
	public CanvasListener m_listener;
	public Melee player1;
	public Range player2;
	Sound m_music;
	public IMap map;
	public int previousMap;
	public MapRender render;
	public List<Aut_Automaton> listAutomata;
	public HudInGame hud;
	public Menu menu;
	public InGameMenu inMenu;
	public RandomAccessFile save;
	public boolean reload;
	public boolean saveExist;
	public boolean showMap;

	Game() throws Exception {
		File f = new File("save.txt");
		long length = f.length();
		if (length == 0) {
			saveExist = false;
		} else {
			saveExist = true;
		}

		new ImagesConst();
		new EntitiesConst();
		EntitiesConst.GAME = this;

		m_listener = new CanvasListener(this);
		m_canvas = new GameCanvas(m_listener);

		System.out.println("  - creating frame...");
		Rectangle rec = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		Dimension d = new Dimension((int) rec.getWidth(), (int) rec.getHeight());
		m_frame = m_canvas.createFrame(d);
		m_frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		m_frame.setResizable(false);
		m_frame.setUndecorated(true);
		m_frame.setIconImage(null);

		System.out.println("  - setting up the frame...");

		m_frame.setIconImage(ImagesConst.LOGO);
		m_frame.setName("Odyssey of Heroes");

		setupFrame();

		setupMenu();
	}

	public void getAutomata() throws Exception {
		File f = new File("config.txt");
		if (!f.exists() || f.length() == 0) {
			throw new Exception("Configuration file not found");
		}
		RandomAccessFile config = new RandomAccessFile(f, "r");
		byte[] buffer = new byte[(int) config.length()];
		config.readFully(buffer);
		String s = new String(buffer);
		String[] configFile = s.split("\n");
		String[][] configData = new String[configFile.length][2];
		for (int i = 0; i < configFile.length; i++) {
			String[] line = configFile[i].split(" = ");
			configData[i][0] = line[0];
			configData[i][1] = line[1];
		}
		config.close();

		IVisitor visitor = new AutCreator();
		AST ast = (AST) AutomataParser.from_file("resources/t.gal");
		List<Aut_Automaton> listAut = (List<Aut_Automaton>) ast.accept(visitor);
		for (int i = 0; i < configFile.length; i++) {
			for (Aut_Automaton aut : listAut) {
				if (aut.name.equals(configData[i][1])) {
					Aut_Automaton automata = aut.clone();
					automata.name = configData[i][0];
					listAutomata.add(automata);
					break;
				}
			}
		}
	}

	public void setupMenu() {
		menu = new Menu(m_frame);
		menu.setMenuSize();
		menu.setMenu();
	}

	public void setupGame(File file) throws Exception {
		m_frame.setCursor(m_frame.getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB),
				new Point(0, 0), "null"));
		byte[] buffer = null;
		if (file == null) {
			file = new File("save.txt");
			save = new RandomAccessFile(file, "rw");
			Random r = new Random();
			EntitiesConst.SEED = r.nextInt();
			reload = false;
		} else {
			reload = true;
		}

		if (!file.exists()) {
			file.createNewFile();
		}
		save = new RandomAccessFile(file, "rw");

		if (reload) {
			buffer = new byte[(int) save.length()];
			save.readFully(buffer);
			loadSeed(buffer);
		}

		listAutomata = new LinkedList<Aut_Automaton>();
		getAutomata();

		player1 = new Melee("Player1", this);
		player1.name = "player1";
		player2 = new Range("Player2", this);
		player2.name = "player2";

		EntitiesConst.MAP = null;
		MapConstants.WORLD_MAP = new WorldMap(100, 100, EntitiesConst.GAME.player1, EntitiesConst.GAME.player2);
		map = MapConstants.WORLD_MAP;
		// map=new DebugMap(40,40,player1,player2);
		render = new MapRender((Map) map, this);

		int level = 0, xp = 0;
		Entity.InitStatics(this, level, xp);

		hud = new HudInGame(m_frame);

		if (reload) {
			this.reload(buffer);
		}

		render.updateCam(player1, player2, m_canvas.getWidth(), m_canvas.getHeight());
		render.setOffsetCam();
		inMenu = new InGameMenu(m_frame);
		inMenu.setMenuSize();
		inMenu.setMenu();
	}

	/*
	 * Then it lays out the frame, with a border layout, adding a label to the north
	 * and the game canvas to the center.
	 */
	private void setupFrame() {
		Toolkit tkit = Toolkit.getDefaultToolkit();
		Point point = new Point(25, 25);
		Image agrou = ImagesConst.CURSOR[0];
		Cursor curs = tkit.createCustomCursor(agrou, point, "AgrouCurs");
		m_frame.setCursor(curs);
		m_frame.setTitle("Odyssey of Heroes");
		m_frame.setLayout(new BorderLayout());

		m_frame.add(m_canvas, BorderLayout.CENTER);

		if (EntitiesConst.GAME.debug) {
			m_text = new JLabel();
			m_text.setText("Tick: 0ms FPS=0");
			m_frame.add(m_text, BorderLayout.NORTH);
		}

		// center the window on the screen
		m_frame.setLocationRelativeTo(null);

		// make the vindow visible
		m_frame.setVisible(true);
	}

	/*
	 * ================================================================ All the
	 * methods below are invoked from the GameCanvas listener, once the window is
	 * visible on the screen.
	 * ==============================================================
	 */

	/*
	 * Called from the GameCanvas listener when the frame
	 */
	String m_musicName;

	void loadMusic() {
		m_musicName = m_musicNames[m_musicIndex];
		String filename = "resources/" + m_musicName + ".ogg";
		m_musicIndex = (m_musicIndex + 1) % m_musicNames.length;
		try {
			RandomAccessFile file = new RandomAccessFile(filename, "r");
			RandomFileInputStream fis = new RandomFileInputStream(file);
			m_canvas.playMusic(fis, 0, 0.65F);
		} catch (Throwable th) {
			th.printStackTrace(System.err);
			System.exit(-1);
		}
	}

	private int m_musicIndex = 0;
	private String[] m_musicNames = new String[] { "theme", "dungeon-theme" };

	private long m_textElapsed;
	private long m_deadTextElapsed;
	private long m_tryEnterDungeon;
	private long m_victoiry;
	public boolean paintDead;
	public boolean endGameFreeze;
	public boolean victory_message;

	/*
	 * This method is invoked almost periodically, given the number of milli-seconds
	 * that elapsed since the last time this method was invoked.
	 */
	void tick(long elapsed) {
		try {
			if (menu.getStarted()) {
				for (int i = 0; i < EntitiesConst.MAP.projectiles.size(); i++) {
					EntitiesConst.MAP.projectiles.get(i).tick(elapsed);
				}

				for (Bush b : EntitiesConst.MAP.deadBush) {
					b.tick(elapsed);
				}
				((Map) map).tickEntities((int) render.camera.getX(), (int) render.camera.getY(), elapsed);
				((Map) map).tickEffects(elapsed);

				if ((player1.action == Action.D && player2.action == Action.D) || EntitiesConst.MAP instanceof MazeMap
						&& ((player1.action == Action.D && player2.healingPotions <= 0)
								|| (player2.action == Action.D && player1.healingPotions <= 0))) {
					gameOVER();
				}
				m_deadTextElapsed += elapsed;
				if (m_deadTextElapsed > 2000) {
					paintDead = false;
				}

				if (Hero.tryToEnterDungeon) {
					m_tryEnterDungeon += elapsed;
					if (m_tryEnterDungeon > 2000) {
						Hero.tryToEnterDungeon = false;
						m_tryEnterDungeon = 0;
					}
				}

				if (map instanceof DungeonMap && DungeonMap.initLit && Boss.h <= 0 && menu.getStarted()) {
					gameWin(elapsed);
				}

				if (EntitiesConst.GAME.debug) {
					m_textElapsed += elapsed;
					if (m_textElapsed > 100) {
						m_textElapsed = 0;
						float period = m_canvas.getTickPeriod();
						int fps = m_canvas.getFPS();

						String txt = "Tick=" + period + "ms";
						while (txt.length() < 15)
							txt += " ";
						txt = txt + fps + " fps   ";
						txt = txt + "P1:" + player1.location.getX() + ";" + player1.location.getY() + "     ";
						txt = txt + "P2:" + player2.location.getX() + ";" + player2.location.getY() + "     ";
						txt = txt + "Cam:" + render.camera.getX() + ";" + render.camera.getY() + "     ";
						txt = txt + "offset" + render.offset.getX() + ";" + render.offset.getY() + "     ";
						m_text.setText(txt);
					}
				}
			}
		} catch (Exception e) {
		}
		// Update every second
		// the text on top of the frame: tick and fps
	}

	/*
	 * This request is to paint the Game Canvas, using the given graphics. This is
	 * called from the GameCanvasListener, called from the GameCanvas.
	 */
	void paint(Graphics g) {

		// get the size of the canvas
		int width = m_canvas.getWidth();
		int height = m_canvas.getHeight();

		// erase background
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);

		if (menu.getStarted()) {
			render.paint(g);
			hud.paint(g);
			if (inMenu.getPause()) {
				inMenu.paint(g);
			} else if (showMap) {
				Image miniMap = ImagesConst.MINIMAP_PICTURE;
				g.drawImage(miniMap, 0, 0, width, height, null);
			}
		} else {
			menu.paint(g);
		}
	}

	void gameOVER() throws Exception {
		Map m = ((Map) this.map);
		if (m instanceof WorldMap) {
			try {
				RandomAccessFile file = new RandomAccessFile("resources/sounds/gameOver.ogg", "r");
				RandomFileInputStream fis = new RandomFileInputStream(file);
				EntitiesConst.GAME.m_canvas.playSound("gameOver", fis, 0, 0.8F);
			} catch (Throwable th) {
				th.printStackTrace(System.err);
				System.exit(-1);
			}

			File f = new File("save.txt");
			long length = f.length();
			if (!f.exists() || length == 0) {
				this.setupGame(null);
			} else {
				RandomAccessFile raf = new RandomAccessFile(f, "r");
				byte[] buffer = new byte[(int) raf.length()];
				raf.readFully(buffer);
				String[] data = new String(buffer).split("\n");
				int seed = Integer.valueOf(data[0]); // restore seed
				if (seed == EntitiesConst.SEED) {
					this.setupGame(f);
				} else {
					this.setupGame(null);
				}
			}
		}
		if (m instanceof DungeonMap || m instanceof MazeMap) {
			Hero.bombs = 0;
			Hero.coins = 0;
			player1.healingPotions = 0;
			player2.healingPotions = 0;
			player1.revive();
			player2.revive();
			player1.direction = Aut_Direction.S;
			player2.direction = Aut_Direction.S;
			this.openMap(WORLD);
			if (m instanceof DungeonMap) {
				DungeonMap.finish = false;
			}
			paintDead = true;
			m_deadTextElapsed = 0;

		}
	}

	void gameWin(long elapsed) {
		endGameFreeze = true;
		victory_message = true;

		if (endGameFreeze) {
			m_victoiry += elapsed;
			if (m_victoiry > 5000) {
				victory_message = false;
			}
		}
		if (victory_message == false) {
			menu.setStarted();
			Toolkit tkit = Toolkit.getDefaultToolkit();
			Point point = new Point(25, 25);
			Image agrou = ImagesConst.CURSOR[0];
			Cursor curs = tkit.createCustomCursor(agrou, point, "AgrouCurs");
			m_frame.setCursor(curs);
			EntitiesConst.GAME.map = null;
			endGameFreeze = false;
			m_victoiry = 0;
			this.m_musicIndex = 0;
			this.loadMusic();
		}
	}

	public void save() {
		if (EntitiesConst.MAP instanceof WorldMap
				&& EntitiesConst.MAP_MATRIX[(int) player1.location.getX()][(int) player1.location
						.getY()] instanceof SaveTile
				&& EntitiesConst.MAP_MATRIX[(int) player2.location.getX()][(int) player2.location
						.getY()] instanceof SaveTile) {
			String data = "";
			data += EntitiesConst.SEED + "\n";

			data += player1.location.getX() + "/" + player1.location.getY() + "/" + player1.currentState.name + "/"
					+ player1.health + "/" + player1.maxHealth + "/" + player1.healingPotions + "/"
					+ player1.weaponDamage + "/" + player1.direction + "\n";
			data += player2.location.getX() + "/" + player2.location.getY() + "/" + player2.currentState.name + "/"
					+ player2.health + "/" + player2.maxHealth + "/" + player2.healingPotions + "/"
					+ player2.weaponDamage + "/" + player2.weaponRange + "/" + player2.direction + "\n";

			data += Hero.level + "/" + Hero.experience + "/" + Hero.coins + "/" + Hero.bombs + "/" + Hero.bushesCut
					+ "\n";

			Location rock = EntitiesConst.MAP.rockLoc;
			boolean forestUnlocked = !(EntitiesConst.MAP_MATRIX[(int) rock.getX()][(int) rock
					.getY()].entity instanceof Rock);
			boolean firePower = Hero.firePowerUnlocked;

			data += forestUnlocked + "/" + firePower + "\n";

			data += VillagerGirl.started + "/" + VillagerGirl.completed + "\n";

			data += Miner.sold;

			byte[] buffer = data.getBytes();
			try {
				save.seek(0);
				save.write(buffer);
			} catch (IOException e) {
				e.printStackTrace();
			}

			SaveTile tileP1 = (SaveTile) EntitiesConst.MAP_MATRIX[(int) player1.location.getX()][(int) player1.location
					.getY()];
			SaveTile tileP2 = (SaveTile) EntitiesConst.MAP_MATRIX[(int) player2.location.getX()][(int) player2.location
					.getY()];
			tileP1.changeTile(true);
			tileP2.changeTile(true);
		}
	}

	public void loadSeed(byte[] buffer) {
		String[] data = new String(buffer).split("\n");
		EntitiesConst.SEED = Integer.valueOf(data[0]); // restore seed

	}

	public void reload(byte[] buffer) {
		String[] data = new String(buffer).split("\n");

		String[] p1 = data[1].split("/");
		String[] p2 = data[2].split("/");
		String[] hero = data[3].split("/");
		String[] game = data[4].split("/");
		String[] villagerGirl = data[5].split("/");
		String[] miner = data[6].split("/");

		Location loc1 = new Location(Float.valueOf(p1[0]), Float.valueOf(p1[1]));
		Location loc2 = new Location(Float.valueOf(p2[0]), Float.valueOf(p2[1]));
		Aut_Direction dir1 = Aut_Direction.valueOf(p1[7]);
		Aut_Direction dir2 = Aut_Direction.valueOf(p2[8]);

		player1.saveRestore(loc1, p1[2], Integer.valueOf(p1[3]), Integer.valueOf(p1[4]), Integer.valueOf(p1[5]),
				Integer.valueOf(p1[6]), 0, dir1);
		player2.saveRestore(loc2, p2[2], Integer.valueOf(p2[3]), Integer.valueOf(p2[4]), Integer.valueOf(p2[5]),
				Integer.valueOf(p2[6]), Integer.valueOf(p2[7]), dir2);

		Hero.restore(Integer.valueOf(hero[0]), Integer.valueOf(hero[1]), Integer.valueOf(hero[2]),
				Integer.valueOf(hero[3]), Integer.valueOf(hero[4]));

		boolean forest = Boolean.valueOf(game[0]);
		boolean fire = Boolean.valueOf(game[1]);
		if (forest) {
			Location rock = EntitiesConst.MAP.rockLoc;
			EntitiesConst.MAP_MATRIX[(int) rock.getX()][(int) rock.getY()].entity = null;
		}
		if (fire) {
			Range.unlockFire();
		} else {
			Hero.firePowerUnlocked = false;
			EntitiesConst.MAP.setDungeonEntrance(EntitiesConst.DUNGEON_ENTRANCE_X_POS,
					EntitiesConst.DUNGEON_ENTRANCE_Y_POS);
		}

		VillagerGirl.started = Boolean.valueOf(villagerGirl[0]);
		VillagerGirl.completed = Boolean.valueOf(villagerGirl[1]);

		Miner.sold = Boolean.valueOf(miner[0]);

		WorldMap.saveTile1.changeTile(true);
		WorldMap.saveTile2.changeTile(true);
	}

	public void unsave() {
		File f = new File("save.txt");
		f.delete();
	}

	void doublePlayerPlace(int i, int j) {
		int x = i - 1;
		int y = j + 1;
		if (EntitiesConst.MAP_MATRIX[x][y].entity instanceof Tree) {
			EntitiesConst.MAP.delTree(x, y);
		}
		EntitiesConst.MAP.setPlayer(x, y, EntitiesConst.GAME.player1);
		x = i + 1;
		if (EntitiesConst.MAP_MATRIX[x][y].entity instanceof Tree) {
			EntitiesConst.MAP.delTree(x, y);
		}
		EntitiesConst.MAP.setPlayer(x, y, EntitiesConst.GAME.player2);
	}

	void removePlayer() {
		EntitiesConst.MAP_MATRIX[(int) EntitiesConst.GAME.player1.location
				.getX()][(int) EntitiesConst.GAME.player1.location.getY()].entity = null;
		if (EntitiesConst.MAP_MATRIX[(int) EntitiesConst.GAME.player1.destLocation
				.getX()][(int) EntitiesConst.GAME.player1.destLocation.getY()].entity instanceof Hero) {
			EntitiesConst.MAP_MATRIX[(int) EntitiesConst.GAME.player1.destLocation
					.getX()][(int) EntitiesConst.GAME.player1.destLocation.getY()].entity = null;
		}
		EntitiesConst.MAP_MATRIX[(int) EntitiesConst.GAME.player2.location
				.getX()][(int) EntitiesConst.GAME.player2.location.getY()].entity = null;
		if (EntitiesConst.MAP_MATRIX[(int) EntitiesConst.GAME.player2.destLocation
				.getX()][(int) EntitiesConst.GAME.player2.destLocation.getY()].entity instanceof Hero) {
			EntitiesConst.MAP_MATRIX[(int) EntitiesConst.GAME.player2.destLocation
					.getX()][(int) EntitiesConst.GAME.player2.destLocation.getY()].entity = null;
		}
	}

	public void openMap(int map) {
		removePlayer();
		switch (map) {
		case WORLD:
			Map previous = (Map) EntitiesConst.GAME.map;
			EntitiesConst.GAME.map = MapConstants.WORLD_MAP;
			EntitiesConst.MAP = (Map) EntitiesConst.GAME.map;
			EntitiesConst.MAP_MATRIX = EntitiesConst.MAP.map;
			if (previous instanceof MazeMap) {
				doublePlayerPlace(EntitiesConst.MAZE_ENTRANCE_X_POS, EntitiesConst.MAZE_ENTRANCE_Y_POS);
			} else {
				doublePlayerPlace(EntitiesConst.DUNGEON_ENTRANCE_X_POS, EntitiesConst.DUNGEON_ENTRANCE_Y_POS);
			}
			this.m_musicIndex = 0;
			this.loadMusic();
			break;
		case MAZE:
			MazeMap mm = new MazeMap(MapConstants.MAZE_MAP_SIZE * (MapConstants.MAZE_MAP_CORRIDOR_SIZE + 1) + 1,
					MapConstants.MAZE_MAP_SIZE * (MapConstants.MAZE_MAP_CORRIDOR_SIZE + 1) + 1,
					EntitiesConst.GAME.player1, EntitiesConst.GAME.player2);
			EntitiesConst.GAME.map = mm;
			EntitiesConst.MAP = (Map) EntitiesConst.GAME.map;
			EntitiesConst.MAP_MATRIX = EntitiesConst.MAP.map;
			mm.mazeCounterActivated = true;
			break;
		case BOSS:
			EntitiesConst.GAME.map = new DungeonMap(40, 40, EntitiesConst.GAME.player1, EntitiesConst.GAME.player2);
			EntitiesConst.MAP = (Map) EntitiesConst.GAME.map;
			EntitiesConst.MAP_MATRIX = EntitiesConst.MAP.map;
			this.m_musicIndex = 1;
			this.loadMusic();
			break;
		}
		EntitiesConst.GAME.render = new MapRender(EntitiesConst.MAP, EntitiesConst.GAME);
		EntitiesConst.GAME.render.updateCam(EntitiesConst.GAME.player1, EntitiesConst.GAME.player2,
				EntitiesConst.GAME.m_canvas.getWidth(), EntitiesConst.GAME.m_canvas.getHeight());
		EntitiesConst.GAME.render.setOffsetCam();
	}

}
