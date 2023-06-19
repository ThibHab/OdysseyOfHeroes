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
import java.io.RandomAccessFile;
import java.nio.CharBuffer;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import info3.game.automata.Aut_Automaton;
import info3.game.automata.ast.AST;
import info3.game.automata.ast.AutCreator;
import info3.game.automata.ast.IVisitor;
import info3.game.automata.parser.AutomataParser;
import info3.game.constants.ImagesConst;
import info3.game.entity.Cowboy;
import info3.game.entity.Entity;
import info3.game.graphics.GameCanvas;
import info3.game.hud.HudInGame;
import info3.game.map.DebugMap;
import info3.game.map.IMap;
import info3.game.map.Map;
import info3.game.map.MapRender;
import info3.game.map.WorldMap;
import info3.game.sound.RandomFileInputStream;

public class Game {

	static Game game;

	public static void main(String args[]) throws Exception {
		try {
			System.out.println("Game starting...");
			game = new Game();
			System.out.println("Game started.");
		} catch (Throwable th) {
			th.printStackTrace(System.err);
		}
	}

	JFrame m_frame;
	JLabel m_text;
	public GameCanvas m_canvas;
	public CanvasListener m_listener;
	Cowboy m_cowboy;
	public Cowboy player1;
	public Cowboy player2;
	Sound m_music;
	public IMap map;
	public MapRender render;
	public List<Aut_Automaton> listAutomata;
	public HudInGame hud;

	Game() throws Exception {
		// creating a cowboy, that would be a model
		// in an Model-View-Controller pattern (MVC)
//		m_cowboy = new Cowboy(this);
		new ImagesConst();
		
		//TODO correctly initialize Level and Experience methods /!\
		int level = 0, xp = 0;
		
		IVisitor visitor = new AutCreator();
		AST ast = (AST)AutomataParser.from_file("resources/t.gal");
		listAutomata = (List<Aut_Automaton>) ast.accept(visitor);
		
//		player1 = new Cowboy(this, "Player1");
//		player1.frozen = true;
//		player2 = new Cowboy(this, "Player2");
//		player2.frozen = true;
		
		player1 = new Cowboy(this, "Player1");
		player1.frozen = true;
		player2 = new Cowboy(this, "Player2");
		player2.frozen = true;
		// creating a listener for all the events
		// from the game canvas, that would be
		// the controller in the MVC pattern
		m_listener = new CanvasListener(this);
		// creating the game canvas to render the game,
		// that would be a part of the view in the MVC pattern
		m_canvas = new GameCanvas(m_listener);
		
		map = new WorldMap(64, 64, player1, player2);
		render = new MapRender((Map)map, this);
		
		Entity.InitStatics(this, level, xp);
		
		player1.frozen = false;
		player2.frozen = false;

		System.out.println("  - creating frame...");
		Dimension d = new Dimension(1024, 768);
		m_frame = m_canvas.createFrame(d);
		
		hud = new HudInGame(m_frame);

		System.out.println("  - setting up the frame...");
		setupFrame();
	}

	/*
	 * Then it lays out the frame, with a border layout, adding a label to the north
	 * and the game canvas to the center.
	 */
	private void setupFrame() {

		m_frame.setTitle("Game");
		m_frame.setLayout(new BorderLayout());

		m_frame.add(m_canvas, BorderLayout.CENTER);

		m_text = new JLabel();
		m_text.setText("Tick: 0ms FPS=0");
		m_frame.add(m_text, BorderLayout.NORTH);

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
			RandomAccessFile file = new RandomAccessFile(filename,"r");
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

		player1.tick(elapsed);
		player2.tick(elapsed);

		// Update every second
		// the text on top of the frame: tick and fps
		m_textElapsed += elapsed;
		//TODO modif pour debug
		if (m_textElapsed > 100) {
			m_textElapsed = 0;
			float period = m_canvas.getTickPeriod();
			int fps = m_canvas.getFPS();

			String txt = "Tick=" + period + "ms";
			while (txt.length() < 15)
				txt += " ";
			txt = txt + fps + " fps   ";
			txt = txt+"P1:" + player1.location.getX() + ";" + player1.location.getY() + "     ";
			txt = txt+"P2:" + player2.location.getX() + ";" + player2.location.getY() + "     ";
			txt = txt+"Cam:" + render.camera.getX() + ";" + render.camera.getY() + "     ";
			txt = txt+"offset" + render.offset.getX() + ";" + render.offset.getY() + "     ";
			m_text.setText(txt);
		}
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
		


		// paint
//		m_cowboy.paint(g, width, height);
		
		render.paint(g);
		hud.paint(g);
//		g.setColor(Color.red);
//		g.fillRect(5, height - 15, 10, 10);
//		g.fillRect(17, height - 15, 10, 10);
//		g.fillRect(29, height - 15, 10, 10);
//		g.fillRect(41, height - 15, 10, 10);
//		g.fillRect(53, height - 15, 10, 10);
//		String j1 = "Vie J1";
//		g.drawString(j1, 70, height - 5);
	}

}
