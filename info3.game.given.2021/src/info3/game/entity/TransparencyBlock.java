package info3.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

public class TransparencyBlock extends DecorElement {
	public List<TransparentDecorElement> target;

	public TransparencyBlock(int x, int y) {
		super();
		this.location = new Location(x, y);
		target = new LinkedList<>();
	}

	public void add(TransparentDecorElement tde) {
		if (!this.target.contains(tde)) {
			this.target.add(tde);
		}
	}

	public void del(TransparentDecorElement tde) {
		this.target.remove(tde);
	}

	@Override
	public void paint(Graphics g, int tileSize, float screenPosX, float screenPosY) {
		g.setColor(new Color(255, 175, 175, 150));
		g.fillRect((int) screenPosX, (int) screenPosY, tileSize, tileSize);
	}

}