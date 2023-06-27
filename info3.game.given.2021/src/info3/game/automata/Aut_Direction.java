package info3.game.automata;

import info3.game.entity.Entity;

public enum Aut_Direction {
	N, // North
	S, // South
	W, // West
	E, // East
	F, // Front
	B, // Back
	L, // Left
	R, // Right
	H; // Here

	public Aut_Direction rightDirection(Entity e) {
		if (this != null) {
			switch (this) {
			case N:
			case S:
			case E:
			case W:
			case H:
				return this;
			case F:
				return e.direction;
			case B:
				switch (e.direction) {
				case N:
					return Aut_Direction.S;
				case S:
					return Aut_Direction.N;
				case E:
					return Aut_Direction.W;
				case W:
					return Aut_Direction.E;
				default:
					return null;
				}
			case L:
				switch (e.direction) {
				case N:
					return Aut_Direction.W;
				case S:
					return Aut_Direction.E;
				case E:
					return Aut_Direction.N;
				case W:
					return Aut_Direction.S;
				default:
					return null;
				}
			case R:
				switch (e.direction) {
				case N:
					return Aut_Direction.E;
				case S:
					return Aut_Direction.W;
				case E:
					return Aut_Direction.S;
				case W:
					return Aut_Direction.N;
				default:
					return null;
				}

			}
		}
		return null;
	}
}
