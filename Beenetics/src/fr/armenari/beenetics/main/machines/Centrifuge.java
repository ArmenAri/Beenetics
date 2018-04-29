package fr.armenari.beenetics.main.machines;

import java.util.ArrayList;

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
import fr.armenari.beenetics.main.items.Item;
import fr.armenari.beenetics.main.utils.Utils;

public class Centrifuge extends Machine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8218951617971285576L;
	private Item comb;
	private ArrayList<Item> producedItems;
	private ArrayList<ParticleSystem> ps;
	private Particle p;

	public static boolean GUIOpened;

	public Centrifuge() {
		super("Centrifuge", 550, 1, 50);
		ps = new ArrayList<>();
		this.producedItems = new ArrayList<>();
	}

	public void onChangingDay(Game game) {
		if (this.comb != null) {
			time++;
			if (time > life) {
				produceItemsRandomly();
				this.comb = null;
				time = 0;
				ps.clear();
			}
		}
	}

	public void putProducedItemsIntoInventory() {
		for (Item i : producedItems) {
			Inventory.inventory.add(i);
		}
		producedItems.clear();
	}

	private void produceItemsRandomly() {
		if (this.comb != null) {
			if (this.comb.getName().equals("Honey Comb")) {
				if (Utils.random(0, 1) > 0) {
					producedItems.add(Item.beeswax);
				}
				if (Utils.random(0, 1) > 0.1) {
					producedItems.add(Item.honeyDrop);
				}
			}
			if (this.comb.getName().equals("Dripping Comb")) {
				if (Utils.random(0, 1) > 0) {
					producedItems.add(Item.honeyDew);
				}
				if (Utils.random(0, 1) > 0.6) {
					producedItems.add(Item.honeyDrop);
				}
			}
			if (this.comb.getName().equals("Stringy Comb")) {
				if (Utils.random(0, 1) > 0.6) {
					producedItems.add(Item.honeyDrop);
				}
				if (Utils.random(0, 1) > 0) {
					producedItems.add(Item.propolis);
				}
			}

			if (this.comb.getName().equals("Cocoa Comb")) {
				if (Utils.random(0, 1) > 0) {
					producedItems.add(Item.beeswax);
				}
			}

			if (this.comb.getName().equals("Parched Comb")) {
				if (Utils.random(0, 1) > 0.1) {
					producedItems.add(Item.honeyDrop);
				}
				if (Utils.random(0, 1) > 0) {
					producedItems.add(Item.beeswax);
				}
			}

			if (this.comb.getName().equals("Mysterious Comb")) {
				if (Utils.random(0, 1) > 0) {
					producedItems.add(Item.pulsatingPropolis);
				}
				if (Utils.random(0, 1) > 0.6) {
					producedItems.add(Item.honeyDrop);
				}
			}

			if (this.comb.getName().equals("Silky Comb")) {
				if (Utils.random(0, 1) > 0) {
					producedItems.add(Item.honeyDrop);
				}
			}

			if (this.comb.getName().equals("Frozen Comb")) {
				if (Utils.random(0, 1) > 0.3) {
					producedItems.add(Item.honeyDrop);
				}
				if (Utils.random(0, 1) > 0.2) {
					producedItems.add(Item.beeswax);
				}
			}
			if (this.comb.getName().equals("Mossy Comb")) {
				if (Utils.random(0, 1) > 0) {
					producedItems.add(Item.beeswax);
				}
				if (Utils.random(0, 1) > 0.1) {
					producedItems.add(Item.honeyDrop);
				}
			}
		}

	}

	public void onRemove(Game game) {
		super.onRemove(game);
	}

	public void update() {
		if (this.comb != null) {
			p = new Particle();
			p.setColor(new float[] { 0.8f, 0.8f, 0.8f, Utils.random(0.2f, 0.7f) });
			p.setLifeTime(life);
			p.setDirection(new Vector2f(Utils.random(-1, 1), Utils.random(-3, 3)));
			p.setSpeed(.6f);
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

	public void setComb(Item comb) {
		this.comb = comb;
	}

	public Item getComb() {
		return this.comb;
	}

	public void openGUI() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		GUI.color(0.3f, 0.3f, 0.3f, 1.0f);
		GUI.drawString("CENTRIFUGE", Display.getWidth() / 2 - "CENTRIFUGE".length() * 24 / 2, 24 + Game.wheel, 24);

		if (this.comb == null && GUI.button("ADD COMB", Display.getWidth() / 2 - "ADD COMB".length() * 24 / 2,
				Display.getHeight() / 2 - 128, "ADD COMB".length() * 24 + 2, 2)) {
			Inventory.invGUIOpened = true;
			Main.getGameInstance().setProperties(Game.PROP_ID_CHOOSING_COMB, true);
		} else if (this.comb != null) {
			GUI.color(0.9f, 0.1f, 0.1f, 1.0f);
			GUI.drawString("There is already a comb in this centrifuge !",
					Display.getWidth() / 2 - "There is already a comb in this centrifuge !".length() * 24 / 2,
					Display.getHeight() / 2 - 24, 24);
		}

		if (GUI.button("x", 0, 0, 32, 5)) {
			GUIOpened = false;
		}

	}

	public ArrayList<Item> getProducedItems() {
		return producedItems;
	}
}
