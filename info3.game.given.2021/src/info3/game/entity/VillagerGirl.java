package info3.game.entity;

import java.util.LinkedList;

import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Direction;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;

public class VillagerGirl extends Villager {
	
	public LinkedList<String> completedDialogs;
	public int completedDialogsIndex;

	public VillagerGirl(Location l) {
		super(l);
		this.completedDialogs = new LinkedList<>();
		this.completedDialogsIndex = 0;
		this.name = "Villager";
		this.dialogs.add("Oyé jeune et beau aventurier !\nSi toi et ton ami arrivez à casser 20 \nbuissons, je vous en serait redevante\n et vous donnerai 20 pièces d'or !");
		this.completedDialogs.add("Merci beaucoup pour les buissons !");
		this.completedDialogs.add("Si vous voulez trouver des potions et \ndes pièces d'or ou encore augmenter \nvotre expérience, vous pouvez \nvous rendre dans le labyrinthe.");
		this.completedDialogs.add("Mais attention ! Ca grouille d'orcs là \ndedans !");
		// TODO Auto-generated constructor stub
		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;
		Aut_Direction dirs[] = new Aut_Direction[] { Aut_Direction.S, Aut_Direction.N, Aut_Direction.E,
				Aut_Direction.W };
		Action acts[] = new Action[] { Action.S, Action.M };
		this.anim = new Animation(this, ImagesConst.VILLAGERGIRL, dirs, acts);
		
	}
	
	@Override
	public int getNbActionSprite(Action a) {
		switch (a) {
		case M:
			return AnimConst.VILLAGERGIRL_M;
		case S:
			return AnimConst.VILLAGERGIRL_S;
		default:
			return 0;
		}
	}
	
	@Override
	public void Move(Aut_Direction d) {
		float x = this.location.getX();
		float y =this.location.getY();
		Aut_Direction dir = d.rightDirection(this);
		if (x > 35 && dir == Aut_Direction.E  || x < 25 && dir == Aut_Direction.W || y > 35 && dir == Aut_Direction.S || y < 25 && dir == Aut_Direction.N) {
			dir = Aut_Direction.B;
			dir = dir.rightDirection(this);
			super.Move(dir);
		}else {
			super.Move(d);
		}
			for (int i = 0; i < EntitiesConst.MAP.bubbles.size() ; i++) {
				SpeechBubble bubble = EntitiesConst.MAP.bubbles.get(i);
				if (bubble.v == this) {
					EntitiesConst.MAP.bubbles.remove(i);
				}
			}
		this.dialogIndex = 0;
	}
	
	@Override
	public void talks() {
		if(Hero.bushesCut >= 20){
			if(!this.completed) {
				Hero.experience += 10;
				if (Hero.experience >= Hero.levelUp) {
					Hero.level++;
					Hero.experience = 0;
					Hero.levelUp = Hero.levelUp * 2;
					
					EntitiesConst.GAME.player1.updateStats();
					EntitiesConst.GAME.player2.updateStats();
				}
			}
			this.completed = true;
			if (this.dialogIndex > 0) {
				for (int i = 0; i < EntitiesConst.MAP.bubbles.size() ; i++) {
					SpeechBubble bubble = EntitiesConst.MAP.bubbles.get(i);
					if (bubble.v == this) {
						EntitiesConst.MAP.bubbles.remove(i);
					}
				}
			}
			if (this.completedDialogs.size() <= this.dialogIndex) {
				for (int i = 0; i < EntitiesConst.MAP.bubbles.size() ; i++) {
					SpeechBubble bubble = EntitiesConst.MAP.bubbles.get(i);
					if (bubble.v == this) {
						EntitiesConst.MAP.bubbles.remove(i);
					}
				}
				this.dialogIndex = 0;
			}else {
				EntitiesConst.MAP.bubbles.add(new SpeechBubble(this, this.completedDialogs.get(dialogIndex++)));
			}
		}else {
			super.talks();
		}
	}
}
