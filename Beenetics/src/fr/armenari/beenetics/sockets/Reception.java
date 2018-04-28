package fr.armenari.beenetics.sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.armenari.beenetics.main.Main;
import fr.armenari.beenetics.main.game.Game;
import fr.armenari.beenetics.main.items.Bee;
import fr.armenari.beenetics.main.items.Item;

public class Reception implements Runnable {

	private ObjectInputStream in;
	static ArrayList<Object> d_m = new ArrayList<>();

	public Reception(ObjectInputStream in) {
		this.in = in;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		while (true) {
			try {
				try {
					d_m = (ArrayList<Object>) in.readObject();
				} catch (ClassNotFoundException e) {
					Main.in.close();
					e.printStackTrace();
				}
				switch (d_m.get(0) + "") {
				case "BP":
					Game.beePoints = (float) d_m.get(1);
					break;
				case "SET_BP_DONE":
					break;
				case "SELLER_BP":
					Game.sellerBeePoints = (float) d_m.get(1);
					break;
				case "SET_SELLER_BP_DONE":
					break;
				case "INSERT_ITEM_TO_MARKET_DONE":
					System.out.println("INSERT_ITEM_TO_MARKET_DONE");
					break;
				case "INSERT_BEE_TO_MARKET_DONE":
					System.out.println("INSERT_BEE_TO_MARKET_DONE");
					break;
				case "USERS":
					Game.users = (ArrayList<String[]>) d_m.get(1);
					break;
				case "ITEMS":
					Game.market_items = getItems(d_m);
					break;
				case "BEES":
					Game.market_bees = getBees(d_m);
					break;
				case "ITEM_REMOVE_DONE":
					System.out.println("ITEM_REMOVE_DONE");
					break;
				default:
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private ArrayList<Item> getItems(ArrayList<Object> d_m2) {
		ArrayList<Object> obj = new ArrayList<>();
		obj.add("getDBItem");
		ArrayList<Item> items = new ArrayList<>();
		for (int i = 0; i < ((ArrayList<Object>) d_m2.get(1)).size(); i += 6) {
			items.add(new Item((String) ((ArrayList<Object>) d_m2.get(1)).get(i),
					(float[]) ((ArrayList<Object>) d_m2.get(1)).get(i + 1),
					(float) ((ArrayList<Object>) d_m2.get(1)).get(i + 2),
					(boolean) ((ArrayList<Object>) d_m2.get(1)).get(i + 3),
					(String) ((ArrayList<Object>) d_m2.get(1)).get(i + 4),
					(int) ((ArrayList<Object>) d_m2.get(1)).get(i + 5)));
		}
		return items;
	}

	@SuppressWarnings("unchecked")
	private List<Item> getBees(ArrayList<Object> d_m2) {
		ArrayList<Object> obj = new ArrayList<>();
		obj.add("getDBBees");
		ArrayList<String> using = new ArrayList<>();
		ArrayList<String> with = new ArrayList<>();

		String name;
		int lifespan;
		float pollination;
		int fertility;
		float speed;
		boolean isPrincess;
		float[] color;
		boolean isAnalaysed;
		float price;
		int id;
		String seller;
		List<Item> bees = new ArrayList<>();

		for (int i = 0; i < ((ArrayList<Object>) d_m2.get(1)).size(); i += 11) {
			name = (String) ((ArrayList<Object>) d_m2.get(1)).get(i);
			lifespan = (int) ((ArrayList<Object>) d_m2.get(1)).get(i + 1);
			speed = (float) ((ArrayList<Object>) d_m2.get(1)).get(i + 2);
			pollination = (float) ((ArrayList<Object>) d_m2.get(1)).get(i + 3);
			fertility = (int) ((ArrayList<Object>) d_m2.get(1)).get(i + 4);
			color = (float[]) ((ArrayList<Object>) d_m2.get(1)).get(i + 5);
			isPrincess = (boolean) ((ArrayList<Object>) d_m2.get(1)).get(i + 6);
			price = (float) ((ArrayList<Object>) d_m2.get(1)).get(i + 7);
			isAnalaysed = (boolean) ((ArrayList<Object>) d_m2.get(1)).get(i + 8);
			seller = (String) ((ArrayList<Object>) d_m2.get(1)).get(i + 9);
			id = (int) ((ArrayList<Object>) d_m2.get(1)).get(i + 10);
			if (isPrincess) {
				for (HashMap.Entry<String, ArrayList<Bee>> pair : Bee.drones.entrySet()) {
					for (int j = 0; j < pair.getValue().size(); j++) {
						if (pair.getValue().get(j).getName().equals(name)) {
							using = pair.getValue().get(j).getUsing();
							with = pair.getValue().get(j).getWith();
						}
					}
				}
			} else {
				for (HashMap.Entry<String, ArrayList<Bee>> pair : Bee.drones.entrySet()) {
					for (int k = 0; k < pair.getValue().size(); k++) {
						if (pair.getValue().get(k).getName().equals(name)) {
							using = pair.getValue().get(k).getUsing();
							with = pair.getValue().get(k).getWith();
						}
					}
				}
			}
			bees.add(new Bee(name, lifespan, speed, pollination, fertility, color, isPrincess, price, using, with,
					isAnalaysed, seller, id));
		}
		return bees;
	}
}
