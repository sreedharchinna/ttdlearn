package gameclient;

import java.io.IOException;
import StarTrek.Game;
import StarTrek.Klingon;
import StarTrek.Weapon;
import StarTrek.Torpedoes;

/**
 * 
 * Note: SampleClient is UNTOUCHABLE! It represents one of hundreds of Game
 * clients.
 * 
 */
public class SampleClient {
	public static void main(String args[]) {
		System.out.println("Simple Star Trek");
		WebGadget wg = new WebGadget("phaser", "1000", new Klingon());
		Game g = new Game();
		Weapon w1 = new Torpedoes();
		g.setEnergy(w1.fireWeapon(wg, g.EnergyRemaining()));
		waitForUserToEndGame();
	}

	private static void waitForUserToEndGame() {
		try {
			System.in.read();
		} catch (IOException e) {
		}
	}
}
