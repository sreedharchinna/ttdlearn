package StarTrek;

import Untouchables.WebGadget;

public class Torpedoes extends Weapon{

	private int torpedoes = 8;
	
	public void setTorpedoes(int value) {
		torpedoes = value;
	}

	public int getTorpedoes() {
		return torpedoes;

	}

	public int fireWeapon(WebGadget wg, int energy) {
		int e = fireWeapon(new Galaxy(wg), energy);
		return e;
	}

	public int fireWeapon(Galaxy wg, int energy) {

		if (wg.parameter("command").equals("phaser")) {
			int amount = Integer.parseInt(wg.parameter("amount"));
			Klingon enemy = (Klingon) wg.variable("target");
			if (energy >= amount) {
				int distance = enemy.distance();
				if (distance > 4000) {
					wg.writeLine("Klingon out of range of phasers at " + distance + " sectors...");
				} else {
					int damage = amount - (((amount / 20) * distance / 200) + Util.rnd(200));
					if (damage < 1)
						damage = 1;
					wg.writeLine("Phasers hit Klingon at " + distance + " sectors with " + damage + " units");
					if (damage < enemy.getEnergy()) {
						enemy.setEnergy(enemy.getEnergy() - damage);
						wg.writeLine("Klingon has " + enemy.getEnergy() + " remaining");
					} else {
						wg.writeLine("Klingon destroyed!");
						enemy.delete();
					}
				}
				energy = energy - amount;

			} else {
				wg.writeLine("Insufficient energy to fire phasers!");
			}

		} else if (wg.parameter("command").equals("photon")) {
			Klingon enemy = (Klingon) wg.variable("target");
			if (getTorpedoes() > 0) {
				int distance = enemy.distance();
				if ((Util.rnd(4) + ((distance / 500) + 1) > 7)) {
					wg.writeLine("Torpedo missed Klingon at " + distance + " sectors...");
				} else {
					int damage = 800 + Util.rnd(50);
					wg.writeLine("Photons hit Klingon at " + distance + " sectors with " + damage + " units");
					if (damage < enemy.getEnergy()) {
						enemy.setEnergy(enemy.getEnergy() - damage);
						wg.writeLine("Klingon has " + enemy.getEnergy() + " remaining");
					} else {
						wg.writeLine("Klingon destroyed!");
						enemy.delete();
					}
				}
				setTorpedoes(getTorpedoes() - 1);

			} else {
				wg.writeLine("No more photon torpedoes!");
			}
		}
		return energy;
	}	
}