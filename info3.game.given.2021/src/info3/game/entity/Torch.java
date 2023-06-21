package info3.game.entity;

import info3.game.automata.Aut_Automaton;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Torch extends DecorElement {

	public Torch(Location l) {
		super();
		this.name = "Torch";
		this.location = l;

		// --- TODO manage automaton ---
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		// -----------------------------

		// --- TODO manage sprite properly ---
		this.sprites =  ImagesConst.TORCH;
		this.imageIndex = 0;
		// -----------------------------------
		
		this.width = 1;
		this.height = 1;
		
		if(this.location != null) {
			this.hitbox = new Hitbox(this, (float)0.90, (float)0.90);
		}
		this.health = -1;
		
	}
	
	@Override
	public void takeDamage(Entity ent) {
		this.health -= ent.weaponDamage;
		if (ent instanceof Range) {
			this.imageIndex = 2;
		}
	}
	
	public void lightAround() {
		EntitiesConst.MAP_MATRIX[(int) this.location.getX()][(int) this.location.getY()].opacity = 0f;

		int x = (int) this.location.getX() + 1;
		int y = (int) this.location.getY() - 1;
		
		EntitiesConst.MAP_MATRIX[x][y].opacity = 0.5f;
		x--;
		EntitiesConst.MAP_MATRIX[x][y].opacity = 0f;
		x--;
		EntitiesConst.MAP_MATRIX[x][y].opacity = 0.5f;
		y++;
		EntitiesConst.MAP_MATRIX[x][y].opacity = 0f;
		y++;
		EntitiesConst.MAP_MATRIX[x][y].opacity = 0.5f;
		x++;
		EntitiesConst.MAP_MATRIX[x][y].opacity = 0f;
		x++;
		EntitiesConst.MAP_MATRIX[x][y].opacity = 0.5f;
		y--;
		EntitiesConst.MAP_MATRIX[x][y].opacity = 0f;
		x--;
	}

}
