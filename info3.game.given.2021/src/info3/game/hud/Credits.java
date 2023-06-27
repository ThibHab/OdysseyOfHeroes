package info3.game.hud;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Credits extends Menu {
	public boolean creditsUp;

	public Credits(String name) {
		super(name);
		creditsUp = false;
		m_bgColor = Color.black;
		imgBackground = ImagesConst.CREDIT_PICTURE;
	}

	public Credits(Menu parent, Color col) {
		super(parent, col);
		m_name = "Credits";
		creditsUp = false;
		imgBackground = ImagesConst.CREDIT_PICTURE;
	}
	
	public Credits(JFrame frame) {
		super(frame);
		m_name = "Credits";
		creditsUp = false;
		m_bgColor = Color.black;
		imgBackground = ImagesConst.CREDIT_PICTURE;
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
		g.drawImage(imgBackground, def_x, def_y, m_frame.getWidth(), m_frame.getHeight(), null);
		this.buttons[0].paint(g);
	}

}
