package info3.game.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;
import info3.game.entity.*;

public class HudInGame {
	
	JFrame m_frame;
	Melee j1;
	Range j2;
	int moula;
	Image redHeart, blueHeart, blackHeart, potion, melee, range, coinIcone, sword;

	public HudInGame(JFrame frame) {
		m_frame = frame;
		j1 = EntitiesConst.GAME.player1;
		j2 = EntitiesConst.GAME.player2;
		moula = Hero.coins;
		redHeart = ImagesConst.RED_HEART;
		blueHeart = ImagesConst.BLUE_HEART;
		blackHeart = ImagesConst.BLACK_HEART;
		potion = ImagesConst.HEALING_POTION[0];
		melee = ImagesConst.MELEE[0];
		range = ImagesConst.RANGE[0];
		coinIcone = ImagesConst.COIN[0];
		sword = ImagesConst.SWORD;
	}
	
	public void paint(Graphics g){
		int width = EntitiesConst.GAME.m_canvas.getWidth();
		int height = EntitiesConst.GAME.m_canvas.getHeight();

		
		Font f = new Font(null, 0, 25);
		g.setFont(f);
		
		g.setColor(Color.blue);
		g.drawRect(7, 7, 55, 55);
		g.drawImage(melee, 5, 7, 60,  60, m_frame);
		// TODO: Mettre le nombre de vie max en gris (Besoind de l'inforamtion de vie max)
		
		int incJ11 = 63;
		int incJ12 = 63;
		for (int i =0; i < j1.maxHealth; i++) {
			if (i < 10) {
				g.drawImage(blackHeart, 5 + incJ11, 7, 25, 25, null);
				incJ11 += 30;
			}
			else {
				g.drawImage(blackHeart, 5 + incJ12, 35, 25, 25, null);
				incJ12 += 30;
			}			
		}
		
		incJ11 = 63;
		incJ12 = 63;
		for (int i =0; i < j1.health; i++) {
			if (i < 10) {
				g.drawImage(blueHeart, 5 + incJ11, 7, 25, 25, null);
				incJ11 += 30;
			}
			else {
				g.drawImage(blueHeart, 5 + incJ12, 35, 25, 25, null);
				incJ12 += 30;
			}			
		}
		
		g.drawImage(sword, 7, 70, 25, 25, m_frame);
		String strenght = "" + j1.weaponDamage;
		g.drawString(strenght, 7 + 28, 93);
		
		g.drawImage(potion, 7, 100, 25, 25, m_frame);
		String potionJ1 = "X" + j1.healingPotions;
		g.drawString(potionJ1, 7 + 28, 123);
		
		
		g.setColor(Color.red);
		g.drawRect(width - 62, 5, 55, 55);
		g.drawImage(range, width - 64, 7, 60,  60, m_frame);
		// TODO: Mettre le nombre de vie max en gris (Besoind de l'inforamtion de vie max)
		
		int inc1 = 87;
		int inc2 = 87;
		for (int i =0; i < j2.maxHealth; i++) {
			if (i < 10) {
				g.drawImage(blackHeart, width - (5 + inc1), 7, 25, 25, null);
				inc1 += 30;
			}
			else {
				g.drawImage(blackHeart, width - (5 + inc2), 35, 25, 25, null);
				inc2 += 30;
			}
		}
		
		inc1 = 87;
		inc2 = 87;
		for (int i =0; i < j2.health; i++) {
			if (i < 10) {
				g.drawImage(redHeart, width - (5 + inc1), 7, 25, 25, null);
				inc1 += 30;
			}
			else {
				g.drawImage(redHeart, width - (5 + inc2), 35, 25, 25, null);
				inc2 += 30;
			}
		}
		
		g.drawImage(sword, width - 32, 70, 25, 25, m_frame);
		String strenght2 = "" + j2.weaponDamage;
		g.drawString(strenght2, width - (7 + 28 + (17 * strenght2.length())), 93);
		
		g.drawImage(potion, width - 32, 100, 25, 25, m_frame);
		String potionJ2 = j2.healingPotions + "X";
		g.drawString(potionJ2, width - (7 + 28 + (17 * potionJ2.length())), 123);
		
		int coinWidth = 25;
		g.drawImage(coinIcone, width/2 - (coinWidth / 2), 7, coinWidth, coinWidth, m_frame);
		g.setColor(Color.YELLOW);
		String argent = "" + Hero.coins;
		g.drawString(argent, width/2 - ((argent.length() * 32) / 4), 55);
		
		g.setColor(Color.blue);
		g.drawRoundRect(width/8, height - 15, width - (width / 4), 10, 10, 10);
		g.drawRoundRect(width/8 - 1, height - 16, width - (width / 4)+ 2, 12, 10, 10);
		
		Color grayTrans = new Color(192, 192, 192, 100);
		g.setColor(grayTrans);
		g.fillRoundRect(width/8 + 1, height - 14, width - width/4 - 1, 9, 10, 10);
		// TODO: Test experience bar
		g.setColor(Color.cyan);
		int exp = Hero.experience;
		if (exp == 0) {
			g.fillRoundRect(width/8 + 1, height - 14, 0, 9, 10, 10);
		}
		else {
			g.fillRoundRect(width/8 + 1, height - 14, (int) ((width - width/4 - 1) / ((float) Hero.levelUp / (float) exp )), 9, 10, 10);
		}
		
		g.setColor(Color.blue);
		String lv = "Level " + Hero.level;
		g.drawString(lv, width/2 - ((lv.length() * 28) / 4), height - 20);
		
		return;
	}

}
