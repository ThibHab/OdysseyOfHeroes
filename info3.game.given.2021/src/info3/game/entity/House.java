package info3.game.entity;

import java.awt.Graphics;
import info3.game.automata.Aut_Automaton;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class House extends DecorElement {
	public House(Location l) {
		super();
		this.name = "House";
		this.location = l;

		// --- TODO manage automaton ---
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		// -----------------------------

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.HOUSE;
		this.imageIndex = 0;
		// -----------------------------------
		
		this.width = 3;
		this.height = 3;
		
		this.scale = EntitiesConst.HOUSE_SCALE;
	}
	
	public void paint(Graphics g, int tileSize, float screenPosX, float screenPosY) {
		
	}
}
