package info3.game.entity;

import info3.game.automata.Aut_Automaton;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Coin extends Item {
	static int CoinValue = 1;

	public Coin(Location l) {
		super();
		this.name = "Coin";
		this.location = l;

		this.automaton = null;
		this.currentState = null;

		this.sprites = ImagesConst.COIN;
		this.imageIndex = 0;
		
		this.scale = 1;
	}
	
	@Override
	public int getStandNbSprite() {
		return AnimConst.COIN_S;
	}
}
