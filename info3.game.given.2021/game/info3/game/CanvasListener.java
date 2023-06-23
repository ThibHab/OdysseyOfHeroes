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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import info3.game.constants.EntitiesConst;
import info3.game.graphics.GameCanvasListener;
import info3.game.hud.Button;
import info3.game.hud.InGameMenu;
import info3.game.hud.Menu;
import info3.game.map.Map;

public class CanvasListener implements GameCanvasListener {
	Game m_game;
	public List<Integer> keys;
	Button m_focused;

	CanvasListener(Game game) {
		m_game = game;
		keys = new LinkedList<Integer>();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (m_game.debug) {
			System.out.println("Mouse clicked: (" + e.getX() + "," + e.getY() + ")");
			System.out.println("   modifiers=" + e.getModifiersEx());
			System.out.println("   buttons=" + e.getButton());
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		Menu menu = EntitiesConst.GAME.menu;
		if (m_focused != null) {
			if (!menu.getStarted()) {
				m_focused.m_bgColor = Color.cyan;
			}
		} else if (m_game.debug) {
			System.out.println("Mouse pressed: (" + e.getX() + "," + e.getY() + ")");
			System.out.println("   modifiers=" + e.getModifiersEx());
			System.out.println("   buttons=" + e.getButton());
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Menu menu = EntitiesConst.GAME.menu;
		InGameMenu inMenu = EntitiesConst.GAME.inMenu;
		if (m_focused != null) {
			if (!menu.getStarted()) {
				if (m_focused == menu.selected(e.getX(), e.getY())) {
					if (m_focused.getName().equals("Resume")) {
					} else if (m_focused.getName().equals("New Game")) {
						menu.setStarted();
						m_game.setupGame();
					} else if (m_focused.getName().equals("Credits")) {
						System.out.println("Credits !");
					} else {
						m_focused.m_bgColor = Color.red;
						m_focused = null;
					}
				}
			} 
			else if (inMenu.getPause()) {
				if (m_focused == inMenu.selected(e.getX(), e.getY())) {
					if (m_focused.getName().equals("Resume")) {
						inMenu.setPause(false);
					} else if (m_focused.getName().equals("Controls")) {
						// TODO
					} else if (m_focused.getName().equals("Quit")) {
						this.exit();
					} else {
						m_focused.m_bgColor = Color.red;
						m_focused = null;
					}
				}
			}
		}
		if (m_game.debug) {
			System.out.println("Mouse released: (" + e.getX() + "," + e.getY() + ")");
			System.out.println("   modifiers=" + e.getModifiersEx());
			System.out.println("   buttons=" + e.getButton());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (m_game.debug) {
			System.out.println("Mouse entered: (" + e.getX() + "," + e.getY() + ")");
			System.out.println("   modifiers=" + e.getModifiersEx());
			System.out.println("   buttons=" + e.getButton());
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (m_game.debug) {
			System.out.println("Mouse exited: (" + e.getX() + "," + e.getY() + ")");
			System.out.println("   modifiers=" + e.getModifiersEx());
			System.out.println("   buttons=" + e.getButton());
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (m_game.debug) {
			System.out.println("Mouse dragged: (" + e.getX() + "," + e.getY() + ")");
			System.out.println("   modifiers=" + e.getModifiersEx());
			System.out.println("   buttons=" + e.getButton());
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Menu menu = EntitiesConst.GAME.menu;
		InGameMenu inMenu = EntitiesConst.GAME.inMenu;
		if (menu != null) {
			if (!menu.getStarted()) {
				m_focused = menu.selected(e.getX(), e.getY());
				int nChild = menu.nbChild;
				for (int i = 0; i < nChild; i++) {
					if (menu.buttons[i] == m_focused) {
						m_focused.grow();
					} else {
						menu.buttons[i].shrink();
						menu.buttons[i].m_bgColor = Color.red;
					}
				}
			}
			else if (inMenu.getPause()) {
				m_focused = inMenu.selected(e.getX(), e.getY());
				int nChild = inMenu.nbChild;
				for (int i = 0; i < nChild; i++) {
					if (inMenu.buttons[i] == m_focused) {
						m_focused.grow();
					} else {
						inMenu.buttons[i].shrink();
						inMenu.buttons[i].m_bgColor = Color.red;
					}
				}
			}
		}
		
		if (m_game.debug) {
			System.out.println("Mouse moved: (" + e.getX() + "," + e.getY() + ")");
			System.out.println("   modifiers=" + e.getModifiersEx());
			System.out.println("   buttons=" + e.getButton());
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (m_game.debug) {
			System.out.println("Key typed: " + e.getKeyChar() + " code=" + e.getKeyCode());
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (m_game.debug) {
			System.out.println("Key pressed: " + e.getKeyChar() + " code=" + e.getKeyCode());
		}
		if (!keys.contains((Integer) e.getKeyCode())) {
			keys.add((Integer) e.getKeyCode());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE && m_game.inMenu != null) {
			boolean b = m_game.inMenu.getPause();
			m_game.inMenu.setPause(!b);
		}
		if (m_game.debug) {
			System.out.println("Key released: " + e.getKeyChar() + " code=" + e.getKeyCode());
		}
		keys.remove((Integer) e.getKeyCode());
	}

	@Override
	public void tick(long elapsed) {
		m_game.tick(elapsed);
	}

	@Override
	public void paint(Graphics g) {
		m_game.paint(g);
	}

	@Override
	public void windowOpened() {
		m_game.loadMusic();
//    m_game.m_canvas.setTimer(6000);
	}

	@Override
	public void exit() {
		System.exit(0);
	}

//  boolean m_expired;
	@Override
	public void endOfPlay(String name) {
//    if (!m_expired) // only reload if it was a forced reload by timer
		m_game.loadMusic();
//    m_expired = false;
	}

	@Override
	public void expired() {
		// will force a change of music, after 6s of play
//    System.out.println("Forcing an ealy change of music");
//    m_expired = true;
//    m_game.loadMusic();    
	}

}
