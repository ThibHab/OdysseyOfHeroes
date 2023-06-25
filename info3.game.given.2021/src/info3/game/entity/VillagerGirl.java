package info3.game.entity;

import java.io.RandomAccessFile;
import java.util.LinkedList;

import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Direction;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;
import info3.game.sound.RandomFileInputStream;

public class VillagerGirl extends Villager {
	
	public static boolean started, completed;
	public LinkedList<String> completedDialogs;
	public int completedDialogsIndex;

	public VillagerGirl(Location l) {
		super(l);
		VillagerGirl.started = false;
		VillagerGirl.completed = false;
		this.completedDialogs = new LinkedList<>();
		this.completedDialogsIndex = 0;
		this.name = "Villager";
		this.dialogs.add("Oyé Oyé, jeunes aventuriers !\nBienvenue à QuoicoubehLand !");
		this.dialogs.add("Depuis que le dragon habite nos \nterres, nous ne sommes plus en \nsécurité.");
		this.dialogs.add("Les squelettes et les orcs rodent \ndans les alentours et menacent \nnotre village");
		this.dialogs.add("Pour survivre en dehors de ces murs \nil vous faudra beaucoup de courage");
		this.completedDialogs.add("Merci beaucoup pour les buissons !");
		this.completedDialogs.add("Si vous voulez trouver des potions, \ndes pièces d'or ou augmenter votre\nexpérience.. ");
		this.completedDialogs.add("vous pouvez vous rendre dans \nle labyrinthe situé au sud du village.");
		this.completedDialogs.add("Mais attention ! Ca grouille d'orcs là \ndedans !");
		this.completedDialogs.add("Si vous voulez vous confronter au \ndragon, il faudra que vous trouviez \nl'hermite caché dans la forêt, il saura\nvous aider.");
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
		if(Hero.bushesCut >= 1){
			if(!VillagerGirl.completed) {
				Hero.experience += 10;
				if (Hero.experience >= Hero.levelUp) {
					Hero.level++;
					try {
						RandomAccessFile file = new RandomAccessFile("resources/sounds/lvlup.ogg", "r");
						RandomFileInputStream fis = new RandomFileInputStream(file);
						EntitiesConst.GAME.m_canvas.playSound("lvlup",fis, 0, 0.8F);
					} catch (Throwable th) {
						th.printStackTrace(System.err);
						System.exit(-1);
					}
					Hero.experience = 0;
					Hero.levelUp = Hero.levelUp * 2;
					
					EntitiesConst.GAME.player1.updateStats();
					EntitiesConst.GAME.player2.updateStats();
				}
				Hero.coins += 20;
				VillagerGirl.completed = true;
				VillagerGirl.started = false;
			}
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
			if (dialogIndex == 4) {
				this.dialogs.clear();
				this.dialogIndex = 0;
				this.dialogs.add("Si toi et ton ami arrivez à casser 20 \nbuissons, je vous en serait redevante\net vous récompenserait de 20 \npièces d'or !");
				VillagerGirl.started = true;
			}
			super.talks();
		}
	}
	
	@Override
	public int getNbActionSprite(Action a) {
		switch (a) {
		case M:
			return AnimConst.VILLAGERGIRL_M;
		case H:
			return 0;
		case T:
			return 0;
		case D:
			return 0;
		case S:
			return AnimConst.VILLAGERGIRL_S;
		default:
			return 0;
		}
	}

	@Override
	public int totSrpitePerDir() {
		return AnimConst.VILLAGERGIRL_M;
	}
}
