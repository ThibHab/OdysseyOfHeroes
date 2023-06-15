package info3.game.entity;

import info3.game.constants.ImagesConst;

public class Coin extends Item {
	static int CoinValue = 1;

	public Coin(Location l) {
		super();
		this.name = "Coin";
		this.location = l;

		// --- TODO manage automaton ---
		this.automaton = null;
		this.currentState = null;
		// -----------------------------

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.COIN ;
		this.imageIndex = 0;
		// -----------------------------------
	}
}
