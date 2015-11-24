package StarTrek;

import java.util.Random;

public class Util {
	public static Random generator = new Random();

	public static int rnd(int maximum) {
		return generator.nextInt(maximum);
	}

}