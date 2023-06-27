package info3.game.entity;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Direction;
import info3.game.constants.Action;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;
import info3.game.map.Tile;

public class House extends TransparentDecorElement {

	public House(Location l) {
		super(3);
		this.name = "House";
		this.location = l;
		this.hitbox.update();

		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;

		Action acts[] = new Action[] { Action.S };
		this.anim = new Animation(this, ImagesConst.HOUSE, null, acts);

		this.width = 3;
		this.height = 3;

		this.scale = EntitiesConst.HOUSE_SCALE;
	}

	@Override
	public void Move(Aut_Direction d) {
		if (!this.frozen) {
			this.frozen = true;
			if (d != null) {
				this.direction = d;
			}
			this.direction = d;

			if (this.action != Action.M) {
				this.action = Action.M;
			}
			this.anim.changeAction(action);

			this.destLocation = new Location(this.location.getX(), this.location.getY());
			this.originLocation = new Location(this.location.getX(), this.location.getY());
			this.relativeMouv = new Location(0, 0);

			Location topLeftOri = new Location(
					(this.location.getX() + EntitiesConst.MAP.lenX - 1) % EntitiesConst.MAP.lenX,
					(this.location.getY() + EntitiesConst.MAP.lenY - 1) % EntitiesConst.MAP.lenY);
			Location leftOri = new Location(
					(this.location.getX() + EntitiesConst.MAP.lenX - 1) % EntitiesConst.MAP.lenX, this.location.getY());
			Location topOri = new Location(this.location.getX(),
					(this.location.getY() + EntitiesConst.MAP.lenY - 1) % EntitiesConst.MAP.lenY);
			Location topRightOri = new Location(
					(this.location.getX() + EntitiesConst.MAP.lenX + 1) % EntitiesConst.MAP.lenX,
					(this.location.getY() + EntitiesConst.MAP.lenY - 1) % EntitiesConst.MAP.lenY);
			Location rightOri = new Location(
					(this.location.getX() + EntitiesConst.MAP.lenX + 1) % EntitiesConst.MAP.lenX, this.location.getY());

			Location topLeftDest = new Location(topLeftOri.getX(), topLeftOri.getY());
			Location leftDest = new Location(leftOri.getX(), leftOri.getY());
			Location topDest = new Location(topOri.getX(), topOri.getY());
			Location topRightDest = new Location(topRightOri.getX(), topRightOri.getY());
			Location rightDest = new Location(rightOri.getX(), rightOri.getY());

			switch (d) {
			case N:
				this.destLocation.setY((this.location.getY() + EntitiesConst.MAP.lenY - 1) % EntitiesConst.MAP.lenY);
				this.relativeMouv.setY(-1);

				topLeftDest.setY((topLeftOri.getY() + EntitiesConst.MAP.lenY - 1) % EntitiesConst.MAP.lenY);
				leftDest.setY((leftOri.getY() + EntitiesConst.MAP.lenY - 1) % EntitiesConst.MAP.lenY);
				topDest.setY((topOri.getY() + EntitiesConst.MAP.lenY - 1) % EntitiesConst.MAP.lenY);
				topRightDest.setY((topRightOri.getY() + EntitiesConst.MAP.lenY - 1) % EntitiesConst.MAP.lenY);
				rightDest.setY((rightOri.getY() + EntitiesConst.MAP.lenY - 1) % EntitiesConst.MAP.lenY);
				break;
			case S:
				this.destLocation.setY((this.location.getY() + EntitiesConst.MAP.lenY + 1) % EntitiesConst.MAP.lenY);
				this.relativeMouv.setY(1);

				topLeftDest.setY((topLeftOri.getY() + EntitiesConst.MAP.lenY + 1) % EntitiesConst.MAP.lenY);
				leftDest.setY((leftOri.getY() + EntitiesConst.MAP.lenY + 1) % EntitiesConst.MAP.lenY);
				topDest.setY((topOri.getY() + EntitiesConst.MAP.lenY + 1) % EntitiesConst.MAP.lenY);
				topRightDest.setY((topRightOri.getY() + EntitiesConst.MAP.lenY + 1) % EntitiesConst.MAP.lenY);
				rightDest.setY((rightOri.getY() + EntitiesConst.MAP.lenY + 1) % EntitiesConst.MAP.lenY);
				break;
			case W:
				this.destLocation.setX((this.location.getX() + EntitiesConst.MAP.lenX - 1) % EntitiesConst.MAP.lenX);
				this.relativeMouv.setX(-1);

				topLeftDest.setX((topLeftOri.getX() + EntitiesConst.MAP.lenX - 1) % EntitiesConst.MAP.lenX);
				leftDest.setX((leftOri.getX() + EntitiesConst.MAP.lenX - 1) % EntitiesConst.MAP.lenX);
				topDest.setX((topOri.getX() + EntitiesConst.MAP.lenX - 1) % EntitiesConst.MAP.lenX);
				topRightDest.setX((topRightOri.getX() + EntitiesConst.MAP.lenX - 1) % EntitiesConst.MAP.lenX);
				rightDest.setX((rightOri.getX() + EntitiesConst.MAP.lenX - 1) % EntitiesConst.MAP.lenX);
				break;
			case E:
				this.destLocation.setX((this.location.getX() + EntitiesConst.MAP.lenX + 1) % EntitiesConst.MAP.lenX);
				this.relativeMouv.setX(1);

				topLeftDest.setX((topLeftOri.getX() + EntitiesConst.MAP.lenX + 1) % EntitiesConst.MAP.lenX);
				leftDest.setX((leftOri.getX() + EntitiesConst.MAP.lenX + 1) % EntitiesConst.MAP.lenX);
				topDest.setX((topOri.getX() + EntitiesConst.MAP.lenX + 1) % EntitiesConst.MAP.lenX);
				topRightDest.setX((topRightOri.getX() + EntitiesConst.MAP.lenX + 1) % EntitiesConst.MAP.lenX);
				rightDest.setX((rightOri.getX() + EntitiesConst.MAP.lenX + 1) % EntitiesConst.MAP.lenX);
				break;
			default:
				break;
			}

			Tile destTile = EntitiesConst.MAP_MATRIX[(int) destLocation.getX()][(int) destLocation.getY()];
			Tile destTileTopLeft = EntitiesConst.MAP_MATRIX[(int) topLeftDest.getX()][(int) topLeftDest.getY()];
			Tile destTileLeft = EntitiesConst.MAP_MATRIX[(int) leftDest.getX()][(int) leftDest.getY()];
			Tile destTileTop = EntitiesConst.MAP_MATRIX[(int) topDest.getX()][(int) topDest.getY()];
			Tile destTileTopRight = EntitiesConst.MAP_MATRIX[(int) topRightDest.getX()][(int) topRightDest.getY()];
			Tile destTileRight = EntitiesConst.MAP_MATRIX[(int) rightDest.getX()][(int) rightDest.getY()];

			if (destTileTopLeft.walkable && (destTileTopLeft.entity == null || destTileTopLeft.entity == this)
					&& destTileLeft.walkable && (destTileLeft.entity == null || destTileLeft.entity == this)
					&& destTileTop.walkable && (destTileTop.entity == null || destTileTop.entity == this)
					&& destTileTopRight.walkable && (destTileTopRight.entity == null || destTileTopRight.entity == this)
					&& destTileRight.walkable && (destTileRight.entity == null || destTileRight.entity == this)) {
				EntitiesConst.MAP_MATRIX[(int) topLeftOri.getX()][(int) topLeftOri.getY()].entity = null;
				EntitiesConst.MAP_MATRIX[(int) leftOri.getX()][(int) leftOri.getY()].entity = null;
				EntitiesConst.MAP_MATRIX[(int) topOri.getX()][(int) topOri.getY()].entity = null;
				EntitiesConst.MAP_MATRIX[(int) topRightOri.getX()][(int) topRightOri.getY()].entity = null;
				EntitiesConst.MAP_MATRIX[(int) rightOri.getX()][(int) rightOri.getY()].entity = null;

				EntitiesConst.MAP_MATRIX[(int) topLeftDest.getX()][(int) topLeftDest.getY()].entity = this;
				EntitiesConst.MAP_MATRIX[(int) leftDest.getX()][(int) leftDest.getY()].entity = this;
				EntitiesConst.MAP_MATRIX[(int) topDest.getX()][(int) topDest.getY()].entity = this;
				EntitiesConst.MAP_MATRIX[(int) topRightDest.getX()][(int) topRightDest.getY()].entity = this;
				EntitiesConst.MAP_MATRIX[(int) rightDest.getX()][(int) rightDest.getY()].entity = this;
				destTile.entity = this;
			} else {
				this.frozen = false;
			}
		}
	}

	@Override
	public void paint(Graphics g, int tileSize, float screenPosX, float screenPosY) {
		BufferedImage img = anim.getFrame();
		if (this.transparent) {
			Graphics2D gr = (Graphics2D) g;
			gr.setComposite(
					AlphaComposite.getInstance(AlphaComposite.SRC_OVER, EntitiesConst.HOUSE_OPACITY / this.opacityDiv));
			gr.drawImage(img, (int) (screenPosX - tileSize), (int) (screenPosY - 2 * tileSize),
					(int) (tileSize * scale * width), (int) (tileSize * scale * height), null);
			gr.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		} else {
			g.drawImage(img, (int) (screenPosX - tileSize), (int) (screenPosY - 2 * tileSize),
					(int) (tileSize * scale * width), (int) (tileSize * scale * height), null);
		}
	}
}
