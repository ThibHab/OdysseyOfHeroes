package info3.game.map;

import java.awt.Graphics;

import info3.game.entity.Entity;

public interface IMap {
	void setEntityRandomly(int x, int y, int areaSize, int spaceBetween, Entity ent, long seed);
}
