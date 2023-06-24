package info3.game.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;

import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;
import info3.game.graphics.GameCanvas;

public class Menu extends Button {

	public Button[] buttons;
	boolean isStarted;
	public int nbChild;
	Image imgBackground; // TODO trouver une image pour le menu
	public Credits credits;

	public Menu(String name) {
		super(name);
		isStarted = false;
		nbChild = 0;
		buttons = new Button[3];
		m_bgColor = Color.blue;
	}

	public Menu(Menu parent, Color col) {
		super(parent, col);
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
		imgBackground = ImagesConst.MENU_PICTURE;
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
		Color col = Color.red;
		if (!EntitiesConst.GAME.saveExist) {
			col = Color.lightGray;
		}
		Button resume = new Button(this, col);
		Button newGame = new Button(this, Color.red);
		Button creditPage = new Button(this, Color.red);

		buttons[0] = resume;
		buttons[1] = newGame;
		buttons[2] = creditPage;

		nbChild = 3;

		resume.setName("Reprendre la partie");
		newGame.setName("Nouvelle partie");
		creditPage.setName("Credits");

		resume.setBounds(m_width / 3, m_height - 3 * (m_height / 5), m_width / 3, 50);
		newGame.setBounds(m_width / 3, m_height - 2 * (m_height / 5), m_width / 3, 50);
		creditPage.setBounds(m_width / 3, m_height - (m_height / 5), m_width / 3, 50);

		credits = new Credits(m_frame);
		credits.setCreditSize();
		credits.setCredits();

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
		if (credits == null) {
			GameCanvas c = EntitiesConst.GAME.m_canvas;
			g.setColor(m_bgColor);
			g.fillRect(0, 0, c.getWidth(), c.getHeight());
			g.drawImage(imgBackground, def_x, def_y, m_width, m_height, null);
			for (int i = 0; i < nbChild; i++) {
				buttons[i].paint(g);
			}

			String gameName = "YES CA MARCHE !";
			Font title = new Font(null, 0, 100);
			g.setFont(title);

			Rectangle2D rec = g.getFontMetrics().getStringBounds(gameName, g);
			int textWidth = (int) rec.getWidth();
			int textHeight = (int) rec.getHeight();
			g.drawString(gameName, (m_width / 2) - (textWidth / 2), m_height - 4 * (m_height / 5));
		} else {
			if (!credits.isCreditUp()) {
				GameCanvas c = EntitiesConst.GAME.m_canvas;
				g.setColor(m_bgColor);
				g.fillRect(0, 0, c.getWidth(), c.getHeight());
				g.drawImage(imgBackground, def_x, def_y, m_width, m_height, null);
				for (int i = 0; i < nbChild; i++) {
					buttons[i].paint(g);
				}

				String gameName = "YES CA MARCHE !";
				Font title = new Font(null, 0, 100);
				g.setFont(title);

				Rectangle2D rec = g.getFontMetrics().getStringBounds(gameName, g);
				int textWidth = (int) rec.getWidth();
				int textHeight = (int) rec.getHeight();
				g.drawString(gameName, (m_width / 2) - (textWidth / 2), m_height - 4 * (m_height / 5));
			} else {
				credits.paint(g);
			}
		}

	}

}
