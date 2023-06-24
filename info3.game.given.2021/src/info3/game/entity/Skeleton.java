package info3.game.entity;

import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Category;
import info3.game.automata.Aut_Direction;
import info3.game.constants.Action;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Skeleton extends Mob {
	public Skeleton(Location l) {
		super();
		this.name = "Skeleton";
		this.location = l;
		this.health = EntitiesConst.SKELETON_HEALTH;
		this.weaponDamage = EntitiesConst.SKELETON_DAMAGE;
		this.weaponRange = EntitiesConst.SKELETON_RANGE;
		this.speed = EntitiesConst.SKELETON_SPEED;
		this.scale = EntitiesConst.SKELETON_SCALE;

		// --- TODO manage automaton ---
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		// -----------------------------
		this.category = Aut_Category.A;

		this.category = Aut_Category.A;
		
		//TODO add sprites and actions
		Aut_Direction dirs[] = new Aut_Direction[] {};
		Action acts[] = new Action[] {};
		this.anim = new Animation(this,ImagesConst.SKELETON, dirs, acts);
	}
}
