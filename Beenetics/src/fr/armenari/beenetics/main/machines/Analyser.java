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
import fr.armenari.beenetics.main.utils.Utils;

public class Analyser extends Machine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 889730715519775690L;
	private Bee bee;
	private Item honeyDrop;
	private ArrayList<ParticleSystem> ps;
	private Particle p;
	public static boolean GUIOpened;

	private ArrayList<Item> producedItems;

	public Analyser() {
		super("Analyser", 700, 1, 50);
		ps = new ArrayList<>();
		this.producedItems = new ArrayList<>();
	}

	public void onChangingDay(Game game) {
		if (this.bee != null && this.honeyDrop != null) {
			time++;
			if (time > life) {
				this.bee.setAnalysed(true);
				this.producedItems.add(this.bee);
				this.bee = null;
				this.honeyDrop = null;
				time = 0;
				ps.clear();
			}
		}
	}

	public void onRemove(Game game) {
		super.onRemove(game);
	}

	public void update() {
		if (this.bee != null && this.honeyDrop != null) {
			p = new Particle();
			p.setColor(new float[] { Utils.random(0, 1), Utils.random(0, 1), Utils.random(0, 1), Utils.random(0.2f, 0.7f) });
			p.setLifeTime(life);
			p.setDirection(new Vector2f(0, Utils.random(-3, -2)));
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

	public void setBee(Bee bee) {
		this.bee = bee;
	}

	public Bee getBee() {
		return this.bee;
	}

	public void setHoneyDrop(Item honeyDrop) {
		this.honeyDrop = honeyDrop;
	}

	public Item getHoneyDrop() {
		return this.honeyDrop;
	}

	public void openGUI() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		GUI.color(0.3f, 0.3f, 0.3f, 1.0f);
		GUI.drawString("ANALYSER", Display.getWidth() / 2 - "ANALYSER".length() * 24 / 2, 24 + Game.wheel, 24);

		if (this.bee == null && GUI.button("ADD BEE", Display.getWidth() / 2 - "ADD BEE".length() * 24 / 2,
				Display.getHeight() / 2 - 128, "ADD BEE".length() * 24 + 2, 2)) {
			Inventory.invGUIOpened = true;
			Game.getInstance().setProperties(Game.PROP_ID_CHOOSING_ANALYSER_BEE, true);
			Game.getInstance().setProperties(Game.PROP_ID_CHOOSING_ANALYSER_DROP, false);
		} else if (this.bee != null) {
			GUI.color(0.9f, 0.1f, 0.1f, 1.0f);
			GUI.drawString("There is already a bee in this analyser !",
					Display.getWidth() / 2 - "There is already a bee in this analyser !".length() * 24 / 2,
					Display.getHeight() / 2 - 128, 24);
		}

		if (this.honeyDrop == null
				&& GUI.button("ADD HONEY DROP", Display.getWidth() / 2 - "ADD HONEY DROP".length() * 24 / 2,
						Display.getHeight() / 2 - 88, "ADD HONEY DROP".length() * 24 + 2, 2)) {
			Inventory.invGUIOpened = true;
			Game.getInstance().setProperties(Game.PROP_ID_CHOOSING_ANALYSER_BEE, false);
			Game.getInstance().setProperties(Game.PROP_ID_CHOOSING_ANALYSER_DROP, true);
		} else if (this.honeyDrop != null) {
			GUI.color(0.9f, 0.1f, 0.1f, 1.0f);
			GUI.drawString("There is already a honey drop in this Analyser !",
					Display.getWidth() / 2 - "There is already a honey drop in this Analyser !".length() * 24 / 2,
					Display.getHeight() / 2 - 88, 24);
		}

		if (GUI.button("x", 0, 0, 32, 5)) {
			GUIOpened = false;
		}

	}

	public void putProducedItemsIntoInventory() {
		for (Item i : producedItems) {
			Inventory.inventory.add(i);
		}
		producedItems.clear();

	}

	public ArrayList<Item> getProducedItems() {
		return this.producedItems;
	}
}
