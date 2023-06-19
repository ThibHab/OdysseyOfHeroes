package info3.game.entity;

import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class CaveWall extends DecorElement {
	
	public CaveWall(Location l) {
		super();
		this.name = "Cave Wall";
		this.location = l;

		// --- TODO manage automaton ---
		this.automaton = null;
		this.currentState = null;
		// -----------------------------

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.CAVE_WALL;
		this.imageIndex = 0;
		// -----------------------------------
		
		this.width = 1;
		this.height = 1;
		
		this.scale = EntitiesConst.CAVE_WALL_SCALE;
	}

}
