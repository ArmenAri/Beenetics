package fr.armenari.beenetics.main.game;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import fr.armenari.beenetics.main.guis.GUI;
import fr.armenari.beenetics.main.items.Bee;
import fr.armenari.beenetics.main.items.Frame;
import fr.armenari.beenetics.main.items.Item;
import fr.armenari.beenetics.main.items.Vial;
import fr.armenari.beenetics.main.machines.Analyser;
import fr.armenari.beenetics.main.machines.Apiary;
import fr.armenari.beenetics.main.machines.Centrifuge;
import fr.armenari.beenetics.main.machines.GenePool;
import fr.armenari.beenetics.main.machines.Inoculator;
import fr.armenari.beenetics.main.machines.Isolator;
import fr.armenari.beenetics.main.machines.Machine;
import fr.armenari.beenetics.sockets.SQL;

public class Inventory {

	public static List<Item> inventory;
	public static boolean invGUIOpened = false;

	public static void openInvGUI() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		if (Apiary.GUIOpened) {
			GUI.color(0.3f, 0.3f, 0.3f, 1.0f);
			if (Game.getInstance().getProperties(Game.PROP_ID_CHOOSING_PRINCESS)) {
				GUI.drawString("Choose princess to add",
						Display.getWidth() / 2 - "Choose princess to add".length() * 24 / 2, 24 + Game.wheel, 24);
			} else if (Game.getInstance().getProperties(Game.PROP_ID_CHOOSING_DRONE)) {
				GUI.drawString("Choose drone to add", Display.getWidth() / 2 - "Choose drone to add".length() * 24 / 2,
						24 + Game.wheel, 24);
			} else if (Game.getInstance().getProperties(Game.PROP_ID_CHOOSING_ANALYSER_DROP)) {
				GUI.drawString("Choose frame to add", Display.getWidth() / 2 - "Choose frame to add".length() * 24 / 2,
						24 + Game.wheel, 24);
			}
		} else if (Centrifuge.GUIOpened) {
			GUI.color(0.3f, 0.3f, 0.3f, 1.0f);
			if (Game.getInstance().getProperties(Game.PROP_ID_CHOOSING_COMB)) {
				GUI.drawString("Choose comb to add", Display.getWidth() / 2 - "Choose comb to add".length() * 24 / 2,
						24 + Game.wheel, 24);
			}
		} else if (GenePool.GUIOpened) {
			GUI.color(0.3f, 0.3f, 0.3f, 1.0f);
			if (Game.getInstance().getProperties(Game.PROP_ID_CHOOSING_GENE_POOL_BEE)) {
				GUI.drawString("Choose bee to add", Display.getWidth() / 2 - "Choose bee to add".length() * 24 / 2,
						24 + Game.wheel, 24);
			}
		} else if (Analyser.GUIOpened) {
			GUI.color(0.3f, 0.3f, 0.3f, 1.0f);
			if (Game.getInstance().getProperties(Game.PROP_ID_CHOOSING_ANALYSER_BEE)) {
				GUI.drawString("Choose bee to add", Display.getWidth() / 2 - "Choose bee to add".length() * 24 / 2,
						24 + Game.wheel, 24);
			}
			if (Game.getInstance().getProperties(Game.PROP_ID_CHOOSING_ANALYSER_DROP)) {
				GUI.drawString("Choose honey drop to add",
						Display.getWidth() / 2 - "Choose honey drop to add".length() * 24 / 2, 24 + Game.wheel, 24);
			}
		} else if (Isolator.GUIOpened) {
			GUI.color(0.3f, 0.3f, 0.3f, 1.0f);
			if (Game.getInstance().getProperties(Game.PROP_ID_CHOOSING_ISOLATOR_BEE)) {
				GUI.drawString("Choose bee to add", Display.getWidth() / 2 - "Choose bee to add".length() * 24 / 2,
						24 + Game.wheel, 24);
			}
			if (Game.getInstance().getProperties(Game.PROP_ID_CHOOSING_ISOLATOR_VIAL)) {
				GUI.drawString("Choose vial to add", Display.getWidth() / 2 - "Choose vial to add".length() * 24 / 2,
						24 + Game.wheel, 24);
			}
		} else if (Inoculator.GUIOpened) {
			GUI.color(0.3f, 0.3f, 0.3f, 1.0f);
			if (Game.getInstance().getProperties(Game.PROP_ID_CHOOSING_INOCULATOR_BEE)) {
				GUI.drawString("Choose bee to add", Display.getWidth() / 2 - "Choose bee to add".length() * 24 / 2,
						24 + Game.wheel, 24);
			}
			if (Game.getInstance().getProperties(Game.PROP_ID_CHOOSING_INOCULATOR_VIAL)) {
				GUI.drawString("Choose vial to add", Display.getWidth() / 2 - "Choose vial to add".length() * 24 / 2,
						24 + Game.wheel, 24);
			}
		} else if (Game.getInstance().getProperties(Game.PROP_ID_CHOOSING_ITEM_TO_SELL)) {
			GUI.color(0.3f, 0.3f, 0.3f, 1.0f);
			GUI.drawString("Choose item to sell", Display.getWidth() / 2 - "Choose item to sell".length() * 24 / 2,
					24 + Game.wheel, 24);

		} else {
			GUI.color(0.3f, 0.3f, 0.3f, 1.0f);
			GUI.drawString("INVENTORY", Display.getWidth() / 2 - "INVENTORY".length() * 24 / 2, 24 + Game.wheel, 24);
		}
		if (inventory.size() <= 0) {
			GUI.color(0.3f, 0.3f, 0.3f, 1.0f);
			GUI.drawString("There is nothing in your inventory",
					Display.getWidth() / 2 - "There is nothing in your inventory".length() * 24 / 2, 64 + Game.wheel,
					24);
		} else {
			int mx;
			int my;

			for (int i = 0; i < inventory.size(); i++) {
				Item m = inventory.get(i);
				String subName = m.getName().substring(0, 3) + " .";
				mx = Mouse.getX();
				my = Display.getHeight() - Mouse.getY();
				GUI.color(0.2f, 0.2f, 0.2f, 1);
				int x = 60 + 20 + (i % (Display.getWidth() / (160))) * (24 * (subName.length()) + 22);
				int y = 60 + (i / (Display.getWidth() / (160))) * (40 + 20) + Game.wheel;

				GUI.color(m.getColor()[0], m.getColor()[1], m.getColor()[2], 0.8f);
				GUI.drawQuad(x, y, 24 * (subName.length()), 32);

				if (GUI.button(subName, x + 6, y + 8, 120, 2)) {
					if (Game.getInstance().getProperties(Game.PROP_ID_CHOOSING_ITEM_TO_SELL)) {
						if (m instanceof Bee) {
							SQL.insertBee((Bee) m);
						} else if (m instanceof Item) {
							SQL.insertItem(m);
						}
						inventory.remove(m);
						Game.getInstance().setProperties(Game.PROP_ID_CHOOSING_ITEM_TO_SELL, false);
						invGUIOpened = false;
					}
					if (Apiary.GUIOpened) {
						if (m instanceof Bee) {
							if (Game.getInstance().getProperties(Game.PROP_ID_CHOOSING_PRINCESS)) {
								if (((Bee) m).isPrincess()) {
									((Apiary) Machine.whatApiary).setPrincess(((Bee) m));
									inventory.remove(i);
									invGUIOpened = false;
								}
							} else if (Game.getInstance().getProperties(Game.PROP_ID_CHOOSING_DRONE)) {
								if (!((Bee) m).isPrincess()) {
									((Apiary) Machine.whatApiary).setDrone(((Bee) m));
									invGUIOpened = false;
									inventory.remove(i);
								}
							}
						}
						if (Game.getInstance().getProperties(Game.PROP_ID_CHOOSING_ADDON)) {
							if (m.getName().contains("Frame")) {
								((Apiary) Machine.whatApiary).setFrame(((Frame) m));
								invGUIOpened = false;
								inventory.remove(i);
							}
						}
					}
					if (Centrifuge.GUIOpened) {
						if (!(m instanceof Bee)) {
							if (Game.getInstance().getProperties(Game.PROP_ID_CHOOSING_COMB)) {
								if (m.getName().contains("Comb")) {
									((Centrifuge) Machine.whatCentrifuge).setComb(((Item) m));
									invGUIOpened = false;
									inventory.remove(i);
								}
							}
						}
					}
					if (GenePool.GUIOpened) {
						if ((m instanceof Bee)) {
							if (Game.getInstance().getProperties(Game.PROP_ID_CHOOSING_GENE_POOL_BEE)) {
								((GenePool) Machine.whatGenePool).setBee(((Bee) m));
								invGUIOpened = false;
								inventory.remove(i);
							}
						}
					}
					if (Analyser.GUIOpened) {
						if (m instanceof Bee) {
							if (Game.getInstance().getProperties(Game.PROP_ID_CHOOSING_ANALYSER_BEE)) {
								if (!((Bee) m).isAnalysed()) {
									((Analyser) Machine.whatAnalyser).setBee(((Bee) m));
									inventory.remove(i);
									invGUIOpened = false;
								}
							}
						} else if (Game.getInstance().getProperties(Game.PROP_ID_CHOOSING_ANALYSER_DROP)) {
							if (m.getName().equals("Honey Drop")) {
								((Analyser) Machine.whatAnalyser).setHoneyDrop(m);
								invGUIOpened = false;
								inventory.remove(i);
							}

						}
					}

					if (Isolator.GUIOpened) {
						if (m instanceof Bee) {
							if (Game.getInstance().getProperties(Game.PROP_ID_CHOOSING_ISOLATOR_BEE)) {
								((Isolator) Machine.whatIsolator).setBee(((Bee) m));
								inventory.remove(i);
								invGUIOpened = false;
							}
						} else if (Game.getInstance().getProperties(Game.PROP_ID_CHOOSING_ISOLATOR_VIAL)) {
							if (m instanceof Vial && ((Vial) m).isEmpty()) {
								((Isolator) Machine.whatIsolator).setVial(((Vial) m));
								invGUIOpened = false;
								inventory.remove(i);
							}
						}
					}

					if (Inoculator.GUIOpened) {
						if (m instanceof Bee && ((Bee) m).isAnalysed()) {
							if (Game.getInstance().getProperties(Game.PROP_ID_CHOOSING_INOCULATOR_BEE)) {
								((Inoculator) Machine.whatInoculator).setBee(((Bee) m));
								inventory.remove(i);
								invGUIOpened = false;
							}
						} else if (Game.getInstance().getProperties(Game.PROP_ID_CHOOSING_INOCULATOR_VIAL)) {
							if (m instanceof Vial && !((Vial) m).isEmpty()) {
								((Inoculator) Machine.whatInoculator).setVial(((Vial) m));
								invGUIOpened = false;
								inventory.remove(i);
							}
						}
					}
				}

				GUI.color(0.3f, 0.3f, 0.3f, 1.0f);
				GUI.drawQuad(Display.getWidth() - 5, 0 - Game.wheel / 10, 5, 10);
				if (Game.wheel <= 0) {
					Game.wheel += Mouse.getDWheel() / 5;
				} else {
					Game.wheel = 0;
				}
			}

			for (int i = 0; i < inventory.size(); i++) {
				Item m = inventory.get(i);
				String subName = m.getName().substring(0, 3) + " .";
				mx = Mouse.getX();
				my = Display.getHeight() - Mouse.getY();
				int x = 60 + 20 + (i % (Display.getWidth() / (160))) * (24 * subName.length() + 22);
				int y = 60 + (i / (Display.getWidth() / (160))) * (40 + 20) + Game.wheel;
				if (mx > x && my > y && mx < x + 24 * subName.length() + 2 && my < y + 40) {
					GUI.color(m.getColor()[0], m.getColor()[1], m.getColor()[2], 0.9f);
					if (mx > Display.getWidth() / 2) {
						mx = mx - 370;
					}
					if (my > Display.getHeight() / 2) {
						if (m.hasInfos()) {
							my = my - 234;
						}
					}
					if (m.hasInfos()) {
						GUI.drawQuad(mx, my, 370, 17);
						GUI.color(1, 1, 1, 0.9f);
						GUI.drawQuad(mx, my + 17, 370, 234);
					} else {
						GUI.drawQuad(mx, my, 370, 17);
						GUI.color(1, 1, 1, 0.9f);
						GUI.drawQuad(mx, my + 17, 370, 24 + 17);
						GUI.color(0.1f, 0.1f, 0.1f, 0.9f);
						GUI.drawString("Price: " + m.getPrice(), mx + 24, my + 32 * 1, 16);
					}
					GUI.color(0.05f, 0.05f, 0.05f, 1.0f);
					if (m instanceof Bee) {
						if (((Bee) m).isPrincess()) {
							GUI.drawString(m.getName() + "#", mx + 370 / 2 - (m.getName().length() + 1) * 7, my + 2,
									16);
						} else {
							GUI.drawString(m.getName() + "*", mx + 370 / 2 - (m.getName().length() + 1) * 7, my + 2,
									16);
						}
					} else {
						GUI.drawString(m.getName(), mx + 370 / 2 - (m.getName().length() + 1) * 7, my + 2, 16);
					}
					if (m instanceof Bee) {
						GUI.drawString("Type: Bee", mx + 24, my + 32 * 1, 16);
						if (((Bee) m).isAnalysed()) {
							GUI.drawString("Tier: " + ((Bee) m).getTier(), mx + 24, my + 32 * 2, 16);
							GUI.drawString("Lifespan: " + ((Bee) m).getLifeSpan(), mx + 24, my + 32 * 3, 16);
							GUI.drawString("Speed: " + ((Bee) m).getSpeed(), mx + 24, my + 32 * 4, 16);
							GUI.drawString("Pollination: " + ((Bee) m).getPollination(), mx + 24, my + 32 * 5, 16);
							GUI.drawString("Fertility: " + ((Bee) m).getFertility(), mx + 24, my + 32 * 6, 16);
							// GUI.drawString("Area: " + ((Bee) m).getArea()[0] + " by " + ((Bee)
							// m).getArea()[1], mx + 24,
							// my + 32 * 6, 16);
							// GUI.drawString("Princess: " + ((Bee) m).isPrincess(), mx + 24, my + 32 * 7,
							// 16);
							GUI.drawString("Price: " + m.getPrice() + " BP", mx + 24, my + 32 * 7, 16);
						} else {
							GUI.drawString("Tier: " + ((Bee) m).getTier(), mx + 24, my + 32 * 2, 16);
							GUI.drawString("Lifespan: " + "???", mx + 24, my + 32 * 3, 16);
							GUI.drawString("Speed: " + "???", mx + 24, my + 32 * 4, 16);
							GUI.drawString("Pollination: " + "???", mx + 24, my + 32 * 5, 16);
							GUI.drawString("Fertility: " + "???", mx + 24, my + 32 * 6, 16);
							// GUI.drawString("Area: " + ((Bee) m).getArea()[0] + " by " + ((Bee)
							// m).getArea()[1], mx + 24,
							// my + 32 * 6, 16);
							// GUI.drawString("Princess: " + ((Bee) m).isPrincess(), mx + 24, my + 32 * 7,
							// 16);
							GUI.drawString("Price: " + m.getPrice() + " BP", mx + 24, my + 32 * 7, 16);
						}
					}
					if (m instanceof Vial) {
						if (((Vial) m).isEmpty()) {
							GUI.drawString("Trait: ", mx + 24, my + 32 * 1, 16);
							GUI.drawString("NULL", mx + 24, my + 32 * 2, 16);
						} else {
							GUI.drawString("Trait: ", mx + 24, my + 32 * 1, 16);
							GUI.drawString(((Vial) m).getTrait().toString(), mx + 24, my + 32 * 2, 16);

						}
						GUI.drawString("Price: " + m.getPrice(), mx + 24, my + 32 * 6, 16);
					}
					if (m instanceof Frame) {
						GUI.drawString(
								"Durability: " + ((Frame) m).getDurability() + "/" + ((Frame) m).getMaxDurability(),
								mx + 24, my + 32 * 1, 16);
						GUI.drawString("Augmentation: " + ((Frame) m).getAugmentation() + "%", mx + 24, my + 32 * 2,
								16);
						GUI.drawString("Price: " + m.getPrice(), mx + 24, my + 32 * 6, 16);
					}
				}

			}
		}
		if (GUI.button("x", 0, 0, 32, 5))

		{
			invGUIOpened = false;
		}
	}

}
