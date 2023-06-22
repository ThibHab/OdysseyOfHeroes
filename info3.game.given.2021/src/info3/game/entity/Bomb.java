package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.automata.Aut_Automaton;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Bomb extends Entity {

	public int timer;

	public Bomb(Location loc) {
		super();

		this.name = "Bomb";
		this.location = loc;

		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;

		this.sprites = ImagesConst.BOMB;
		this.imageIndex = 0;

		this.scale = 1;
		timer = EntitiesConst.BOMB_TIMER;
		EntitiesConst.MAP.createBomb((int) loc.getX(), (int) loc.getY(), this);
	}

	public void tick(long elapsed) {
		this.automaton.step(this, EntitiesConst.GAME);
		timer -= elapsed;
		System.out.println(timer);
	}

	public void paint(Graphics g, int TileSize, float screenPosX, float screenPosY) {
		BufferedImage img = sprites[0];
		g.drawImage(img, (int) screenPosX, (int) screenPosY, TileSize, TileSize, null);
		if (EntitiesConst.GAME.debug) {
			int diff = (int) (TileSize * (2 * EntitiesConst.BOMB_RADIUS - 1)) / 2;
			g.drawOval((int) (screenPosX - diff), (int) (screenPosY - diff),
					(int) (TileSize * EntitiesConst.BOMB_RADIUS * 2), (int) (TileSize * EntitiesConst.BOMB_RADIUS * 2));
		}
	}

}
