package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.automata.Aut_Automaton;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;
import info3.game.map.Map;

public class Bomb extends Entity {
	public Entity owner;
	public int timer;

	public Bomb(Location loc, Entity owner) {
		super();

		this.owner = owner;
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
	
	@Override
	public void Explode() {
		Map map = EntitiesConst.MAP;
		for (int i = 0; i < EntitiesConst.BOMB_RADIUS * 2 + 1; i++) {
			for (int j = 0; j < EntitiesConst.BOMB_RADIUS * 2 + 1; j++) {
				Entity entity = EntitiesConst.MAP_MATRIX[(int) (this.location.getX() - 2 + i + map.lenX)
						% map.lenX][(int) (this.location.getY() - 2 + j + map.lenY) % map.lenY].entity;
				if (entity != null && circleIntersect(this.location, entity, EntitiesConst.BOMB_RADIUS)) {
					entity.takeDamage(this.owner);
				}
			}
		}
		this.die(this.owner);
		// TODO delete destroyable rocks
		// TODO add explode method for animation (view)
	}
}
