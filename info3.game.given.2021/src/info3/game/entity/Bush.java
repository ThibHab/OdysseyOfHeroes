package info3.game.entity;

import java.io.RandomAccessFile;
import java.util.Random;

import animations.Animation;
import info3.game.automata.Aut_Automaton;
import info3.game.automata.Aut_Category;
import info3.game.automata.Aut_Direction;
import info3.game.constants.Action;
import info3.game.constants.AnimConst;
import info3.game.constants.EntitiesConst;
import info3.game.constants.ImagesConst;
import info3.game.sound.RandomFileInputStream;

public class Bush extends DecorElement {

	boolean destroyed;

	public Bush(Location l) {
		super();
		this.name = "Bush";
		this.location = l;
		this.health = EntitiesConst.BUSH_HEALTH;
		this.category = Aut_Category.O;

		for (Aut_Automaton next : EntitiesConst.GAME.listAutomata) {
			if (next.name.equals(name))
				automaton = next;
		}
		this.currentState = automaton.initial;

		Action acts[] = new Action[] { Action.S };
		this.anim = new Animation(this, ImagesConst.BUSH, null, acts);

		this.width = 1;
		this.height = 1;

		if (this.location != null) {
			this.hitbox = new Hitbox(this, (float) 0.90, (float) 0.90);
		}

		this.scale = EntitiesConst.BUSH_SCALE;
		this.destroyed = false;
	}

	@Override
	public void takeDamage(Entity attacker) {
		try {
			RandomAccessFile file = new RandomAccessFile("resources/sounds/bush.ogg", "r");
			RandomFileInputStream fis = new RandomFileInputStream(file);
			EntitiesConst.GAME.m_canvas.playSound("bush", fis, 0, 0.8F);
		} catch (Throwable th) {
			th.printStackTrace(System.err);
			System.exit(-1);
		}
		health -= attacker.weaponDamage;
		if (attacker instanceof Hero && VillagerGirl.started) {
			Hero.bushesCut++;
		}
		if (health <= 0) {
			EntitiesConst.MAP.deadBush.add(this);
			EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity = null;
		}
	}

	@Override
	public void waited() {
		if (EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity == null) {
			this.health = EntitiesConst.BUSH_HEALTH;
			EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity = this;
		} else {
			this.Wait(5000);
		}
	}

	@Override
	public void Egg(Aut_Direction d, Aut_Category c, int id) {
		if (d == null) {
			d = this.direction;
		}

		Location location = this.frontTileLocation(d);
		switch (c) {
		case A:
			if (id == 0) {
				Random randomA = new Random();
				id = randomA.nextInt(2) + 1;
			}
			switch (id) {
			case 1:
				Goblin gob = new Goblin(location);
				EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity = gob;
				break;
			case 2:
				Skeleton s = new Skeleton(location);
				EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity = s;
				break;
			}
			break;

		case D:
			new Bomb(location, this);
			Hero.bombs--;
			break;
		case P:
			if (id == 0) {
				Random randomP = new Random();
				id = randomP.nextInt(3) + 1;
			}
			switch (id) {
			case 1:
				Coin coin = new Coin(location);
				EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity = coin;
				break;
			case 2:
				HealingPotion hp = new HealingPotion(location);
				EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity = hp;
				break;
			case 3:
				StrengthPotion sp = new StrengthPotion(location);
				EntitiesConst.MAP_MATRIX[(int) location.getX()][(int) location.getY()].entity = sp;
				break;
			}
			break;

		default:
			break;
		}
	}

	@Override
	public int getNbActionSprite(Action a) {
		switch (a) {
		case H:
			return AnimConst.BUSH_H;
		case S:
			return AnimConst.BUSH_S;
		default:
			return 0;
		}
	}

	@Override
	public int totSrpitePerDir() {
		return AnimConst.BUSH_TOT;
	}
}
