package info3.game.entity;

import info3.game.automata.Aut_Automaton;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Miner extends Villager {

	public Miner(Location l) {
		super(l);
		this.name = "Villager";
		this.dialogs.add("Salut ! Je suis Billy-Boy, fils \nde mineur.");
		this.dialogs.add("Je peux te vendre une bombe mais \nne dis rien à mon père...");
		this.dialogs.add("Je peux te la vendre pour 50 pièces \nd'or mais visiblement tu es trop pauvre.");
		this.dialogs.add("Dommage, reviens me voir quand tu en \nauras au moins 50.");
		// TODO Auto-generated constructor stub
		this.sprites = ImagesConst.MINER;
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
	}

}
