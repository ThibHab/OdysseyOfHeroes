package animations;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import info3.game.automata.Aut_Direction;
import info3.game.constants.EntitiesConst;
import info3.game.entity.Location;
import info3.game.map.Map;

public abstract class Effect {
	BufferedImage[] sprites;
	long totalTime;
	long incrTime;
	Location location;
	int index;
	Aut_Direction dir;
	float height;
	float width;

	public Effect(Location l, Aut_Direction d) {
		if (d != null) {
			dir = d;
			switch (this.dir) {
			case N:
				location = new Location(l.getX(), l.getY() + 0.5f);
				break;
			case S:
				location = new Location(l.getX(), l.getY() - 0.5f);
				break;
			case E:
				location = new Location(l.getX() - 0.5f, l.getY());
				break;
			case W:
				location = new Location(l.getX() + 0.5f, l.getY());
				break;
			default:
				location = l;
			}
		} else {
			location = l;
		}
		this.incrTime = 0;
		this.index = 0;
		EntitiesConst.MAP.effects.add(this);
	}

	public void step(long elapsed) {
		incrTime += elapsed;
		if (this.nextAnim(elapsed)) {
			if (index + 1 < sprites.length - 1)
				index++;
			else
				((Map) EntitiesConst.GAME.map).effects.remove(this);
		}
	}

	private boolean nextAnim(long elapsed) {
		long divide, nb_sprite;
		nb_sprite = sprites.length - 1;
		if (nb_sprite > 0)
			divide = totalTime / nb_sprite;
		else
			divide = 1;
		return (incrTime - elapsed) / divide < incrTime / divide;
	}

	public void paint(Graphics g, int tileSize, float screenPosX, float screenPosY) {
		BufferedImage img = sprites[index];
		Location pixel = EntitiesConst.GAME.render.gridToPixel(this.location, true);
		int height = this.getTrueHeight(tileSize);
		int width = this.getTrueWidth(tileSize);
		float shiftXY = ((this.height - 1) / 2) * tileSize;
		int positionX = getPosX(pixel, shiftXY, tileSize);
		int positionY = getPosY(pixel, shiftXY, tileSize);
		g.drawImage(img, positionX, positionY, height, width, null);
	}

	public int getTrueHeight(int tileSize) {
		if (dir != null && dir == Aut_Direction.W) {
			return (int) -(this.width * tileSize);
		} else {
			return (int) (this.width * tileSize);
		}
	}

	public int getTrueWidth(int tileSize) {
		if (dir != null && dir == Aut_Direction.N) {
			return (int) -(this.width * tileSize);
		} else {
			return (int) (this.width * tileSize);
		}
	}

	public int getPosX(Location pixel, float shiftXY, int tileSize) {
		if (dir != null && dir == Aut_Direction.W) {
			return (int) (pixel.getX() - shiftXY + tileSize);
		} else {
			return (int) (pixel.getX() - shiftXY);
		}
	}

	public int getPosY(Location pixel, float shiftXY, int tileSize) {
		if (dir != null && dir == Aut_Direction.N) {
			return (int) (pixel.getY() - shiftXY + tileSize);
		} else {
			return (int) (pixel.getY() - shiftXY);
		}
	}

	public static BufferedImage[] rotateckwise(BufferedImage[] src) {
		int width = src[0].getWidth();
		int height = src[0].getHeight();
		BufferedImage[] dest = new BufferedImage[src.length];

		for (int i = 0; i < src.length; i++) {
			dest[i] = new BufferedImage(height, width, src[0].getType());

			Graphics2D graphics2D = dest[i].createGraphics();
			graphics2D.translate((height - width) / 2, (height - width) / 2);
			graphics2D.rotate(Math.PI / 2, height / 2, width / 2);
			graphics2D.drawRenderedImage(src[i], null);
		}
		return dest;
	}
}
