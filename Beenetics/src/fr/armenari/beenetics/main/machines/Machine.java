package fr.armenari.beenetics.main.machines;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import fr.armenari.beenetics.main.Main;
import fr.armenari.beenetics.main.game.Game;
import fr.armenari.beenetics.main.guis.GUI;
import fr.armenari.beenetics.main.items.Item;

public class Machine implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7161642666828767453L;
	private String name;
	private int cost;
	private int size;
	protected int life;
	protected boolean removed;
	protected int time;

	protected Vector2f position;

	public static List<Machine> machines;
	public static List<Machine> machinesStore;

	public static int machineSizeX = 275;
	public static int machineSizeY = 95;

	public static int machinesSize;
	public static int machinesCapacity;

	public static Machine whatApiary;
	public static Machine whatCentrifuge;
	public static Machine whatAnalyser;
	public static Machine whatGenePool;
	public static Machine whatIsolator;
	public static Machine whatInoculator;

	public Machine(String name, int cost, int size, int life) {
		this.name = name;
		this.cost = cost;
		this.size = size;
		this.life = life;
		this.position = new Vector2f(0, 0);
		this.time = 0;
	}

	public boolean isRemoved() {
		return removed;
	}

	/**
	 * @brief Actions to do when machine starts
	 */
	public void onStart(Game game) {

	}

	/**
	 * @brief Actions to do when machine removed
	 */
	public void onRemove(Game game) {

	}

	public void onChangingDay(Game game) {

	}

	private static void updateMachines(Machine m) {
		if (m instanceof Apiary) {
			((Apiary) m).update();
		}
		if (m instanceof Centrifuge) {
			((Centrifuge) m).update();
		}
		if (m instanceof Analyser) {
			((Analyser) m).update();
		}
		if (m instanceof Isolator) {
			((Isolator) m).update();
		}
		if (m instanceof GenePool) {
			((GenePool) m).update();
		}
	}

	public static void machinesGUI() {
		GUI.color(0.3f, 0.3f, 0.3f, 1.0f);
		GUI.drawString("FACTORY", Display.getWidth() / 2 + 124, 24 + Game.wheel, 24);
		for (int i = 0; i < Machine.machines.size(); i++) {
			Machine m = Machine.machines.get(i);
			updateMachines(m);
			GUI.color(0.2f, 0.2f, 0.2f, 1);
			int x = 300 + 20
					+ (i % (int) ((Display.getWidth() - Game.sideMenuX) / (machineSizeX + 10))) * (machineSizeX + 20);
			int y = 90
					+ (i / (int) ((Display.getWidth() - Game.sideMenuX) / (machineSizeX + 10))) * (machineSizeY + 20);
			m.setPosition(new Vector2f(x, y));
			GUI.drawQuad((int) m.getPosition().x, (int) m.getPosition().y, machineSizeX, machineSizeY);
			GUI.color(0.9f, 0.9f, 0.9f, 1);
			GUI.drawString(m.getName(), x + (machineSizeX / 2) - (m.getName().length() * 24 / 2), y + 10, 24);

			float time = (float) m.getTime() / (float) m.getLife();
			GUI.color(time, 1 - time, 0, 1);
			GUI.drawQuad(x, y + machineSizeY - 5, (int) (machineSizeX * (1 - time)), 5);

			int mx = Mouse.getX();
			int my = Display.getHeight() - Mouse.getY();

			if (mx > m.getPosition().x && my > y && mx < m.getPosition().x + machineSizeX && my < y + machineSizeY) {
				if (GUI.button("x", x, y, 32, 5)) {
					removeMachin(m);
				}
			}

			if (mx > m.getPosition().x && my > y && mx < m.getPosition().x + machineSizeX && my < y + machineSizeY
					&& m instanceof Apiary) {
				if (GUI.button("+", x + machineSizeX - 32, y + machineSizeY - 37, 32, 5)) {
					Apiary.GUIOpened = true;
					((Apiary) m).openGUI();
					whatApiary = m;
				}
				if (GUI.button("|", x, y + machineSizeY - 37, 32, 5)) {
					((Apiary) m).putProducedItemsIntoInventory();
				}
			}

			if (mx > x && my > y && mx < x + machineSizeX && my < y + machineSizeY && m instanceof Centrifuge) {
				if (GUI.button("+", x + machineSizeX - 32, y + machineSizeY - 37, 32, 5)) {
					Centrifuge.GUIOpened = true;
					((Centrifuge) m).openGUI();
					whatCentrifuge = m;
				}
				if (GUI.button("|", x, y + machineSizeY - 37, 32, 5)) {
					((Centrifuge) m).putProducedItemsIntoInventory();
				}
			}

			if (mx > x && my > y && mx < x + machineSizeX && my < y + machineSizeY && m instanceof Analyser) {
				if (GUI.button("+", x + machineSizeX - 32, y + machineSizeY - 37, 32, 5)) {
					Analyser.GUIOpened = true;
					((Analyser) m).openGUI();
					whatAnalyser = m;
				}
				if (GUI.button("|", x, y + machineSizeY - 37, 32, 5)) {
					((Analyser) m).putProducedItemsIntoInventory();
				}
			}

			if (mx > x && my > y && mx < x + machineSizeX && my < y + machineSizeY && m instanceof Isolator) {
				if (GUI.button("+", x + machineSizeX - 32, y + machineSizeY - 37, 32, 5)) {
					Isolator.GUIOpened = true;
					((Isolator) m).openGUI();
					whatIsolator = m;
				}
				if (GUI.button("|", x, y + machineSizeY - 37, 32, 5)) {
					((Isolator) m).putProducedItemsIntoInventory();
				}
			}
			if (mx > x && my > y && mx < x + machineSizeX && my < y + machineSizeY && m instanceof Inoculator) {
				if (GUI.button("+", x + machineSizeX - 32, y + machineSizeY - 37, 32, 5)) {
					Inoculator.GUIOpened = true;
					((Inoculator) m).openGUI();
					whatInoculator = m;
				}
				if (GUI.button("|", x, y + machineSizeY - 37, 32, 5)) {
					((Inoculator) m).putProducedItemsIntoInventory();
				}
			}

			if (mx > x && my > y && mx < x + machineSizeX && my < y + machineSizeY && m instanceof GenePool) {
				if (GUI.button("+", x + machineSizeX - 32, y + machineSizeY - 37, 32, 5)) {
					GenePool.GUIOpened = true;
					((GenePool) m).openGUI();
					whatGenePool = m;
				}
			}
		}

		for (int i = 0; i < Machine.machines.size(); i++) {
			Machine m = Machine.machines.get(i);
			int mx = Mouse.getX();
			int my = Display.getHeight() - Mouse.getY();
			int x = (int) m.getPosition().x;
			int y = (int) m.getPosition().y;

			if (mx > x && my > y && mx < x + machineSizeX && my < y + machineSizeY) {
				if (mx > Display.getWidth() - 370) {
					mx = mx - 370;
				}
				if (my > Display.getHeight() / 2) {
					my = my - 294;
				}
				GUI.color(0.5f, 0.5f, 0.5f, 0.5f);
				GUI.drawQuad(mx, my, 370, 17);
				GUI.color(1, 1, 1, 1);
				GUI.drawQuad(mx, my + 17, 370, 294);
				GUI.color(0.05f, 0.05f, 0.05f, 1.0f);
				GUI.drawString(m.getName(), mx + 370 / 2 - m.getName().length() * 7, my + 2, 16);
				if (m instanceof Apiary) {
					int index = 0;
					ArrayList<String> producedItems = new ArrayList<>();

					String queenName = ((Apiary) m).getPrincess() == null ? "NO Princess"
							: ((Apiary) m).getPrincess().getName();
					String droneName = ((Apiary) m).getDrone() == null ? "NO DRONE" : ((Apiary) m).getDrone().getName();

					String frameName = ((Apiary) m).getFrame() == null ? "NO FRAME" : ((Apiary) m).getFrame().getName();

					if (((Apiary) m).getPrincess() == null) {
						GUI.color(1, 0, 0, 1);
					}
					GUI.drawString("Princess: " + queenName, mx + 24, my + 32 * 1, 16);

					if (((Apiary) m).getDrone() == null) {
						GUI.color(1, 0, 0, 1);
					}
					GUI.drawString("Drone: " + droneName, mx + 24, my + 32 * 2, 16);
					if (((Apiary) m).getFrame() == null) {
						GUI.color(1, 0, 0, 1);
					}
					GUI.drawString("Frame: " + frameName, mx + 24, my + 32 * 3, 16);
					GUI.color(0.05f, 0.05f, 0.05f, 1.0f);
					GUI.drawString("Produced items: ", mx + 24, my + 32 * 4, 16);

					for (Item o : ((Apiary) m).getProducedItems()) {
						if (!producedItems.contains(o.getName())) {
							producedItems.add(o.getName());
						}
					}
					int[] producedItemsCount = new int[producedItems.size()];
					for (String o : producedItems) {
						for (Item l : ((Apiary) m).getProducedItems()) {
							if (o == l.getName()) {
								producedItemsCount[producedItems.indexOf(o)]++;
							}
						}
					}
					for (String name : producedItems) {
						GUI.drawString(name + " x" + producedItemsCount[index], mx + 24, my + 32 * (5 + index), 16);
						index++;
					}
					GUI.drawString("Remaining time: " + (m.getLife() - m.getTime()), mx + 24, my + 32 * 9, 16);
				}
				if (m instanceof Centrifuge) {
					int index = 0;
					ArrayList<String> producedItems = new ArrayList<>();
					String comb = ((Centrifuge) m).getComb() == null ? "NO COMB" : ((Centrifuge) m).getComb().getName();
					if (((Centrifuge) m).getComb() == null) {
						GUI.color(1, 0, 0, 1);
					}
					GUI.drawString("COMB: " + comb, mx + 24, my + 32 * 1, 16);
					GUI.color(0.05f, 0.05f, 0.05f, 1.0f);
					GUI.drawString("Produced items: ", mx + 24, my + 32 * 2, 16);
					for (Item o : ((Centrifuge) m).getProducedItems()) {
						if (!producedItems.contains(o.getName())) {
							producedItems.add(o.getName());
						}
					}
					int[] producedItemsCount = new int[producedItems.size()];
					for (String o : producedItems) {
						for (Item l : ((Centrifuge) m).getProducedItems()) {
							if (o == l.getName()) {
								producedItemsCount[producedItems.indexOf(o)]++;
							}
						}
					}
					for (String name : producedItems) {
						GUI.drawString(name + " x" + producedItemsCount[index], mx + 24, my + 32 * (3 + index), 16);
						index++;
					}
					GUI.drawString("Remaining time: " + (m.getLife() - m.getTime()), mx + 24, my + 32 * 9, 16);
				}
				if (m instanceof Analyser) {
					int index = 0;
					ArrayList<String> producedItems = new ArrayList<>();
					String bee = ((Analyser) m).getBee() == null ? "NO BEE" : ((Analyser) m).getBee().getName();
					String drop = ((Analyser) m).getHoneyDrop() == null ? "NO DROP"
							: ((Analyser) m).getHoneyDrop().getName();
					if (((Analyser) m).getBee() == null) {
						GUI.color(1, 0, 0, 1);
					}
					GUI.drawString("Bee: " + bee, mx + 24, my + 32 * 1, 16);
					if (((Analyser) m).getHoneyDrop() == null) {
						GUI.color(1, 0, 0, 1);
					}
					GUI.drawString("Drop: " + drop, mx + 24, my + 32 * 2, 16);
					GUI.color(0.05f, 0.05f, 0.05f, 1.0f);
					GUI.drawString("Produced items: ", mx + 24, my + 32 * 3, 16);
					for (Item o : ((Analyser) m).getProducedItems()) {
						if (!producedItems.contains(o.getName())) {
							producedItems.add(o.getName());
						}
					}
					int[] producedItemsCount = new int[producedItems.size()];
					for (String o : producedItems) {
						for (Item l : ((Analyser) m).getProducedItems()) {
							if (o == l.getName()) {
								producedItemsCount[producedItems.indexOf(o)]++;
							}
						}
					}
					for (String name : producedItems) {
						GUI.drawString(name + " x" + producedItemsCount[index], mx + 24, my + 32 * (4 + index), 16);
						index++;
					}
					GUI.drawString("Remaining time: " + (m.getLife() - m.getTime()), mx + 24, my + 32 * 9, 16);
				}
				if (m instanceof Isolator) {
					int index = 0;
					ArrayList<String> producedItems = new ArrayList<>();
					String bee = ((Isolator) m).getBee() == null ? "NO BEE" : ((Isolator) m).getBee().getName();
					String vial = ((Isolator) m).getVial() == null ? "NO VIAL" : ((Isolator) m).getVial().getName();
					if (((Isolator) m).getBee() == null) {
						GUI.color(1, 0, 0, 1);
					}
					GUI.drawString("Bee: " + bee, mx + 24, my + 32 * 1, 16);
					if (((Isolator) m).getVial() == null) {
						GUI.color(1, 0, 0, 1);
					}
					GUI.drawString("Vial: " + vial, mx + 24, my + 32 * 2, 16);
					GUI.color(0.05f, 0.05f, 0.05f, 1.0f);
					GUI.drawString("Produced items: ", mx + 24, my + 32 * 3, 16);
					for (Item o : ((Isolator) m).getProducedItems()) {
						if (!producedItems.contains(o.getName())) {
							producedItems.add(o.getName());
						}
					}
					int[] producedItemsCount = new int[producedItems.size()];
					for (String o : producedItems) {
						for (Item l : ((Isolator) m).getProducedItems()) {
							if (o == l.getName()) {
								producedItemsCount[producedItems.indexOf(o)]++;
							}
						}
					}
					for (String name : producedItems) {
						GUI.drawString(name + " x" + producedItemsCount[index], mx + 24, my + 32 * (4 + index), 16);
						index++;
					}
					GUI.drawString("Remaining time: " + (m.getLife() - m.getTime()), mx + 24, my + 32 * 9, 16);
				}
				if (m instanceof Inoculator) {
					int index = 0;
					ArrayList<String> producedItems = new ArrayList<>();
					String bee = ((Inoculator) m).getBee() == null ? "NO BEE" : ((Inoculator) m).getBee().getName();
					String vial = ((Inoculator) m).getVial() == null ? "NO VIAL" : ((Inoculator) m).getVial().getName();
					if (((Inoculator) m).getBee() == null) {
						GUI.color(1, 0, 0, 1);
					}
					GUI.drawString("Bee: " + bee, mx + 24, my + 32 * 1, 16);
					if (((Inoculator) m).getVial() == null) {
						GUI.color(1, 0, 0, 1);
					}
					GUI.drawString("Vial: " + vial, mx + 24, my + 32 * 2, 16);
					GUI.color(0.05f, 0.05f, 0.05f, 1.0f);
					GUI.drawString("Produced items: ", mx + 24, my + 32 * 3, 16);
					for (Item o : ((Inoculator) m).getProducedItems()) {
						if (!producedItems.contains(o.getName())) {
							producedItems.add(o.getName());
						}
					}
					int[] producedItemsCount = new int[producedItems.size()];
					for (String o : producedItems) {
						for (Item l : ((Inoculator) m).getProducedItems()) {
							if (o == l.getName()) {
								producedItemsCount[producedItems.indexOf(o)]++;
							}
						}
					}
					for (String name : producedItems) {
						GUI.drawString(name + " x" + producedItemsCount[index], mx + 24, my + 32 * (4 + index), 16);
						index++;
					}
					GUI.drawString("Remaining time: " + (m.getLife() - m.getTime()), mx + 24, my + 32 * 9, 16);
				}
				if (m instanceof GenePool) {
					String bee = ((GenePool) m).getBee() == null ? "NO BEE" : ((GenePool) m).getBee().getName();
					if (((GenePool) m).getBee() == null) {
						GUI.color(1, 0, 0, 1);
					}
					GUI.drawString("Bee: " + bee, mx + 24, my + 32 * 1, 16);
					GUI.color(0.05f, 0.05f, 0.05f, 1.0f);
					GUI.drawString("Remaining time: " + (m.getLife() - m.getTime()), mx + 24, my + 32 * 9, 16);
				}

			}
		}
	}

	public static void removeMachin(Machine m) {
		m.onRemove(Main.game);
		Machine.machines.remove(m);
		machinesSize -= m.getSize();
	}

	public String getName() {
		return name;
	}

	public int getCost() {
		return cost;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getLife() {
		return life;
	}

	public int getTime() {
		return time;
	}

	public void setPosition(Vector2f vector2f) {
		this.position = vector2f;
	}

	public Vector2f getPosition() {
		return this.position;
	}
}