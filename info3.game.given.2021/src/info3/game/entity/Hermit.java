package info3.game.entity;

import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Direction;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Hermit extends Villager {

	public Hermit(Location l) {
		super(l);
		this.name = "Hermit";
		this.dialogs.add("Je suis Pascal Sicard, \nl'hermite suprême de la forêt");
		this.dialogs.add("Tiens voilà le feu pour \ntuer le dragon !");
		this.dialogs.add("Bon courage ! Moi je vais \nme recoucher");
		// TODO Auto-generated constructor stub
		this.sprites = ImagesConst.HERMIT;
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void talks() {
		super.talks();
		if (this.dialogIndex >= 2) {
			Hero.firePowerUnlocked = true;
			this.dialogs.clear();
		}
	}

}
