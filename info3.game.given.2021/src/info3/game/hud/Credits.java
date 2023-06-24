package info3.game.hud;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

import info3.game.constants.EntitiesConst;

public class Credits extends Menu {
	public boolean creditsUp;
	Image creditsImg;

	public Credits(String name) {
		super(name);
		creditsUp = false;
		m_bgColor = Color.black;
		// TODO: rajouter l'image
	}

	public Credits(Menu parent, Color col) {
		super(parent, col);
		m_name = "Credits";
		creditsUp = false;
		// TODO: rajouter l'image
	}
	
	public Credits(JFrame frame) {
		super(frame);
		m_name = "Credits";
		creditsUp = false;
		m_bgColor = Color.black;
		// TODO: rajouter l'image
	}
	
	public void setCreditSize() {
		m_x = m_frame.getX();
		m_y = m_frame.getY();
		m_width = m_frame.getWidth();
		m_height = m_frame.getHeight();
	}
	
	public void setCredits() {

		Button goBack = new Button(this, Color.red);
		
		buttons[0] = goBack;
		
		nbChild = 1;
		
		goBack.setName("Menu");
		
		goBack.setBounds(20, 20, 150, 50);
	}
	
	public boolean isCreditUp() {
		return creditsUp;
	}
	
	public void paint(Graphics g) {
		// TODO: remplaser le background par l'image
		g.setColor(m_bgColor);
		g.fillRect(0, 0, m_frame.getWidth(), m_frame.getHeight());
		this.buttons[0].paint(g);
	}

}
