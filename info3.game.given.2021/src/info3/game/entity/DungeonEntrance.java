package info3.game.entity;

import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Category;
import info3.game.constants.Action;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class DungeonEntrance extends DecorElement {
	public DungeonEntrance(Location location) {
		super();
		this.name = "DungeonEntrance";
		this.location = location;
		this.category = Aut_Category.G;

		// --- TODO manage automaton ---
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		// -----------------------------

		Action acts[] = new Action[] { Action.S };
		this.anim = new Animation(this,ImagesConst.DUNGEON_ENTRANCE, null, acts);
		// -----------------------------------

		this.width = 1;
		this.height = 1;

		if (this.location != null) {
			this.hitbox = new Hitbox(this, (float) 0.90, (float) 0.90);
		}

		this.scale = EntitiesConst.DUNGEON_ENTRANCE_SCALE;
	}

}
