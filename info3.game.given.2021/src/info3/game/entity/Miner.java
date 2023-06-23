package info3.game.entity;

import java.util.LinkedList;

import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Direction;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class Miner extends Villager {

	public LinkedList<String> sellingDialogs;
	public int sellingDialogsIndex;
	public String sellingDialog;
	public boolean sold;

	public Miner(Location l) {
		super(l);
		this.sold = false;
		this.sellingDialogs = new LinkedList<>();
		this.sellingDialogsIndex = 0;
		this.sellingDialog = "50 pièces d'or pour une bombe, \ncrois-moi c'est une bonne affaire ! \n Fais en bon usage !";
		this.name = "Villager";
		this.dialogs.add("Salut ! Je suis Billy-Boy, fils \nde mineur.");
		this.dialogs.add(
				"Je peux te vendre une bombe pour \n50 pièces d'or si tu es interessé, \nmais surtout ne dis \nrien à mon père...");
		this.dialogs.add("Ah... tu n'as pas un montant \nde pièces d'or suffisant.");
		this.dialogs.add("Reviens me voir quand ce sera \nle cas !");
		this.sellingDialogs
				.add("50 pièces d'or pour une bombe, \ncrois-moi c'est une bonne affaire ! \n Fais en bon usage !");
		// TODO Auto-generated constructor stub
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		Aut_Direction dirs[] = new Aut_Direction[] { Aut_Direction.S, Aut_Direction.N, Aut_Direction.E,
				Aut_Direction.W };
		Action acts[] = new Action[] { Action.S, Action.M };
		this.anim = new Animation(this, ImagesConst.MINER, dirs, acts);
	}
	
	@Override
	public int getNbActionSprite(Action a) {
		switch (a) {
		case M:
			return AnimConst.MINER_M;
		case S:
			return AnimConst.MINER_S;
		default:
			return 0;
		}
	}

	@Override
	public void talks() {
		if (Hero.coins >= 50) {
			for (int i = 0; i < EntitiesConst.MAP.bubbles.size() ; i++) {
				SpeechBubble bubble = EntitiesConst.MAP.bubbles.get(i);
				if (bubble.v == this) {
					EntitiesConst.MAP.bubbles.remove(i);
				}
			}
			if(!this.sold) {
				EntitiesConst.MAP.bubbles.add(new SpeechBubble(this, this.sellingDialog));
				Hero.coins -= 50;
				this.sold = true;
			}else {
				this.sold = false;
			}
		} else {
			if(!this.sold) {
				super.talks();
			}else {
				for (int i = 0; i < EntitiesConst.MAP.bubbles.size() ; i++) {
					SpeechBubble bubble = EntitiesConst.MAP.bubbles.get(i);
					if (bubble.v == this) {
						EntitiesConst.MAP.bubbles.remove(i);
					}
				}
				this.sold = false;
			}
		}
	}

	@Override
	public void Move(Aut_Direction d) {
		float x = this.location.getX();
		float y = this.location.getY();
		Aut_Direction dir = d.rightDirection(this);
		if (x > 35 && dir == Aut_Direction.E || x < 25 && dir == Aut_Direction.W || y > 35 && dir == Aut_Direction.S
				|| y < 25 && dir == Aut_Direction.N) {
			dir = Aut_Direction.B;
			dir = dir.rightDirection(this);
			super.Move(dir);
		} else {
			super.Move(d);
		}
			for (int i = 0; i < EntitiesConst.MAP.bubbles.size(); i++) {
				SpeechBubble bubble = EntitiesConst.MAP.bubbles.get(i);
				if (bubble.v == this) {
					EntitiesConst.MAP.bubbles.remove(i);
				}
			}
		this.dialogIndex = 0;
	}

}
