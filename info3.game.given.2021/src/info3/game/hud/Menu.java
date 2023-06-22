package info3.game.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;

import info3.game.constants.EntitiesConst;
import info3.game.graphics.GameCanvas;

public class Menu extends Button {
	
	public Button[] buttons;
	boolean isStarted;
	public int nbChild;
	Image img; // TODO trouver une image pour le menu
	
	public Menu(String name) {
		super(name);
		isStarted = false;
		nbChild = 0;
		buttons = new Button[3];
		m_bgColor = Color.blue;
	}

	public Menu(Menu parent) {
		super(parent);
		isStarted = false;
		nbChild = 0;
		buttons = new Button[3];
	}
	
	public Menu(JFrame frame) {
		super(frame);
		m_name = "Menu";
		isStarted = false;
		nbChild = 0;
		buttons = new Button[3];
		m_bgColor = Color.blue;
	}
	
	
	public void gameStart() {
		isStarted = true;
	}
	
	public Button selected(int x, int y) {
		for (int i = 0; i < nbChild; i++) {
			Button b = buttons[i].selected(x, y);
			if (b != null) {
				return b;
			}
		}
		return null;
	}

	public void setMenu() {
		Button resume = new Button(this);
		Button newGame = new Button(this);
		Button credits = new Button(this);
		
		buttons[0] = resume;
		buttons[1] = newGame;
		buttons[2] = credits;
		
		nbChild = 3;
		
		resume.setName("Resume");
		newGame.setName("New Game");
		credits.setName("Credits");
		
		resume.setBounds(m_width / 3, m_height -  3 * (m_height / 5), m_width / 3, 50);
		newGame.setBounds(m_width / 3, m_height -  2 * (m_height / 5), m_width / 3, 50);
		credits.setBounds(m_width / 3, m_height -  (m_height / 5), m_width / 3, 50);

	}
	
	public boolean getStarted() {
		return isStarted;
	}
	
	public void setStarted() {
		isStarted = true;
	}
	
	
	public void setMenuSize() {
		m_x = m_frame.getX();
		m_y = m_frame.getY();
		m_width = m_frame.getWidth();
		m_height = m_frame.getHeight();
	}

	public void paint(Graphics g) {
		GameCanvas c = EntitiesConst.GAME.m_canvas;
		g.setColor(m_bgColor);
		g.fillRect(0, 0, c.getWidth(), c.getHeight());
		for (int i = 0; i < nbChild; i++) {
			buttons[i].paint(g);
		}
		
		String gameName = "YES CA MARCHE !";
		Font title = new Font(null, 0, 100);
		g.setFont(title);
		
		Rectangle2D rec = g.getFontMetrics().getStringBounds(gameName, g);
		int textWidth = (int) rec.getWidth();
		int textHeight = (int) rec.getHeight();
		g.drawString(gameName, (m_width / 2)  - (textWidth / 2), m_height -  4 * (m_height / 5));
	}

}
