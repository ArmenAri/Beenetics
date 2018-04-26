package fr.armenari.beenetics.main.items;

import java.util.HashMap;

public class Vial extends Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7808853508681836228L;
	private HashMap<String, Float> trait;
	private boolean isEmpty;

	public Vial(boolean isEmpty) {
		super("Vial", new float[] { 1, 1, 1, 1 }, 120, true, null);
		this.isEmpty = isEmpty;
	}

	public HashMap<String, Float> getTrait() {
		return trait;
	}

	public void setTrait(HashMap<String, Float> trait) {
		this.trait = trait;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}
}
