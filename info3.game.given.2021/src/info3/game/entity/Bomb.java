package info3.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import animations.Animation;
import animations.ExplosionEffect;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Category;
import info3.game.constants.Action;
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
		this.category = Aut_Category.D;

		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		
		Action acts[] = new Action[] { Action.S };
		this.anim = new Animation(this,ImagesConst.BOMB, null, acts);

		this.scale = 1;
		this.category = Aut_Category.D;
		timer = EntitiesConst.BOMB_TIMER;
		EntitiesConst.MAP.createBomb((int) loc.getX(), (int) loc.getY(), this);
	}


	public void paint(Graphics g, int TileSize, float screenPosX, float screenPosY) {
		BufferedImage img = anim.getFrame();
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
				if (entity != null && circleIntersect(this.location, entity, EntitiesConst.BOMB_RADIUS) && (entity instanceof Mob || entity instanceof Hero || entity.category == Aut_Category.O || entity instanceof Bush)) {
					entity.takeDamage(this.owner);
				}
			}
		}
		new ExplosionEffect(this.location);
		// TODO delete destroyable rocks
		// TODO add explode method for animation (view)
	}
}
