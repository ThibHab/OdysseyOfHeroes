package info3.game.entity;

import java.io.RandomAccessFile;

import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Direction;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;
import info3.game.sound.RandomFileInputStream;

public class Hermit extends Villager {

	public Hermit(Location l) {
		super(l);
		this.name = "Hermit";
		this.dialogs.add("Enchanté jeunes guerriers. je\n suis l'hermite suprême de la forêt");
		this.dialogs.add("Pour combattre le dragon, vous \naurez besoin du pouvoir du feu.");
		this.dialogs.add("Je vous transmet ce pouvoir !");
		this.dialogs.add("Bon courage ! Je retourne méditer.");
		// TODO Auto-generated constructor stub
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		Aut_Direction dirs[] = new Aut_Direction[] { Aut_Direction.S, Aut_Direction.E, Aut_Direction.N,
				Aut_Direction.W };
		Action acts[] = new Action[] { Action.S, Action.M };
		this.anim = new Animation(this, ImagesConst.HERMIT, dirs, acts);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getNbActionSprite(Action a) {
		switch (a) {
		case M:
			return AnimConst.HERMIT_M;
		case S:
			return AnimConst.HERMIT_S;
		default:
			return 0;
		}
	}
	
	@Override
	public void talks() {
		super.talks();
		if(this.dialogIndex == 3) {
			try {
				RandomAccessFile file = new RandomAccessFile("resources/sounds/fireUnlocked.ogg", "r");
				RandomFileInputStream fis = new RandomFileInputStream(file);
				EntitiesConst.GAME.m_canvas.playSound("lvlup",fis, 0, 0.8F);
			} catch (Throwable th) {
				th.printStackTrace(System.err);
				System.exit(-1);
			}
			Range.unlockFire();
		}
		if (this.dialogIndex >= 4) {
			this.dialogs.clear();
		}
	}

	@Override
	public int totSrpitePerDir() {
		return AnimConst.HERMIT_M + AnimConst.HERMIT_S;
	}

}
