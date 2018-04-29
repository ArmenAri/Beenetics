package fr.armenari.beenetics.main.machines;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import fr.armenari.beenetics.main.fx.Particle;
import fr.armenari.beenetics.main.fx.ParticleSystem;
import fr.armenari.beenetics.main.game.Game;
import fr.armenari.beenetics.main.game.Inventory;
import fr.armenari.beenetics.main.guis.GUI;
import fr.armenari.beenetics.main.guis.Texture;
import fr.armenari.beenetics.main.items.Bee;
import fr.armenari.beenetics.main.items.Item;
import fr.armenari.beenetics.main.items.Vial;
import fr.armenari.beenetics.main.utils.Utils;

public class Inoculator extends Machine {
	/**
	 * 
	 */
	private static final long serialVersionUID = -456183780992267280L;
	private Bee bee;
	private Vial vial;
	private ArrayList<ParticleSystem> ps;
	private Particle p;

	private ArrayList<Item> producedItems;
	public static boolean GUIOpened;

	public Inoculator() {
		super("Inoculator", 2500, 1, 10);
		ps = new ArrayList<>();
		this.producedItems = new ArrayList<>();
	}

	public void onChangingDay(Game game) {
		if (this.bee != null && this.vial != null) {
			time++;
			Game.removeDNA(2.75f);
			if (time > life) {
				produceGMO();
				this.bee = null;
				this.vial = null;
				time = 0;
			}
		}
	}

	private void produceGMO() {
		switch (this.vial.getTrait().keySet().toArray()[0].toString()) {
		case "lifespan":
			this.bee.setLifeSpan(this.vial.getTrait().get("lifespan"));
			break;
		case "speed":
			this.bee.setSpeed(this.vial.getTrait().get("speed"));
			break;
		case "pollination":
			this.bee.setPollination(this.vial.getTrait().get("pollination"));
			break;
		case "fertility":
			this.bee.setFertility(this.vial.getTrait().get("fertility"));
			break;
		default:
			break;
		}
		producedItems.add(this.bee);
	}

	public void putProducedItemsIntoInventory() {
		for (Item i : producedItems) {
			Inventory.inventory.add(i);
		}
		producedItems.clear();
	}

	public void onRemove(Game game) {
		super.onRemove(game);
	}

	public Bee getBee() {
		return bee;
	}

	public void setBee(Bee bee) {
		this.bee = bee;

	}

	public void openGUI() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		GUI.color(0.3f, 0.3f, 0.3f, 1.0f);
		GUI.drawString("inoculator", Display.getWidth() / 2 - "inoculator".length() * 24 / 2, 24 + Game.wheel, 24);

		if (this.bee == null && GUI.button("ADD BEE", Display.getWidth() / 2 - "ADD BEE".length() * 24 / 2,
				Display.getHeight() / 2 - 128, "ADD BEE".length() * 24 + 2, 2)) {
			Inventory.invGUIOpened = true;
			Game.getInstance().setProperties(Game.PROP_ID_CHOOSING_INOCULATOR_BEE, true);
			Game.getInstance().setProperties(Game.PROP_ID_CHOOSING_INOCULATOR_VIAL, false);
		} else if (this.bee != null) {
			GUI.color(0.9f, 0.1f, 0.1f, 1.0f);
			GUI.drawString("There is already a bee in this inoculator !",
					Display.getWidth() / 2 - "There is already a bee in this inoculator !".length() * 24 / 2,
					Display.getHeight() / 2 - 128, 24);
		}

		if (this.vial == null && GUI.button("ADD VIAL", Display.getWidth() / 2 - "ADD VIAL".length() * 24 / 2,
				Display.getHeight() / 2 - 88, "ADD VIAL".length() * 24 + 2, 2)) {
			Inventory.invGUIOpened = true;
			Game.getInstance().setProperties(Game.PROP_ID_CHOOSING_INOCULATOR_BEE, false);
			Game.getInstance().setProperties(Game.PROP_ID_CHOOSING_INOCULATOR_VIAL, true);
		} else if (this.vial != null) {
			GUI.color(0.9f, 0.1f, 0.1f, 1.0f);
			GUI.drawString("There is already a vial in this inoculator !",
					Display.getWidth() / 2 - "There is already a vial in this inoculator !".length() * 24 / 2,
					Display.getHeight() / 2 - 88, 24);
		}

		if (GUI.button("x", 0, 0, 32, 5)) {
			GUIOpened = false;
		}

	}

	public void update() {
		if (this.bee != null && this.vial != null) {
			p = new Particle();
			p.setColor(new float[] { Utils.random(0f, 1f), 0.5f, Utils.random(0f, 1f), Utils.random(0.2f, 0.7f) });
			p.setLifeTime(life);
			p.setDirection(new Vector2f(Utils.random(-1, 1), Utils.random(-5, 5)));
			p.setSpeed(.6f);
			p.setSize(20);
			p.setTexture(Texture.particle);
			ps.add(new ParticleSystem((int) position.x + Machine.machineSizeX / 2,
					(int) position.y + Machine.machineSizeY / 2, 1, p));
		}
		for (ParticleSystem particleSystem : ps) {
			particleSystem.update();
			particleSystem.render();
		}
	}

	public ArrayList<Item> getProducedItems() {
		return producedItems;
	}

	public Vial getVial() {
		return vial;
	}

	public void setVial(Vial vial) {
		this.vial = vial;
	}
}
