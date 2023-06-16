package info3.game.hud;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

import info3.game.constants.EntitiesConst;

public class HudInGame {
	
	JFrame m_frame;

	public HudInGame(JFrame frame) {
		m_frame = frame;
	}
	
	public void paint(Graphics g){
		int width = EntitiesConst.GAME.m_canvas.getWidth();
		int height = EntitiesConst.GAME.m_canvas.getHeight();
		g.setColor(Color.red);
		String j1 = "Vie J1 :";
		g.drawString(j1, 5, 15);
		int inc = j1.length() * 6 + 5;
		for (int i =0; i < 5; i++) {
			g.fillOval(5 + inc, 5, 10, 10);
			inc += 12;
		}
		
		
		int inc2 = 0;
		for (int i =0; i < 5; i++) {
			g.fillOval(width - (10 + inc2), 5, 10, 10);
			inc2 += 12;
		}
		String j2 = "Vie J2 :";
		g.drawString(j2, width - (inc2 + j2.length() * 6 + 5), 15);
		return;
	}

}
