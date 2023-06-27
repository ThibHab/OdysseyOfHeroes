package info3.game.entity;

import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Category;
import info3.game.automata.Aut_Direction;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Torch extends DecorElement {

	public boolean lit=false;
	
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
		
		Action acts[] = new Action[] { Action.S };
		this.anim = new Animation(this,ImagesConst.TORCH, null, acts);		
		this.width = 1;
		this.height = 1;
		
		if(this.location != null) {
			this.hitbox = new Hitbox(this, (float)0.90, (float)0.90);
		}
		this.health = 1;
		this.lit=false;
		
	}
	
	@Override
	public void takeDamage(Entity ent) {
		if (ent instanceof Range) {
			this.health -= ent.weaponDamage;
			this.anim.sprites = ImagesConst.loadSprite("TorchLight", 2, 3);
		}
	}
	
	@Override
	public void Pop(Aut_Direction d, Aut_Category c) {
		//TODO UPDATE ANIM
		//this.imageIndex = 1;
		this.lit=true;
	}
	
	@Override
	public int getNbActionSprite(Action a) {
		switch (a) {
		case M:
			return 0;
		case H:
			return 0;
		case T:
			return 0;
		case D:
			return 0;
		case S:
			return AnimConst.TORCH_S;
		default:
			return 0;
		}
	}

	@Override
	public int totSrpitePerDir() {
		return AnimConst.TORCH_S;
	}

}
