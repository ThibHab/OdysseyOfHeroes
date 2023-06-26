package info3.game.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.ImageCapabilities;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.JFrame;

import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;
import info3.game.entity.*;
import info3.game.map.DungeonMap;
import info3.game.map.Maze;
import info3.game.map.MazeMap;

public class HudInGame {

	JFrame m_frame;
	Melee j1;
	Range j2;
	int moula;
	Image redHeart, blueHeart, blackHeart, potion, melee, range, coinIcone, sword, bomb, power;

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
		sword = ImagesConst.SWORD[0];
		bomb = ImagesConst.BOMB[0];
		power = ImagesConst.FIRE_POWER[0];
	}

	public void setTimer(Graphics g) {
		int timer = EntitiesConst.MAZE_COUNTER_LIMIT - ((MazeMap) EntitiesConst.MAP).mazeCounter;
		int timerSec = (timer / 1000) % 60;
		int timerMin = (timer / 1000) / 60;
		String setTimer = String.format("%02d", timerMin) + ":" + String.format("%02d", timerSec);
		Font f = new Font(null, 0, 50);
		g.setColor(Color.red);
		g.setFont(f);
		Rectangle2D rec = g.getFontMetrics().getStringBounds(setTimer, g);
		int textWidth = (int) rec.getWidth();
		int textHeight = (int) rec.getHeight();

		g.drawString(setTimer, (m_frame.getWidth() / 2) - (int) (textWidth / 2), 200);
	}

	public void setBomb(Graphics g, Font f, int width, int height) {
		g.setColor(Color.black);
		g.setFont(f);
		g.drawImage(bomb, (width / 2) - (50 / 2), 70, 50, 50, m_frame);
		String nbBomb = "" + Hero.bombs;
		Rectangle2D rec = g.getFontMetrics().getStringBounds(nbBomb, g);
		int textWidth = (int) rec.getWidth();
		g.drawString(nbBomb, (width / 2) - (textWidth / 2), 140);
	}

	public void setCoin(Graphics g, Font f, int width, int height) {
		int coinWidth = 25;
		g.drawImage(coinIcone, width / 2 - (coinWidth / 2), 7, coinWidth, coinWidth, m_frame);
		g.setColor(Color.YELLOW);
		String argent = "" + Hero.coins;
		g.drawString(argent, width / 2 - ((argent.length() * 32) / 4), 55);
	}

	public void setLevel(Graphics g, Font f, int width, int height) {
		g.setColor(Color.blue);
		g.drawRoundRect(width / 8, height - 15, width - (width / 4), 10, 10, 10);
		g.drawRoundRect(width / 8 - 1, height - 16, width - (width / 4) + 2, 12, 10, 10);

		Color grayTrans = new Color(192, 192, 192, 100);
		g.setColor(grayTrans);
		g.fillRoundRect(width / 8 + 1, height - 14, width - width / 4 - 1, 9, 10, 10);

		g.setColor(Color.cyan);
		int exp = Hero.experience;
		if (exp == 0) {
			g.fillRoundRect(width / 8 + 1, height - 14, 0, 9, 10, 10);
		} else {
			g.fillRoundRect(width / 8 + 1, height - 14,
					(int) ((width - width / 4 - 1) / ((float) Hero.levelUp / (float) exp)), 9, 10, 10);
		}

		g.setColor(Color.blue);
		String lv = "Level " + Hero.level;
		Rectangle2D rec = g.getFontMetrics().getStringBounds(lv, g);
		int textWidth = (int) rec.getWidth();
		g.drawString(lv, (width / 2) - (textWidth / 2), height - 20);
	}

	public void setBushQuest(Graphics g, Font f, int width, int height) {
		String quest = "Buissons détruits :";
		String advancement = "" + Hero.bushesCut + " / " + 20;

		Rectangle2D rec = g.getFontMetrics().getStringBounds(quest, g);
		int textWidth = (int) rec.getWidth();

		g.drawString(quest, width - (15 + textWidth), height);

		rec = g.getFontMetrics().getStringBounds(advancement, g);
		textWidth = (int) rec.getWidth();
		int textHeight = (int) rec.getHeight();

		g.drawString(advancement, width - (15 + textWidth), height + textHeight);
	}

	public void setTorchQuest(Graphics g, int width, int height) {
		String quest = "Torches allumées :";
		List<Torch> torches = DungeonMap.torches;
		int nbTorchesLit = 0;
		for (Torch torch : torches) {
			if (torch.lit) {
				nbTorchesLit += 1;
			}
		}
		String advancement = "" + nbTorchesLit + " / " + EntitiesConst.NUMBER_OF_TORCHES;

		Rectangle2D rec = g.getFontMetrics().getStringBounds(quest, g);
		int textWidth = (int) rec.getWidth();

		g.drawString(quest, width - (15 + textWidth), height);

		rec = g.getFontMetrics().getStringBounds(advancement, g);
		textWidth = (int) rec.getWidth();
		int textHeight = (int) rec.getHeight();

		g.drawString(advancement, width - (15 + textWidth), height + textHeight);
	}

	public void setBossLifeBar(Graphics g, int width, int height) {
		String name = Boss.n.toString();
		
		Rectangle2D rec = g.getFontMetrics().getStringBounds(name, g);
		int textWidth = (int) rec.getWidth();
		
		Color col = new Color(192, 0, 0);
		g.setColor(col);
		g.drawString(name, (width / 2) - (textWidth / 2), 180);
		
		g.drawRoundRect(width / 8, 145, width - (width / 4), 10, 10, 10);
		g.drawRoundRect(width / 8 - 1, 144, width - (width / 4) + 2, 12, 10, 10);
		
		Color col2 = new Color(233, 0, 0);
		g.setColor(col2);
		g.fillRoundRect(width / 8 + 1, 146,
				(int) ((width - width / 4 - 1) / ((float) EntitiesConst.BOSS_HEALTH / (float) Boss.h)), 9, 10, 10);

	}
	
	public void showDeadMessage(Graphics g, int width, int height) {
		String deathMessage = "Vous êtes mort !";
		
		Font f = new Font(null, 1, 100);
		g.setFont(f);
		
		Rectangle2D rec = g.getFontMetrics().getStringBounds(deathMessage, g);
		int textWidth = (int) rec.getWidth();
		int textHeight = (int) rec.getHeight();
		
		g.setColor(new Color(0,0,0,175));
		g.fillRect(0, (height / 3), width, (height / 3));
		g.setColor(Color.red);
		g.drawString(deathMessage, (width / 2) - (textWidth / 2), (height / 2) + (textHeight) / 2);
	}
		
	public void showWinMessage(Graphics g, int width, int height) {
		String winMessage = "Victoire !";
		
		Font f = new Font(null, 1, 100);
		g.setFont(f);
		
		Rectangle2D rec = g.getFontMetrics().getStringBounds(winMessage, g);
		int textWidth = (int) rec.getWidth();
		int textHeight = (int) rec.getHeight();
		
		g.setColor(new Color(0,0,0,175));
		g.fillRect(0, (height / 3), width, (height / 3));
		g.setColor(Color.red);
		g.drawString(winMessage, (width / 2) - (textWidth / 2), (height / 2) + (textHeight) / 2);
	}
	
	public void setDungeonEntrance(Graphics g, int width, int height) {
		String cantEnter = "Vous ne pouvez pas entrer dans le donjon sans le pouvoir du feu !";
		
		Font f = new Font(null, 1, 40);
		g.setFont(f);
		g.setColor(Color.red);
		
		Rectangle2D rec = g.getFontMetrics().getStringBounds(cantEnter, g);
		int textWidth = (int) rec.getWidth();
		int textHeight = (int) rec.getHeight();
		
		g.drawString(cantEnter, (width / 2) - (textWidth / 2), (height / 4) + (textHeight) / 2);
	}

	public void paint(Graphics g) {
		int width = EntitiesConst.GAME.m_frame.getWidth();
		int height = EntitiesConst.GAME.m_frame.getHeight();

		Font f = new Font(null, 0, 25);
		g.setFont(f);

		g.setColor(Color.blue);
		g.drawRect(7, 7, 55, 55);
		g.drawImage(melee, 5, 7, 60, 60, m_frame);
		// TODO: Mettre le nombre de vie max en gris (Besoind de l'inforamtion de vie
		// max)

		int incJ11 = 63;
		int incJ12 = 63;
		for (int i = 0; i < j1.maxHealth; i++) {
			if (i < 10) {
				g.drawImage(blackHeart, 5 + incJ11, 7, 25, 25, null);
				incJ11 += 30;
			} else {
				g.drawImage(blackHeart, 5 + incJ12, 35, 25, 25, null);
				incJ12 += 30;
			}
		}

		incJ11 = 63;
		incJ12 = 63;
		for (int i = 0; i < j1.health; i++) {
			if (i < 10) {
				g.drawImage(blueHeart, 5 + incJ11, 7, 25, 25, null);
				incJ11 += 30;
			} else {
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
		g.drawImage(range, width - 64, 7, 60, 60, m_frame);

		int inc1 = 87;
		int inc2 = 87;
		for (int i = 0; i < j2.maxHealth; i++) {
			if (i < 10) {
				g.drawImage(blackHeart, width - (5 + inc1), 7, 25, 25, null);
				inc1 += 30;
			} else {
				g.drawImage(blackHeart, width - (5 + inc2), 35, 25, 25, null);
				inc2 += 30;
			}
		}

		inc1 = 87;
		inc2 = 87;
		for (int i = 0; i < j2.health; i++) {
			if (i < 10) {
				g.drawImage(redHeart, width - (5 + inc1), 7, 25, 25, null);
				inc1 += 30;
			} else {
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

		if (Hero.firePowerUnlocked) {
			g.drawImage(power, 7, 130, 25, 25, m_frame);
			g.drawImage(power, width - (7 + 25), 130, 25, 25, m_frame);
		}

		setCoin(g, f, width, height);

		setBomb(g, f, width, height);

		setLevel(g, f, width, height);

		if (VillagerGirl.started) {
			Font fQuest = new Font(null, 1, 20);
			g.setFont(fQuest);
			g.setColor(Color.magenta);
			setBushQuest(g, fQuest, width, height / 3);
		}

		if (EntitiesConst.MAP instanceof MazeMap) {
			setTimer(g);
		}
		
		if (EntitiesConst.GAME.paintDead) {
			showDeadMessage(g, width, height);
		}
		
		if (Hero.tryToEnterDungeon) {
			setDungeonEntrance(g, width, height);
		}
		
		if (EntitiesConst.GAME.victory_message) {
			showWinMessage(g, width, height);
		}

		if (EntitiesConst.MAP instanceof DungeonMap) {
			if (!DungeonMap.finish) {
				Font fQuest = new Font(null, 1, 20);
				g.setFont(fQuest);
				g.setColor(Color.magenta);
				setTorchQuest(g, width, height / 3);
			}
			else {
				g.setFont(f);
				setBossLifeBar(g, width, height);
			}
		}

		return;
	}

}
