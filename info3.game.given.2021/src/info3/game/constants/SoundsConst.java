package info3.game.constants;

import java.io.RandomAccessFile;

import info3.game.sound.RandomFileInputStream;

public class SoundsConst {
	
	public static RandomFileInputStream SWORD_SOUND;

	public SoundsConst() {
		// TODO Auto-generated constructor stub
		try {
			SWORD_SOUND = new RandomFileInputStream(new RandomAccessFile("resources/sword.ogg", "r"));
		}catch(Throwable th) {
			th.printStackTrace(System.err);
			System.exit(-1);
		}
		
	}

}
