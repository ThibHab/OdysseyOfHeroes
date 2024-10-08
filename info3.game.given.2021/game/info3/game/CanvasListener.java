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
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;
import info3.game.entity.Hero;
import info3.game.entity.Range;
import info3.game.graphics.GameCanvasListener;
import info3.game.hud.Button;
import info3.game.hud.InGameMenu;
import info3.game.hud.Menu;
import info3.game.map.WorldMap;

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
		if (m_game.debug) {
			System.out.println("Mouse pressed: (" + e.getX() + "," + e.getY() + ")");
			System.out.println("   modifiers=" + e.getModifiersEx());
			System.out.println("   buttons=" + e.getButton());
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Menu menu = EntitiesConst.GAME.menu;
		InGameMenu inMenu = EntitiesConst.GAME.inMenu;
		Game game = EntitiesConst.GAME;
		if (m_focused != null) {
			if (!menu.getStarted()) {
				if (menu.credits.isCreditUp()) {
					if (m_focused == menu.credits.selected(e.getX(), e.getY()) && m_focused.getName().equals("Menu")) {
						menu.credits.creditsUp = false;
					}
				} else {
					if (m_focused == menu.selected(e.getX(), e.getY())) {
						if (m_focused.getName().equals("Reprendre la partie")) {
							File f = new File("save.txt");
							long length = f.length();
							if (length == 0)
								try {
									m_game.setupGame(null);
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							else
								try {
									m_game.setupGame(f);
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							menu.setStarted();
						} else if (m_focused.getName().equals("Nouvelle partie")) {
							menu.setStarted();
							try {
								m_game.setupGame(null);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						} else if (m_focused.getName().equals("Credits")) {
							menu.credits.creditsUp = true;
						} else if (m_focused.getName().equals("Quitter")) {
							this.exit();
						} else {
							m_focused = null;
						}
					}
				}

			} else if (inMenu.getPause()) {
				if (inMenu.controls.isControlsUp()) {
					if (m_focused == inMenu.controls.selected(e.getX(), e.getY())
							&& m_focused.getName().equals("Menu")) {
						inMenu.controls.controlsUp = false;
					}
				} else {
					if (m_focused == inMenu.selected(e.getX(), e.getY())) {
						if (m_focused.getName().equals("Reprendre")) {
							game.m_frame.setCursor(game.m_frame.getToolkit().createCustomCursor(
									new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null"));
							inMenu.setPause(false);
						} else if (m_focused.getName().equals("Controls")) {
							inMenu.controls.controlsUp = true;
						} else if (m_focused.getName().equals("Quitter")) {
							this.exit();
						} else {
							m_focused.m_bgColor = Color.red;
							m_focused = null;
						}
					}
				}
			}
		}
		if (m_game.debug)

		{
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
				if (menu.credits.isCreditUp()) {
					m_focused = menu.credits.selected(e.getX(), e.getY());
					int nChild = menu.credits.nbChild;
					for (int i = 0; i < nChild; i++) {
						if (menu.credits.buttons[i] == m_focused) {
							m_focused.grow();
						} else {
							menu.credits.buttons[i].shrink();
						}
					}
				} else {
					if (menu.selected(e.getX(), e.getY()) == null
							|| menu.selected(e.getX(), e.getY()).m_bgColor == Color.LIGHT_GRAY) {
						m_focused = null;
					} else {
						m_focused = menu.selected(e.getX(), e.getY());
					}
					int nChild = menu.nbChild;
					for (int i = 0; i < nChild; i++) {
						if (menu.buttons[i] == m_focused) {
							m_focused.grow();
						} else {
							menu.buttons[i].shrink();
						}
					}
				}
			} else if (inMenu.getPause()) {
				if (inMenu.controls.isControlsUp()) {
					m_focused = inMenu.controls.selected(e.getX(), e.getY());
					int nChild = inMenu.controls.nbChild;
					for (int i = 0; i < nChild; i++) {
						if (inMenu.controls.buttons[i] == m_focused) {
							m_focused.grow();
						} else {
							inMenu.controls.buttons[i].shrink();
						}
					}
				} else {
					m_focused = inMenu.selected(e.getX(), e.getY());
					int nChild = inMenu.nbChild;
					for (int i = 0; i < nChild; i++) {
						if (inMenu.buttons[i] == m_focused) {
							m_focused.grow();
						} else {
							inMenu.buttons[i].shrink();
						}
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
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			m_game.save();
			if (m_game.debug) {
				Hero.bombs = 100;
				m_game.player1.healingPotions = 100;
				m_game.player2.healingPotions = 100;
				Range.firePowerUnlocked = true;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_H && m_game.menu.getStarted() && EntitiesConst.MAP instanceof WorldMap
				&& !m_game.inMenu.isPaused) {
			EntitiesConst.GAME.showMap = true;
		}

		if (!keys.contains((Integer) e.getKeyCode())) {
			keys.add((Integer) e.getKeyCode());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Game game = EntitiesConst.GAME;
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE && m_game.inMenu != null) {
			boolean b = m_game.inMenu.getPause();
			if (!b) {
				Toolkit tkit = Toolkit.getDefaultToolkit();
				Point point = new Point(25, 25);
				Image agrou = ImagesConst.CURSOR[0];
				Cursor curs = tkit.createCustomCursor(agrou, point, "AgrouCurs");
				game.m_frame.setCursor(curs);
			} else {
				game.m_frame.setCursor(game.m_frame.getToolkit().createCustomCursor(
						new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null"));
			}
			m_game.inMenu.setPause(!b);
		} else if (e.getKeyCode() == KeyEvent.VK_H && m_game.menu.getStarted() && !m_game.inMenu.isPaused) {
			EntitiesConst.GAME.showMap = false;
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
	}

	@Override
	public void exit() {
		System.exit(0);
	}

	@Override
	public void endOfPlay(String name) {
	}

	@Override
	public void expired() {
	}

}
