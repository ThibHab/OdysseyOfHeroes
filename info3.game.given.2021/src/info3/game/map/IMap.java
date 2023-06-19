package info3.game.map;

import java.awt.Graphics;

import info3.game.entity.*;

public interface IMap {
	void setEntityRandomly(int x, int y, int areaSize, int spaceBetween, String ent, long seed, int rareness);
}
