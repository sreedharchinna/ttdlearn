package StarTrek;

import gameclient.WebGadget;

public abstract class Weapon {
	public abstract int fireWeapon(WebGadget wg,int energy);
	public abstract int fireWeapon(Galaxy g, int energy);
}