package info3.game.hud;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

import info3.game.constants.ImagesConst;

public class Controls extends InGameMenu {

	public boolean controlsUp;

	public Controls(String name) {
		super(name);
		controlsUp = false;
		m_bgColor = new Color(255, 255, 255, 150);
		imgBackground = ImagesConst.CONTROL_PICTURE;
	}

	public Controls(Menu parent, Color col) {
		super(parent, col);
		m_name = "Credits";
		controlsUp = false;
		imgBackground = ImagesConst.CONTROL_PICTURE;
	}

	public Controls(JFrame frame) {
		super(frame);
		m_name = "Credits";
		controlsUp = false;
		m_bgColor = new Color(255, 255, 255, 150);
		imgBackground = ImagesConst.CONTROL_PICTURE;
	}

	public void setControlSize() {
		m_x = m_frame.getX();
		m_y = m_frame.getY();
		m_width = m_frame.getWidth();
		m_height = m_frame.getHeight();
	}

	public void setControls() {

		Button goBack = new Button(this, Color.red);

		buttons[0] = goBack;

		nbChild = 1;

		goBack.setName("Menu");

		goBack.setBounds(20, 20, 150, 50);
	}

	public boolean isControlsUp() {
		return controlsUp;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(m_bgColor);
		g.fillRect(0, 0, m_frame.getWidth(), m_frame.getHeight());
		g.drawImage(imgBackground, def_x, def_y, m_frame.getWidth(), m_frame.getHeight(), null);
		this.buttons[0].paint(g);
	}

}
