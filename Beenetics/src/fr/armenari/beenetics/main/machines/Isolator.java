package fr.armenari.beenetics.main.machines;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import fr.armenari.beenetics.main.Main;
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

public class Isolator extends Machine {

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

	public Isolator() {
		super("Isolator", 1599, 1, 50);
		ps = new ArrayList<>();
		this.producedItems = new ArrayList<>();
	}

	public void onChangingDay(Game game) {
		if (this.bee != null && this.vial != null) {
			time++;
			Game.removeDNA(2.75f);
			if (time > life) {
				produceRandomVial();
				this.bee = null;
				this.vial = null;
				time = 0;
			}
		}
	}

	private void produceRandomVial() {
		HashMap<String, Float> res = new HashMap<String, Float>();
		int rand = (int) Utils.random(0, 4);
		Float[] traits = new Float[4];
		traits[0] = (float) this.bee.getLifeSpan();
		traits[1] = (float) this.bee.getSpeed();
		traits[2] = (float) this.bee.getPollination();
		// traits[3] = this.bee.getArea();
		traits[3] = (float) this.bee.getFertility();
		String[] traitNames = new String[] { "lifespan", "speed", "pollination", "fertility" };
		res.put(traitNames[rand], traits[rand]);

		this.vial.setTrait(res);
		this.vial.setEmpty(false);
		this.producedItems.add(vial);
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
		GUI.drawString("ISOLATOR", Display.getWidth() / 2 - "ISOLATOR".length() * 24 / 2, 24 + Game.wheel, 24);

		if (this.bee == null && GUI.button("ADD BEE", Display.getWidth() / 2 - "ADD BEE".length() * 24 / 2,
				Display.getHeight() / 2 - 128, "ADD BEE".length() * 24 + 2, 2)) {
			Inventory.invGUIOpened = true;
			Main.getGameInstance().setProperties(Game.PROP_ID_CHOOSING_ISOLATOR_BEE, true);
			Main.getGameInstance().setProperties(Game.PROP_ID_CHOOSING_ISOLATOR_VIAL, false);
		} else if (this.bee != null) {
			GUI.color(0.9f, 0.1f, 0.1f, 1.0f);
			GUI.drawString("There is already a bee in this isolator !",
					Display.getWidth() / 2 - "There is already a bee in this isolator !".length() * 24 / 2,
					Display.getHeight() / 2 - 128, 24);
		}

		if (this.vial == null
				&& GUI.button("ADD EMPTY VIAL", Display.getWidth() / 2 - "ADD EMPTY VIAL".length() * 24 / 2,
						Display.getHeight() / 2 - 88, "ADD EMPTY VIAL".length() * 24 + 2, 2)) {
			Inventory.invGUIOpened = true;
			Main.getGameInstance().setProperties(Game.PROP_ID_CHOOSING_ISOLATOR_BEE, false);
			Main.getGameInstance().setProperties(Game.PROP_ID_CHOOSING_ISOLATOR_VIAL, true);
		} else if (this.vial != null) {
			GUI.color(0.9f, 0.1f, 0.1f, 1.0f);
			GUI.drawString("There is already an empty vial in this Isolator !",
					Display.getWidth() / 2 - "There is already an empty vial in this Isolator !".length() * 24 / 2,
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
			p.setSpeed(.5f);
			p.setSize(24);
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
