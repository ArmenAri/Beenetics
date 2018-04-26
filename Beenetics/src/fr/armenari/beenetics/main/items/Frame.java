package fr.armenari.beenetics.main.items;

public class Frame extends Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5853364969019873503L;
	private int durability;
	private int augmentation;
	private int maxDurability;

	/*
	 * Frames
	 */
	public static final Frame untreatedFrame = new Frame("Untreated Frame", new float[] { 0.454f, 0.301f, 0.164f, 1 },
			100, true, 50, 10);
	public static final Frame impregnatedFrame = new Frame("Impregnated Frame",
			new float[] { 0.376f, 0.219f, 0.086f, 1 }, 200, true, 100, 15);
	public static final Frame provenFrame = new Frame("Proven Frame", new float[] { 0.674f, 0.329f, 0.023f, 1 }, 300,
			true, 175, 25);

	public Frame(String name, float[] color, float price, boolean hasInfos, int durability, int augmentation) {
		super(name, color, price, hasInfos, null);
		this.setDurability(durability);
		this.maxDurability = durability;
		this.setAugmentation(augmentation);
	}

	public int getDurability() {
		return durability;
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}

	public int getMaxDurability() {
		return this.maxDurability;
	}

	public int getAugmentation() {
		return augmentation;
	}

	public void setAugmentation(int augmentation) {
		this.augmentation = augmentation;
	}

}
