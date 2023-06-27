package animations;

import info3.game.automata.Aut_Direction;
import info3.game.constants.AnimConst;
import info3.game.constants.ImagesConst;
import info3.game.entity.Location;

public class BloodEffect extends Effect {
	public BloodEffect(Location l, Aut_Direction d) {
		super(l, d);
		if (d == Aut_Direction.E || d == Aut_Direction.W) {
			this.sprites = Effect.rotateckwise(ImagesConst.BLOOD_EFFECT);
		} else {
			this.sprites = ImagesConst.BLOOD_EFFECT;
		}
		this.totalTime = AnimConst.TIME_BLOOD_EFFECT;
		this.height = 0.7f;
		this.width = 0.7f;
	}

	@Override
	public int getTrueWidth(int tileSize) {
		if (dir != null && dir == Aut_Direction.S) {
			return (int) -(this.width * tileSize);
		} else {
			return (int) (this.width * tileSize);
		}
	}

	public int getPosX(Location pixel, float shiftXY, int tileSize) {
		if (dir != null && dir == Aut_Direction.W) {
			return (int) (pixel.getX() - shiftXY + 0.8 * tileSize);
		} else {
			return (int) (pixel.getX() - shiftXY);
		}
	}

	@Override
	public int getPosY(Location pixel, float shiftXY, int tileSize) {
		if (dir != null && dir == Aut_Direction.S) {
			return (int) (pixel.getY() - shiftXY + 0.7 * tileSize);
		} else {
			return (int) (pixel.getY() - shiftXY);
		}
	}
}
