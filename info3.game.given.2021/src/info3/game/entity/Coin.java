package info3.game.entity;

public class Coin extends Item {
	static int CoinValue = 1;

	public Coin(Location l) {
		super();
		//TODO Set the sprite, the width and the height
		this.height = 0;
		this.width= 0;
		this.location = l;
		this.image = null;
	}
}
