package fr.armenari.beenetics.main.items;

import java.io.Serializable;
import java.util.Random;

public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8075848200253907177L;

	private String name;
	private float[] color;
	private float price;
	private boolean hasInfos;
	protected String seller;
	protected int id;

	Random rn = new Random();

	/*
	 * Items
	 */
	public static final Item beeswax = new Item("Beeswax", new float[] { 255f / 255f, 255f / 255f, 153f / 255f, 1 },
			30.0f, false, null);
	public static final Item diamond = new Item("Diamond", new float[] { 0.223f, 0.839f, 0.917f, 1 }, 1800.0f, false,
			null);
	public static final Item emerald = new Item("Emerald", new float[] { 0.149f, 0.631f, 0.180f, 1 }, 1950.0f, false,
			null);
	public static final Item gold = new Item("Gold", new float[] { 0.976f, 0.788f, 0.384f, 1 }, 1500.0f, false, null);
	public static final Item honeyDew = new Item("HoneyDew", new float[] { 0.737f, 0.376f, 0.062f, 1 }, 46.0f, false,
			null);
	public static final Item honeyDrop = new Item("Honey Drop", new float[] { 255f / 255f, 153f / 255f, 0, 1 }, 43.0f,
			false, null);
	public static final Item iceShard = new Item("Ice Shard", new float[] { 0.584f, 0.874f, 0.937f, 1 }, 150.0f, false,
			null);
	public static final Item metal = new Item("Metal", new float[] { 0.341f, 0.341f, 0.4f, 1 }, 300.0f, false, null);
	public static final Item pollenCluster = new Item("Pollen Cluster", new float[] { 128f / 255f, 96f / 255f, 0, 1 },
			75.0f, false, null);
	public static final Item propolis = new Item("Propolis", new float[] { 0.376f, 0.435f, 0.223f, 1 }, 65.0f, false,
			null);
	public static final Item pulsatingPropolis = new Item("Pulsating Propolis",
			new float[] { 0.243f, 0.596f, 0.427f, 1 }, 65.0f, false, null);
	public static final Item royalJelly = new Item("Royal Jelly", new float[] { 255f / 255f, 204f / 255f, 0, 1 }, 80.0f,
			false, null);

	/*
	 * Combs
	 */
	public static final Item cocoaComb = new Item("Cocoa Comb", new float[] { 0.964f, 0.486f, 0.156f, 1 }, 230.0f,
			false, null);
	public static final Item drippingComb = new Item("Dripping Comb", new float[] { 0.964f, 0.768f, 0.156f, 1 }, 130.0f,
			false, null);
	public static final Item frozenComb = new Item("Frozen Comb", new float[] { 0.560f, 0.862f, 0.878f, 1 }, 1950.0f,
			false, null);
	public static final Item honeyComb = new Item("Honey Comb", new float[] { 0.937f, 0.658f, 0.243f, 1 }, 36.0f, false,
			null);
	public static final Item luxeComb = new Item("Luxe Comb", new float[] { 0.937f, 0.243f, 0.788f, 1 }, 1500.0f, false,
			null);
	public static final Item mossyComb = new Item("Mossy Comb", new float[] { 0.137f, 0.352f, 0.125f, 0, 1 }, 300.0f,
			false, null);
	public static final Item mysteriousComb = new Item("Mysterious Comb", new float[] { 0.854f, 0.745f, 0.854f, 1 },
			1000.0f, false, null);
	public static final Item parchedComb = new Item("Parched Comb", new float[] { 0.839f, 0.866f, 0.192f }, 300.0f,
			false, null);
	public static final Item silkyComb = new Item("Silky Comb", new float[] { 0.317f, 0.823f, 0.384f, 1 }, 75.0f, false,
			null);
	public static final Item simmeringComb = new Item("Simmering Comb", new float[] { 0.823f, 0.317f, 0.329f, 1 },
			600.0f, false, null);
	public static final Item stringyComb = new Item("Stringy Comb", new float[] { 0.552f, 0.568f, 0.556f, 1 }, 65.0f,
			false, null);

	public Item(String name, float[] color, float price, boolean hasInfos, String seller) {
		this.name = name;
		this.color = color;
		this.price = price;
		this.hasInfos = hasInfos;
		this.seller = seller;
		int n = 100000 - 1 + 1;
		int i = rn.nextInt() % n;
		int randomNum = 1 + i;
		this.id = randomNum;
	}

	public Item(String name, float[] color, float price, boolean hasInfos, String seller, int id) {
		this.name = name;
		this.color = color;
		this.price = price;
		this.hasInfos = hasInfos;
		this.seller = seller;
		this.id = id;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return this.name;
	}

	public float[] getColor() {
		return this.color;
	}

	public float getPrice() {
		return this.price;
	}

	public boolean hasInfos() {
		return hasInfos;
	}

	public String getSeller() {
		return this.seller;
	}

	public int getId() {
		return id;
	}
}
