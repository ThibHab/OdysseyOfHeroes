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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;

import info3.game.automata.*;
import info3.game.automata.ast.AST;
import info3.game.automata.ast.AutCreator;
import info3.game.automata.ast.IVisitor;
import info3.game.automata.parser.AutomataParser;
import info3.game.constants.Action;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;
import info3.game.constants.MapConstants;
import info3.game.entity.*;
import info3.game.graphics.GameCanvas;
import info3.game.hud.HudInGame;
import info3.game.hud.InGameMenu;
import info3.game.hud.Menu;
import info3.game.map.DebugMap;
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

	public static void main(String args[]) throws Exception {
		try {
			System.out.println("Game starting...");
//			newGame = false;
//			if (newGame)
//				game = new Game(null);
//			else {
//				File f = new File("save.txt");
//				long length = f.length();
//				if (length == 0)
//					game = new Game(null);
//				else
//					game = new Game(new File("save.txt"));
//			}
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
	Cowboy m_cowboy;
	public Melee player1;
	public Range player2;
	Sound m_music;
	public IMap map;
	public MapRender render;
	public List<Aut_Automaton> listAutomata;
	public HudInGame hud;
	public Menu menu;
	public InGameMenu inMenu;
	public RandomAccessFile save;
	boolean reload;

	Game() throws Exception {
		// creating a cowboy, that would be a model
		// in an Model-View-Controller pattern (MVC)
//		m_cowboy = new Cowboy(this);
//		byte[] buffer = null;
//		if (file == null) {
//			file = new File("save.txt");
//			save = new RandomAccessFile(file, "rw");
//			Random r = new Random();
//			EntitiesConst.SEED = r.nextInt();
//			reload = false;
//		} else {
//			reload = true;
//		}
//
//		if (!file.exists()) {
//			file.createNewFile();
//		}
//		save = new RandomAccessFile(file, "rw");
//
//		if (reload) {
//			buffer = new byte[(int) save.length()];
//			save.readFully(buffer);
//			loadSeed(buffer);
//		}

		new ImagesConst();
		new EntitiesConst();
		EntitiesConst.GAME = this;

		IVisitor visitor = new AutCreator();
		AST ast = (AST) AutomataParser.from_file("resources/t.gal");
		listAutomata = (List<Aut_Automaton>) ast.accept(visitor);

		// creating a listener for all the events
		// from the game canvas, that would be
		// the controller in the MVC pattern
		m_listener = new CanvasListener(this);
		// creating the game canvas to render the game,
		// that would be a part of the view in the MVC pattern
		m_canvas = new GameCanvas(m_listener);

		System.out.println("  - creating frame...");
//		Dimension d = new Dimension(1024, 768);
		Rectangle rec = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		Dimension d = new Dimension((int) rec.getWidth(), (int) rec.getHeight());
		m_frame = m_canvas.createFrame(d);
		m_frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		m_frame.setResizable(false);
		m_frame.setUndecorated(true);
		m_frame.setIconImage(null);

		System.out.println("  - setting up the frame...");

		setupFrame();

		setupMenu();
	}

	public void setupMenu() {
		menu = new Menu(m_frame);
		menu.setMenuSize();
		menu.setMenu();
	}

	public void setupGame(File file) throws Exception {
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

		player1 = new Melee("Player1", this);
		player1.name = "player1";
		player2 = new Range("Player2", this);
		player2.name = "player2";

		// map = new MazeMap(MapConstants.MAZE_MAP_SIZE *
		// (MapConstants.MAZE_MAP_CORRIDOR_SIZE + 1) + 1, MapConstants.MAZE_MAP_SIZE *
		// (MapConstants.MAZE_MAP_CORRIDOR_SIZE + 1) + 1, player1, player2);
		map = new WorldMap(100, 100, player1, player2);
		// map = new DungeonMap(32, 32, player1, player2);
		// map=new DebugMap(40,40,player1,player2);
		render = new MapRender((Map) map, this);

		// TODO correctly initialize Level and Experience methods /!\
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

		m_frame.setTitle("Game");
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
			m_canvas.playMusic(fis, 0, 1.0F);
		} catch (Throwable th) {
			th.printStackTrace(System.err);
			System.exit(-1);
		}
	}

	private int m_musicIndex = 0;
	private String[] m_musicNames = new String[] { "theme" };

	private long m_textElapsed;

	/*
	 * This method is invoked almost periodically, given the number of milli-seconds
	 * that elapsed since the last time this method was invoked.
	 */
	void tick(long elapsed) {
		if (menu.getStarted()) {
			for (int i = 0; i < EntitiesConst.MAP.projectiles.size(); i++) {
				EntitiesConst.MAP.projectiles.get(i).tick(elapsed);
			}

			((Map) map).tickEntities((int) render.camera.getX(), (int) render.camera.getY(), elapsed);
			((Map)map).tickEffects(elapsed);
			
			if (EntitiesConst.GAME.debug) {
				m_textElapsed += elapsed;
				// TODO modif pour debug
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
		g.setColor(Color.gray);
		g.fillRect(0, 0, width, height);

		if (menu.getStarted()) {
			render.paint(g);
			hud.paint(g);
			if (inMenu.getPause()) {
				inMenu.paint(g);
			}
		} else {
			menu.paint(g);
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
					+ player1.strengthPotions + "/" + player1.direction + "/" + player1.action + "\n";
			data += player2.location.getX() + "/" + player2.location.getY() + "/" + player2.currentState.name + "/"
					+ player2.health + "/" + player2.maxHealth + "/" + player2.healingPotions + "/"
					+ player2.strengthPotions + "/" + player2.direction + "/" + player2.action + "\n";

			data += Hero.level + "/" + Hero.experience + "/" + Hero.coins + "\n";

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

		Location loc1 = new Location(Float.valueOf(p1[0]), Float.valueOf(p1[1]));
		Location loc2 = new Location(Float.valueOf(p2[0]), Float.valueOf(p2[1]));
		Aut_Direction dir1 = Aut_Direction.valueOf(p1[7]);
		Aut_Direction dir2 = Aut_Direction.valueOf(p2[7]);
		Action a1 = Action.valueOf(p1[8]);
		Action a2 = Action.valueOf(p2[8]);

		player1.saveRestore(loc1, p1[2], Integer.valueOf(p1[3]), Integer.valueOf(p1[4]), Integer.valueOf(p1[5]),
				Integer.valueOf(p1[6]), dir1);
		player2.saveRestore(loc2, p2[2], Integer.valueOf(p2[3]), Integer.valueOf(p2[4]), Integer.valueOf(p2[5]),
				Integer.valueOf(p2[6]), dir2);

		Hero.saveRestore(Integer.valueOf(hero[0]), Integer.valueOf(hero[1]), Integer.valueOf(hero[2]));

	}

	public void unsave() {
		File f = new File("save.txt");
		f.delete();
	}

}
