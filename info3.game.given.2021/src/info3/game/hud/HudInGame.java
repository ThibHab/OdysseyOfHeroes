package info3.game.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;
import info3.game.entity.Coin;
import info3.game.entity.Entity;

public class HudInGame {
	
	JFrame m_frame;
	Entity j1;
	Entity j2;
	int moula;

	public HudInGame(JFrame frame) {
		m_frame = frame;
		j1 = EntitiesConst.GAME.player1;
		j2 = EntitiesConst.GAME.player2;
		moula = EntitiesConst.GAME.player1.coins;
	}
	
	public void paint(Graphics g){
		int width = EntitiesConst.GAME.m_canvas.getWidth();
		int height = EntitiesConst.GAME.m_canvas.getHeight();
		
		g.setColor(Color.blue);
		g.drawRect(7, 7, 55, 55);
		Image melee = ImagesConst.MELEE[0];
		g.drawImage(melee, 5, 7, 60,  60, m_frame);
		
		int inc = 63;
		for (int i =0; i < 10; i++) {
			g.fillOval(5 + inc, 7, 25, 25);
			g.fillOval(5 + inc, 35, 25, 25);
			inc += 30;
		}
		
		Image potion = ImagesConst.HEALING_POTION[0];
		g.drawImage(potion, 7, 70, 25, 25, m_frame);
		
		g.setColor(Color.red);
		g.drawRect(width - 63, 5, 55, 55);
		Image range = ImagesConst.RANGE[0];
		g.drawImage(range, width - 65, 7, 60,  60, m_frame);
		int inc2 = 87;
		for (int i =0; i < 10; i++) {
			g.fillOval(width - (5 + inc2), 7, 25, 25);
			g.fillOval(width - (5 + inc2), 35, 25, 25);
			inc2 += 30;
		}
		
		g.drawImage(potion, width - 32, 70, 25, 25, m_frame);
		
		Image coinIcone = ImagesConst.COIN[0];
		g.drawImage(coinIcone, width/2, 7, 25, 25, m_frame);
		g.setColor(Color.YELLOW);
		String argent = "1000";
		Font f = new Font(argent, 2, 25);
		g.setFont(f);
		g.drawString(argent, width/2 - 20, 55);
		
		return;
	}

}
