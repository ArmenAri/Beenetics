package fr.armenari.beenetics.main.game;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glVertex2d;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import fr.armenari.beenetics.main.guis.GUI;
import fr.armenari.beenetics.main.guis.Notification;
import fr.armenari.beenetics.main.items.Bee;
import fr.armenari.beenetics.main.items.Item;
import fr.armenari.beenetics.main.machines.Analyser;
import fr.armenari.beenetics.main.machines.Apiary;
import fr.armenari.beenetics.main.machines.Centrifuge;
import fr.armenari.beenetics.main.machines.GenePool;
import fr.armenari.beenetics.main.machines.Inoculator;
import fr.armenari.beenetics.main.machines.Isolator;
import fr.armenari.beenetics.main.machines.Machine;
import fr.armenari.beenetics.main.utils.DataBaseConnection;
import fr.armenari.beenetics.main.utils.Utils;
import fr.armenari.beenetics.sockets.SQL;

public class Game {

	public static final int PROP_ID_CHOOSING_PRINCESS = 0;
	public static final int PROP_ID_CHOOSING_DRONE = 1;
	public static final int PROP_ID_CHOOSING_ADDON = 2;
	public static final int PROP_ID_CHOOSING_COMB = 3;
	public static final int PROP_ID_CHOOSING_ANALYSER_BEE = 4;
	public static final int PROP_ID_CHOOSING_ANALYSER_DROP = 5;
	public static final int PROP_ID_CHOOSING_GENE_POOL_BEE = 6;
	public static final int PROP_ID_CHOOSING_ISOLATOR_BEE = 7;
	public static final int PROP_ID_CHOOSING_ISOLATOR_VIAL = 8;
	public static final int PROP_ID_CHOOSING_ITEM_TO_SELL = 9;
	public static final int PROP_ID_CHOOSING_INOCULATOR_VIAL = 10;
	public static final int PROP_ID_CHOOSING_INOCULATOR_BEE = 11;

	private int time;
	private int years;

	public static int days;

	public static float DNAQuantity = 0;
	private static float MAX_DNA = 1295;

	public static float beePoints;
	public static float sellerBeePoints;

	public static final int sideMenuX = 300;

	private boolean[] properties = new boolean[12]; // Array which contains properties.

	public static boolean firstMenu = true;

	public static int wheel = 0;

	float yoff = 0;
	private double angle = 0;

	public static ArrayList<Notification> notifications = new ArrayList<>();
	public static List<String[]> users = new ArrayList<>();
	public static List<Item> market_bees = new ArrayList<>();
	public static List<Item> market_items = new ArrayList<>();

	public Game() {
		this.time = 0;
		Game.days = 0;
		DNAQuantity = 0;
		SQL.getAllUsers();
		beePoints = 0;
		Machine.machinesCapacity = 28;

		Machine.machinesStore = new ArrayList<Machine>();
		Machine.machines = new ArrayList<Machine>();
		Inventory.inventory = new ArrayList<Item>();
		Market.market = new ArrayList<>();

		Apiary ap = new Apiary();
		ap.setPrincess(new Bee("fazepd", 6874687654.f));
		Machine.machinesStore.add(ap);
		Machine.machinesStore.add(new Centrifuge());
		Machine.machinesStore.add(new Analyser());
		Machine.machinesStore.add(new GenePool());
		Machine.machinesStore.add(new Isolator());
		Machine.machinesStore.add(new Inoculator());

		notifications.add(new Notification());

		if (Inventory.inventory.size() <= 0 && Game.days <= 0) {
			for (HashMap.Entry<String, ArrayList<Bee>> pair : Bee.drones.entrySet()) {
				for (int i = 0; i < pair.getValue().size(); i++) {
					if (pair.getValue().get(i).getTier().equals("common"))
						Inventory.inventory.add(pair.getValue().get(i));

				}
			}

			for (HashMap.Entry<String, ArrayList<Bee>> pair : Bee.queens.entrySet()) {
				for (int i = 0; i < pair.getValue().size(); i++) {
					if (pair.getValue().get(i).getTier().equals("common"))
						Inventory.inventory.add(pair.getValue().get(i));
				}
			}
		}
		Inventory.inventory.add(Item.honeyComb);
	}

	/**
	 * 
	 * @param propID
	 *            ID of the requested property.
	 * 
	 * @return The boolean value of the desired game's property.
	 * 
	 */
	public boolean getProperties(int propID) {
		if (0 > propID || properties.length <= propID) {
			// TODO : Ajouter une vraie Exception.
			System.err.println("Bad properties ID : " + propID);
			return false;
		}
		return properties[propID];
	}

	/**
	 * 
	 * @param propID
	 *            ID of the requested property.
	 * @param value
	 *            The desired value for the property.
	 * 
	 */
	public void setProperties(int propID, boolean value) {
		if (0 > propID || properties.length <= propID) {
			// TODO : Ajouter une vraie Exception.
			System.err.println("Bad properties ID : " + propID);
		} else {
			properties[propID] = value;
		}
	}

	public static void addDNA(float x) {
		if (DNAQuantity < MAX_DNA)
			DNAQuantity += x;
	}

	public static void removeDNA(float x) {
		if (DNAQuantity > 0)
			DNAQuantity -= x;
	}

	public void update() {
		Market.market.clear();
		for (Item i : Game.market_items) {
			Market.market.add(i);
		}

		for (Item b : Game.market_bees) {
			Market.market.add(b);
		}
		this.time++;
		if (this.time % 120 == 0) {
			Game.days++;
			for (int i = 0; i < Machine.machines.size(); i++) {
				Machine m = Machine.machines.get(i);
				m.onChangingDay(this);
				if (m.isRemoved())
					Machine.removeMachin(m);
			}
		}
		if (this.time % 240 == 0) {
			SQL.getUserBP(DataBaseConnection.username);
		}
		if (Game.days > 365) {
			this.years++;
			Game.days = 0;
		}
	}

	public void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glClearColor(0.89f, 0.89f, 0.89f, 1.0f);
		glOrtho(0, Display.getWidth(), Display.getHeight(), 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		
		Machine.machinesGUI();
		leftSideMenu();
		if (firstMenu) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			GUI.color(0.3f, 0.3f, 0.3f, 1.0f);
			GUI.drawString("BEENETICS", Display.getWidth() / 2 - "BEENETICS".length() * 24 / 2, 24 + Game.wheel, 24);
			if (GUI.button("SIGN UP", Display.getWidth() / 2 - 6 * 24, Display.getHeight() / 2 - 34, 300, 64)) {
				DataBaseConnection.name = "";
				DataBaseConnection.password = "";
				DataBaseConnection.mail = "";
				DataBaseConnection.hidedPass = "";
				DataBaseConnection.count = 0;
				DataBaseConnection.signupGUIOpened = true;
				firstMenu = false;
			}
			if (GUI.button("SIGN IN", Display.getWidth() / 2 - 6 * 24, Display.getHeight() / 2, 300, 64)) {
				DataBaseConnection.name = "";
				DataBaseConnection.password = "";
				DataBaseConnection.mail = "";
				DataBaseConnection.hidedPass = "";
				DataBaseConnection.count = 0;
				DataBaseConnection.signinGUIOpened = true;
				firstMenu = false;
			}
		}
		if (DataBaseConnection.signupGUIOpened) {
			DataBaseConnection.signup();
		}

		if (DataBaseConnection.signinGUIOpened) {
			DataBaseConnection.signin();
		}

		if (Market.marketGUIOpened) {
			Market.openGUI();
		}

		if (Apiary.GUIOpened) {
			((Apiary) Machine.whatApiary).openGUI();
		}

		if (Centrifuge.GUIOpened) {
			((Centrifuge) Machine.whatCentrifuge).openGUI();
		}

		if (Analyser.GUIOpened) {
			((Analyser) Machine.whatAnalyser).openGUI();
		}

		if (Isolator.GUIOpened) {
			((Isolator) Machine.whatIsolator).openGUI();
		}

		if (Inoculator.GUIOpened) {
			((Inoculator) Machine.whatInoculator).openGUI();
		}

		if (GenePool.GUIOpened) {
			((GenePool) Machine.whatGenePool).openGUI();
		}

		if (Inventory.invGUIOpened) {
			Inventory.openInvGUI();
		}

		if (DNAQuantity > 0 && anyGUI()) {
			DNAGui();
		}

		for (int i = 0; i < notifications.size(); i++) {
			if (!notifications.get(i).isFinished()) {
				notifications.get(i).animate(notifications.get(i).getMsg(), notifications.get(i).getColor());
			}
		}
	}

	private boolean anyGUI() {
		return (!Inventory.invGUIOpened && !Market.marketGUIOpened && !Centrifuge.GUIOpened && !Analyser.GUIOpened
				&& !Apiary.GUIOpened && !Isolator.GUIOpened && !GenePool.GUIOpened);
	}

	private void DNAGui() {
		glLineWidth(10f);
		glColor4f((float) Math.abs(Math.cos(angle)), 0.3f, (float) Math.abs(Math.sin(angle)), 0.3f);
		glBegin(GL_LINE_STRIP);
		float xoff = 0;
		for (float a = 306; a < 306 + DNAQuantity + 20; a += 10) {
			float offset = Utils.map(Utils.noise(xoff, yoff, 0), 0, 1, 15, -10);
			float r = 900 - 10 + offset;
			glVertex2d(a, r);
			xoff += 0.055f;
		}

		glVertex2f(306 + DNAQuantity + 10, Display.getHeight());
		glVertex2f(306, Display.getHeight());
		glEnd();
		yoff += 0.01f;
		glColor4f((float) Math.abs(Math.cos(angle)), 0.3f, (float) Math.abs(Math.sin(angle)), 0.3f);
		angle += 0.01;

		GUI.drawQuad(305, Display.getHeight() - 11, (int) (DNAQuantity), 10);
		GUI.color(0.6f, 0, 0.9f, 1);
		GUI.drawString("DNA:" + DNAQuantity, 305 + (Display.getWidth() - 305) / 2 - 49, Display.getHeight() - 44, 24);
	}

	public void leftSideMenu() {
		GUI.color(0.2f, 0.2f, 0.2f, 1);
		GUI.drawQuad(0, 0, sideMenuX, Display.getHeight());
		GUI.color(0.1f, 0.1f, 0.1f, 1);
		GUI.drawQuad(300, 0, 5, Display.getHeight());
		GUI.color(0.3f, 1, 0.3f, 1f);
		GUI.drawString("USER:" + DataBaseConnection.username, 10, 10, 16);
		if (beePoints > 0) {
			GUI.color(0.3f, 1, 0.3f, 1f);
		} else {
			GUI.color(1, 0.3f, 0.3f, 1f);
		}
		GUI.drawString(beePoints + " BP", 10, 10 + 32, 24);
		GUI.color(0.9f, 0.9f, 0.9f, 1);
		GUI.drawString("Year: " + years, 10, 20 + 32 * 2, 24);
		GUI.drawString("Day: " + days, 10, 20 + 32 * 3, 24);

		// LABO
		int h = 10 + 32 * 4 + 20;
		GUI.drawString("LABO", 10, h, 32);
		GUI.drawQuad(10, h + 32, 280, 4);
		int i;
		GUI.drawString("Machines", 10, h + 48, 24);
		for (i = 0; i < Machine.machinesStore.size(); i++) {
			Machine m = Machine.machinesStore.get(i);
			if (anyGUI()) {
				if (GUI.button(m.getName(), 10, h + 48 + 26 + i * 36, 280, 24)) {
					addMachine(m);

				}
			}
			int x = 10;
			int y = h + 48 + 26 + i * 36;
			int w = 280;
			int hh = 32;
			int mx = Mouse.getX();
			int my = Display.getHeight() - Mouse.getY();

			if (mx > x && my > y && mx < x + w && my < y + hh) {
				GUI.color(0.0f, 0.0f, 0.0f, 0.9f);
				GUI.drawQuad(x, y, w + 15, hh);

				GUI.color(0.0f, 0.7f, 1f, 1.0f);
				GUI.drawString(m.getCost() + " BP", x + 10, y + 5, 24);
			}
		}
		GUI.color(0.9f, 0.9f, 0.9f, 1);
		GUI.drawString("MENU", 10, 500, 32);
		GUI.drawQuad(10, 500 + 32, 280, 4);

		if (anyGUI()) {
			if (GUI.button("Inventory", 10, h + 48 + 26 + i * 37 + 90, 280)) {
				Inventory.invGUIOpened = true;
			}
		}
		i += 1;
		if (anyGUI()) {
			if (GUI.button("Market", 10, h + 48 + 26 + i * 37 + 90, 280)) {
				Market.marketGUIOpened = true;
				SQL.getDBBees();
				SQL.getDBItems();

			}
		}
		GUI.color(0.9f, 0.9f, 0.9f, 1);
	}

	public void addMachine(Machine m) {
		if ((Game.beePoints - m.getCost()) < 0)
			return;
		if ((Machine.machinesSize + m.getSize()) <= Machine.machinesCapacity) {
			m.onStart(this);
			Machine.machinesSize += m.getSize();
			try {
				Machine.machines.add(m.getClass().newInstance());
				SQL.addUserBP(DataBaseConnection.username, -m.getCost());
				SQL.getUserBP(DataBaseConnection.username);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public void save() {
		List<Object> arrays = new ArrayList<>();
		arrays.add(Inventory.inventory);
		arrays.add(Machine.machines);
		Utils.serializeArrayList(arrays);
		Utils.saveDay();
		Utils.saveDNA();
	}

	public static void load() {
		List<Object> arrays = new ArrayList<>();
		arrays.add(Inventory.inventory);
		arrays.add(Machine.machines);
		Utils.deserializeArrayList(arrays);
		Utils.loadDay();
		Utils.loadDNA();
	}
}
