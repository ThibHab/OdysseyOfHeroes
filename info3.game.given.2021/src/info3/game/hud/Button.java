package info3.game.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;

import info3.game.constants.EntitiesConst;

public class Button {
	int m_x, m_y, m_width, m_height;
	int def_x, def_y, def_width, def_height;
	String m_name;
	public Color m_bgColor;
	Menu m_parent;
	public JFrame m_frame;
	public boolean alreadyGrow;
	
	public Button(String name) {
		m_name = name;
	}
	
	public Button(Menu parent, Color col) {
		m_parent = parent;
		m_bgColor = col;
	}
	
	public Button(JFrame frame) {
		m_frame = frame;
	}
	
	public void setName(String name) {
		m_name = name;
	}
	
	public void setBounds(int x, int y, int width, int height) {
		m_x = x;
		def_x = x;
		m_y = y;
		def_y = y;
		m_width = width;
		def_width = width;
		m_height = height;
		def_height = height;
	}
	
	public void changeBounds(int x, int y, int width, int height) {
		m_x = x;
		m_y = y;
		m_width = width;
		m_height = height;
	}
	
	public Button selected(int x, int y) {
		if (x > m_x && x < m_x + m_width && y > m_y && y < m_y + m_height) {
			return this;
		}
		return null;
	}
	
	public String getName() {
		return this.m_name;
	}
	
	public void grow() {
		float scale = 1.2f;
		if (!alreadyGrow) {
			changeBounds((int) (m_x - ((m_width * scale) - m_width)/2), (int) (m_y - ((m_height * scale) - m_height)/2), (int) (m_width * scale), (int) (m_height * scale));
			alreadyGrow = true;
		}
	}
	
	public void shrink() {
		float scale = 1.2f;
		if (alreadyGrow) {
			changeBounds(def_x, def_y, def_width, def_height);
			alreadyGrow = false;
		}
	}
	
	public void paint(Graphics g) {
		g.setColor(m_bgColor);
		g.fillRoundRect(m_x, m_y, m_width, m_height, 10, 10);
		g.setColor(Color.white);
		Font f = new Font(null, 0, 30);
		g.setFont(f);
		Rectangle2D rec = g.getFontMetrics().getStringBounds(m_name, g);
		int textWidth = (int) rec.getWidth();
		int textHeight = (int) rec.getHeight();

		g.drawString(m_name, m_x + (m_width / 2)  - (int) (textWidth / 2), m_y + (m_height / 2) + 10);
	}
}
