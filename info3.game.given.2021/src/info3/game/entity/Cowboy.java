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
package info3.game.entity;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import info3.game.Game;
import info3.game.automata.*;
import info3.game.automata.ast.AST;
import info3.game.automata.ast.AutCreator;
import info3.game.automata.ast.IVisitor;
import info3.game.automata.parser.AutomataParser;

/**
 * A simple class that holds the images of a sprite for an animated cowbow.
 *
 */
public class Cowboy extends Entity {
	BufferedImage[] m_images;
	int m_imageIndex;
	long m_imageElapsed;
	long m_moveElapsed;
	int m_x = 10, m_y = 10;
	int m_width;
	Aut_Automaton aut;
	Game game;

	public Cowboy(Game g) throws IOException {
		m_images = loadSprite("resources/winchester-4x6.png", 4, 6);
//		IVisitor visitor = new AutCreator();
//		try {
//			AST ast = (AST)AutomataParser.from_file("resources/test.gal");
//			aut = (Aut_Automaton)ast.accept(visitor);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			aut = null;
//		}
		Aut_State src = new Aut_State("1");
		Cond up = new Cond(new Aut_Key(KeyEvent.VK_UP), null, "");
		Cond down = new Cond(new Aut_Key(KeyEvent.VK_DOWN), null, "");
		Cond left = new Cond(new Aut_Key(KeyEvent.VK_LEFT), null, "");
		Cond right = new Cond(new Aut_Key(KeyEvent.VK_RIGHT), null, "");
		Aut_Action moveUp = new Move(Aut_Direction.N);
		Aut_Action moveDown = new Move(Aut_Direction.S);
		Aut_Action moveLeft = new Move(Aut_Direction.W);
		Aut_Action moveRight = new Move(Aut_Direction.E);
		Aut_Transition t1 = new Aut_Transition(src, up, moveUp, src);
		Aut_Transition t2 = new Aut_Transition(src, down, moveDown, src);
		Aut_Transition t3 = new Aut_Transition(src, left, moveLeft, src);
		Aut_Transition t4 = new Aut_Transition(src, right, moveRight, src);
		LinkedList<Aut_Transition> list = new LinkedList<Aut_Transition>();
		list.add(t1);
		list.add(t2);
		list.add(t3);
		list.add(t4);
		aut = new Aut_Automaton("Cowboy", src, list);
		game = g;
		currentState = aut.initial;
		this.direction = Aut_Direction.S;
	}

	/*
	 * Simple animation here, the cowbow
	 */
	public void tick(long elapsed) {
//		m_imageElapsed += elapsed;
//		if (m_imageElapsed > 200) {
//			m_imageElapsed = 0;
//			m_imageIndex = (m_imageIndex + 1) % m_images.length;
//		}
//		m_moveElapsed += elapsed;
//		if (m_moveElapsed > 24 & m_width != 0) {
//			m_moveElapsed = 0;
//			m_x = (m_x + 2) % m_width;
//		}
		
		aut.step(this, game);

	}

	public void paint(Graphics g, int width, int height) {
		m_width = width;
		BufferedImage img = m_images[m_imageIndex];
		Location pixel=game.render.gridToPixel(location,true);
		g.drawImage(img, (int)pixel.getX(), (int)pixel.getY(), game.render.tileSize, game.render.tileSize, null);
	}

	public static BufferedImage[] loadSprite(String filename, int nrows, int ncols) throws IOException {
		File imageFile = new File(filename);
		if (imageFile.exists()) {
			BufferedImage image = ImageIO.read(imageFile);
			int width = image.getWidth(null) / ncols;
			int height = image.getHeight(null) / nrows;

			BufferedImage[] images = new BufferedImage[nrows * ncols];
			for (int i = 0; i < nrows; i++) {
				for (int j = 0; j < ncols; j++) {
					int x = j * width;
					int y = i * height;
					images[(i * ncols) + j] = image.getSubimage(x, y, width, height);
				}
			}
			return images;
		}
		return null;
	}

//	@Override
//	public void Move(Aut_Direction d) {
//		System.out.print("Move in direction " + d.toString() + "\n");
//	}
}
