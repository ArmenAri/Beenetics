package fr.armenari.beenetics.main.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import fr.armenari.beenetics.main.utils.BeeInitializer;

public class Bee extends Item {
	
	Random rn = new Random();

	/**
	 * 
	 */
	private static final long serialVersionUID = 3270549073031784679L;
	private int lifeSpan;
	private float speed;
	private float pollination;
	private int fertility;
	// private int[] area;
	private boolean isPrincess;
	private ArrayList<String> using;
	private ArrayList<String> with;
	private boolean isAnalysed;

	public static HashMap<String, ArrayList<Bee>> drones = BeeInitializer.readDronesFile();
	public static HashMap<String, ArrayList<Bee>> queens = BeeInitializer.readQueensFile();

	public Bee(String name, float price) {
		super(name, new float[] { 0, 0, 0, 1 }, price, true, null);
		this.lifeSpan = 50;
		this.speed = 50;
		this.pollination = 50;
		this.fertility = 50;
		// this.area = new int[] { 3, 3 };
		this.isPrincess = false;
	}

	public Bee(String name, boolean isPrincess, float price) {
		super(name, new float[] { 0, 0, 0, 1 }, price, true, null);
		this.lifeSpan = 50;
		this.speed = 50;
		this.pollination = 50;
		this.fertility = 50;
		// this.area = new int[] { 3, 3 };
		this.isPrincess = isPrincess;
		this.getTier();
	}

	public Bee(String name, int lifeSpan, float speed, float pollination, int fertility,
			/* int[] area, */ float[] color, boolean isPrincess, float price, ArrayList<String> using,
			ArrayList<String> with, boolean isAnalysed, String seller, int id) {
		super(name, color, price, true, null);
		this.lifeSpan = lifeSpan;
		this.speed = speed;
		this.pollination = pollination;
		this.fertility = fertility;
		// this.area = area;
		this.isPrincess = isPrincess;
		this.using = using;
		this.with = with;
		this.isAnalysed = isAnalysed;
		this.seller = seller;
		this.id = id;
	}
	
	public Bee(String name, int lifeSpan, float speed, float pollination, int fertility,
			/* int[] area, */ float[] color, boolean isPrincess, float price, ArrayList<String> using,
			ArrayList<String> with, boolean isAnalysed, String seller) {
		super(name, color, price, true, null);
		this.lifeSpan = lifeSpan;
		this.speed = speed;
		this.pollination = pollination;
		this.fertility = fertility;
		// this.area = area;
		this.isPrincess = isPrincess;
		this.using = using;
		this.with = with;
		this.isAnalysed = isAnalysed;
		this.seller = seller;
		int n = 100000 - 1 + 1;
		int i = rn.nextInt() % n;
		int randomNum = 1 + i;
		this.id = randomNum;
	}

	public Bee() {
		this("UNKNOWN", 0);
	}

	public String getTier() {
		for (HashMap.Entry<String, ArrayList<Bee>> pair : queens.entrySet()) {
			for (int i = 0; i < pair.getValue().size(); i++) {
				if (pair.getValue().get(i).getName().equals(this.getName())) {
					return pair.getKey();
				}
			}
		}
		return null;
	}

	public int getLifeSpan() {
		return lifeSpan;
	}

	public float getSpeed() {
		return speed;
	}

	public float getPollination() {
		return pollination;
	}

	public int getFertility() {
		return fertility;
	}

	// public int[] getArea() {
	// return area;
	// }

	public boolean isPrincess() {
		return isPrincess;
	}

	public ArrayList<String> getUsing() {
		return this.using;
	}

	public ArrayList<String> getWith() {
		return this.with;
	}

	public boolean isAnalysed() {
		return this.isAnalysed;
	}

	public void setAnalysed(boolean value) {
		this.isAnalysed = value;
	}

	public void setLifeSpan(float f) {
		this.lifeSpan = (int) f;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void setPollination(float pollination) {
		this.pollination = pollination;
	}

	public void setFertility(float f) {
		this.fertility = (int) f;
	}
}
