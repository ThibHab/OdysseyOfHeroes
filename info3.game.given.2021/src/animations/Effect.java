package animations;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.automata.Aut_Direction;
import info3.game.constants.EntitiesConst;
import info3.game.entity.Location;

public abstract class Effect {
	BufferedImage[] sprites;
	long totalTime;
	long incrTime;
	Location location;
	int index;
	Aut_Direction dir;
	int scale;

	public Effect(Location l, Aut_Direction d) {
		if (d != null)
			dir = d;
		location = l;
		this.incrTime = 0;
		this.index = 0;
		this.scale = 1;
		EntitiesConst.MAP.effects.add(this);
	}

	public void step(long elapsed) {
		incrTime += elapsed;
		if (this.nextAnim(elapsed)) {
			index++;
		}
	}

	private boolean nextAnim(long elapsed) {
		long divide, nb_sprite;
		nb_sprite = sprites.length;
		if (nb_sprite > 0)
			divide = totalTime / nb_sprite;
		else
			divide = 1;
		return (incrTime - elapsed) / divide < incrTime / divide;
	}

	public void paint(Graphics g, int tileSize, float screenPosX, float screenPosY) {
		BufferedImage img = sprites[index];
		Location pixel = EntitiesConst.GAME.render.gridToPixel(location, true);
		int dimension = (int) (scale * tileSize);
		float shiftXY = ((scale - 1) / 2) * tileSize;
		int positionX = (int) (pixel.getX() - shiftXY);
		int positionY = (int) (pixel.getY() - shiftXY);
		g.drawImage(img, positionX, positionY, dimension, dimension, null);
	}

}
