package fr.armenari.beenetics.main.game;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import fr.armenari.beenetics.main.Main;
import fr.armenari.beenetics.main.guis.GUI;
import fr.armenari.beenetics.main.items.Bee;
import fr.armenari.beenetics.main.items.Frame;
import fr.armenari.beenetics.main.items.Item;
import fr.armenari.beenetics.main.items.Vial;
import fr.armenari.beenetics.main.utils.DataBaseConnection;
import fr.armenari.beenetics.sockets.SQL;

public class Market {

	public static List<Item> market;
	public static boolean marketGUIOpened = false;

	public static void openGUI() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		GUI.color(0.3f, 0.3f, 0.3f, 1.0f);
		GUI.drawString("MARKET", Display.getWidth() / 2 - "MARKET".length() * 24 / 2, 24 + Game.wheel, 24);

		if (GUI.button("Sell Item", Display.getWidth() - 250, 8, 224, 4)) {
			Inventory.invGUIOpened = true;
			Main.getGameInstance().setProperties(Game.PROP_ID_CHOOSING_ITEM_TO_SELL, true);
			Market.marketGUIOpened = false;
		}

		if (market.size() <= 0) {
			GUI.color(0.3f, 0.3f, 0.3f, 1.0f);
			GUI.drawString("There is nothing available in the Market.",
					Display.getWidth() / 2 - "There is nothing available in the Market.".length() * 24 / 2, 64, 24);
		} else {
			int mx;
			int my;

			for (int i = 0; i < market.size(); i++) {
				Item m = market.get(i);
				String subName = m.getName().substring(0, 3) + " .";
				mx = Mouse.getX();
				my = Display.getHeight() - Mouse.getY();
				GUI.color(0.2f, 0.2f, 0.2f, 1);
				int x = 60 + 20 + (i % (Display.getWidth() / (160))) * (24 * (subName.length()) + 22);
				int y = 60 + (i / (Display.getWidth() / (160))) * (40 + 20) + Game.wheel;

				GUI.color(m.getColor()[0], m.getColor()[1], m.getColor()[2], 0.8f);
				GUI.drawQuad(x, y, 24 * (subName.length()), 32);

				if (GUI.button(subName, x + 6, y + 8, 120, 2)) {
					SQL.getSellerBP(m.getSeller());
					System.out.println(Game.sellerBeePoints);
					if (Game.beePoints - m.getPrice() >= 0) {
						if (!DataBaseConnection.username.equals(m.getSeller())) {
							SQL.addUserBP(DataBaseConnection.username, -m.getPrice());
							SQL.addUserBP(m.getSeller(), m.getPrice());
							SQL.removeItem(m.getId());
							Inventory.inventory.add(m);
							SQL.getDBBees();
							SQL.getDBItems();
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

			for (int i = 0; i < market.size(); i++) {
				Item m = market.get(i);
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
							my = my - 264;
						} else {
							my = my - (24 + 48);
						}
					}
					if (m.hasInfos()) {
						GUI.drawQuad(mx, my, 370, 17);
						GUI.color(1, 1, 1, 0.9f);
						GUI.drawQuad(mx, my + 17, 370, 264);
						GUI.color(0.1f, 0.1f, 0.1f, 0.9f);
						GUI.drawString("Seller: " + m.getSeller(), mx + 24, my + 32 * 8, 16);
					} else {
						GUI.drawQuad(mx, my, 370, 17);
						GUI.color(1, 1, 1, 0.9f);
						GUI.drawQuad(mx, my + 17, 370, 24 + 48);
						GUI.color(0.1f, 0.1f, 0.1f, 0.9f);
						GUI.drawString("Price: " + m.getPrice(), mx + 24, my + 32 * 1, 16);
						GUI.drawString("Seller: " + m.getSeller(), mx + 24, my + 32 * 2, 16);
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
		if (GUI.button("x", 0, 0, 32, 5)) {
			marketGUIOpened = false;
		}

	}

}
