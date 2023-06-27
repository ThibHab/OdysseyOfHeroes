package info3.game.hud;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

import info3.game.constants.EntitiesConst;

public class InGameMenu  extends Menu {
	public boolean isPaused;
	public Controls controls;

	public InGameMenu(String name) {
		super(name);
		isStarted = true;
		isPaused = false;
	}

	public InGameMenu(Menu parent, Color col) {
		super(parent, col);
		isStarted = true;
		isPaused = false;
	}
	
	public InGameMenu(JFrame frame) {
		super(frame);
		m_name = "InGameMenu";
		isStarted = true;
		isPaused = false;
	}

	public boolean getPause() {
		return isPaused;
	}
	
	public void setPause(boolean b) {
		isPaused = b;
	}
	
	public void gamePause() {
		isPaused = true;
	}
	
	@Override
	public void setMenu() {
		Button resume = new Button(this, Color.red);
		Button Controls = new Button(this, Color.red);
		Button Quit = new Button(this, Color.red);
		
		buttons[0] = resume;
		buttons[1] = Controls;
		buttons[2] = Quit;
		
		nbChild = 3;
		
		resume.setName("Reprendre");
		Controls.setName("Controls");
		Quit.setName("Quitter");
		
		resume.setBounds(m_width / 3, m_height -  3 * (m_height / 4), m_width / 3, 50);
		Controls.setBounds(m_width / 3, m_height -  2 * (m_height / 4), m_width / 3, 50);
		Quit.setBounds(m_width / 3, m_height -  (m_height / 4), m_width / 3, 50);
		
		controls = new Controls(m_frame);
		controls.setControlSize();
		controls.setControls();
	}

	public void paint(Graphics g) {
		if (controls == null) {
			Color c = new Color(255, 255, 255, 150);
			g.setColor(c);
			g.fillRect(0, 0, m_width, m_height);
			for (int i = 0; i < nbChild; i++) {
				buttons[i].paint(g);
			}
		} else {
			if (!controls.isControlsUp()) {
				Color c = new Color(255, 255, 255, 150);
				g.setColor(c);
				g.fillRect(0, 0, m_width, m_height);
				for (int i = 0; i < nbChild; i++) {
					buttons[i].paint(g);
				}
			} else {
				controls.paint(g);
			}
		}
	}
}
