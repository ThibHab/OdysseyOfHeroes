package info3.game.entity;

import info3.game.automata.Aut_Automaton;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class MazeWall extends DecorElement {
	public MazeWall(Location l) {
		super();
		this.name = "CaveWall";
		this.location = l;

		// TODO set walls unbreakable, but keep this idea for a secret place room in the labyrinth ?
		
		// --- TODO manage automaton ---
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		// -----------------------------

		// --- TODO manage sprite properly ---
		this.sprites = ImagesConst.MAZE_WALL;
		this.imageIndex = 0;
		// -----------------------------------
		
		this.width = 1;
		this.height = 1;
		
		this.scale = EntitiesConst.MAZE_WALL_SCALE;
	}

}
