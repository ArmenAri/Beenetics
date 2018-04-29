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
import fr.armenari.beenetics.main.items.Frame;
import fr.armenari.beenetics.main.items.Item;
import fr.armenari.beenetics.main.utils.Utils;

public class Apiary extends Machine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5634784656980787886L;
	private Bee princess;
	private Bee drone;
	private ArrayList<ParticleSystem> ps;
	private Particle p;
	private ArrayList<Item> producedItems;
	private Item producingComb;
	private Frame frame;

	public static boolean GUIOpened = false;

	public Apiary() {
		super("Apiary", 300, 1, 50);
		ps = new ArrayList<>();
		producedItems = new ArrayList<>();
	}

	public void onChangingDay(Game game) {
		if (this.princess != null && this.drone != null) {
			time++;
			produceItems();
			if (this.frame != null) {
				this.frame.setDurability(this.frame.getDurability() - 1);
			}
			if (time > life) {
				produceDrones();

				// Game.notifications.get(0).launch("Apiary finished", new float[] {0, 1, 0,
				// 0.6f});

				if (this.frame != null) {
					if (this.frame.getDurability() > 0) {
						producedItems.add(this.frame);
						this.frame = null;
					} else {
						this.frame = null;
					}
				}
				this.princess = null;
				this.drone = null;
				time = 0;
				ps.clear();
			}
		}
	}

	private void produceDrones() {
		Bee res = null;
		for (HashMap.Entry<String, ArrayList<Bee>> pair : Bee.drones.entrySet()) {
			for (int i = 0; i < pair.getValue().size(); i++) {
				if (pair.getValue().get(i).getUsing().contains(this.princess.getName())
						&& pair.getValue().get(i).getWith().contains(this.drone.getName())) {
					res = pair.getValue().get(i);

				}
			}
		}
		if (res != null) {
			for (int j = 0; j < this.princess.getFertility(); j++) {
				float rand = Utils.random(0, 1);
				if (rand >= 0.6f) {
					String name = res.getName();
					int lifeSpan = (int) Utils.random(Math.min(res.getLifeSpan(), this.princess.getLifeSpan()),
							Math.max(res.getLifeSpan(), this.princess.getLifeSpan()));
					float speed = Utils.random((float) Math.min(res.getSpeed(), this.princess.getSpeed()),
							(float) Math.max(res.getSpeed(), this.princess.getSpeed()));
					speed = (float) ((int) (speed * 100)) / 100;
					float pollination = Utils.random(
							(float) Math.min(res.getPollination(), this.princess.getPollination()),
							(float) Math.max(res.getPollination(), this.princess.getPollination()));
					pollination = (float) ((int) (pollination * 100)) / 100;
					int fertility = (int) Utils.random(Math.min(res.getFertility(), this.princess.getFertility()),
							Math.max(res.getFertility(), this.princess.getFertility()));
					producedItems.add(new Bee(name, lifeSpan, speed, pollination, fertility, /* res.getArea(), */
							res.getColor(), false, res.getPrice(), res.getUsing(), res.getWith(), false, null));
				} else {
					String name = princess.getName();
					int lifeSpan = (int) Utils.random(Math.min(princess.getLifeSpan(), this.drone.getLifeSpan()),
							Math.max(princess.getLifeSpan(), this.drone.getLifeSpan()));
					float speed = Utils.random((float) Math.min(princess.getSpeed(), this.drone.getSpeed()),
							(float) Math.max(princess.getSpeed(), this.drone.getSpeed()));
					speed = (float) ((int) (speed * 100)) / 100;
					float pollination = Utils.random(
							(float) Math.min(princess.getPollination(), this.drone.getPollination()),
							(float) Math.max(princess.getPollination(), this.drone.getPollination()));
					pollination = (float) ((int) (pollination * 100)) / 100;
					int fertility = (int) Utils.random(Math.min(princess.getFertility(), this.drone.getFertility()),
							Math.max(princess.getFertility(), this.drone.getFertility()));
					producedItems.add(new Bee(name, lifeSpan, speed, pollination, fertility, /* princess.getArea(), */
							princess.getColor(), false, princess.getPrice(), princess.getUsing(), princess.getWith(),
							false, null));
				}
			}
			if (Utils.random(0, 1) > 0.5) {
				String name = princess.getName();
				int lifeSpan = (int) Utils.random(Math.min(princess.getLifeSpan(), this.drone.getLifeSpan()),
						Math.max(princess.getLifeSpan(), this.drone.getLifeSpan()));
				float speed = Utils.random((float) Math.min(princess.getSpeed(), this.drone.getSpeed()),
						(float) Math.max(princess.getSpeed(), this.drone.getSpeed()));
				speed = (float) ((int) (speed * 100)) / 100;
				float pollination = Utils.random(
						(float) Math.min(princess.getPollination(), this.drone.getPollination()),
						(float) Math.max(princess.getPollination(), this.drone.getPollination()));
				pollination = (float) ((int) (pollination * 100)) / 100;
				int fertility = (int) Utils.random(Math.min(princess.getFertility(), this.drone.getFertility()),
						Math.max(princess.getFertility(), this.drone.getFertility()));
				producedItems.add(new Bee(name, lifeSpan, speed, pollination, fertility,
						/* princess.getArea(), */ princess.getColor(), true, princess.getPrice(), princess.getUsing(),
						princess.getWith(), false, null));
			} else {
				String name = res.getName();
				int lifeSpan = (int) Utils.random(Math.min(res.getLifeSpan(), this.princess.getLifeSpan()),
						Math.max(res.getLifeSpan(), this.princess.getLifeSpan()));
				float speed = Utils.random((float) Math.min(res.getSpeed(), this.princess.getSpeed()),
						(float) Math.max(res.getSpeed(), this.princess.getSpeed()));
				speed = (float) ((int) (speed * 100)) / 100;
				float pollination = Utils.random((float) Math.min(res.getPollination(), this.princess.getPollination()),
						(float) Math.max(res.getPollination(), this.princess.getPollination()));
				pollination = (float) ((int) (pollination * 100)) / 100;
				int fertility = (int) Utils.random(Math.min(res.getFertility(), this.princess.getFertility()),
						Math.max(res.getFertility(), this.princess.getFertility()));
				producedItems
						.add(new Bee(name, lifeSpan, speed, pollination, fertility, /* res.getArea(), */ res.getColor(),
								true, res.getPrice(), res.getUsing(), res.getWith(), false, null));
			}

		} else {
			for (int j = 0; j < this.princess.getFertility(); j++) {
				String name = princess.getName();
				int lifeSpan = (int) Utils.random(Math.min(princess.getLifeSpan(), this.drone.getLifeSpan()),
						Math.max(princess.getLifeSpan(), this.drone.getLifeSpan()));
				float speed = Utils.random((float) Math.min(princess.getSpeed(), this.drone.getSpeed()),
						(float) Math.max(princess.getSpeed(), this.drone.getSpeed()));
				speed = (float) ((int) (speed * 100)) / 100;
				float pollination = Utils.random(
						(float) Math.min(princess.getPollination(), this.drone.getPollination()),
						(float) Math.max(princess.getPollination(), this.drone.getPollination()));
				pollination = (float) ((int) (pollination * 100)) / 100;
				int fertility = (int) Utils.random(Math.min(princess.getFertility(), this.drone.getFertility()),
						Math.max(princess.getFertility(), this.drone.getFertility()));
				producedItems.add(new Bee(name, lifeSpan, speed, pollination, fertility,
						/* princess.getArea(), */ princess.getColor(), false, princess.getPrice(), princess.getUsing(),
						princess.getWith(), false, null));
			}
			String name = princess.getName();
			int lifeSpan = (int) Utils.random(Math.min(princess.getLifeSpan(), this.drone.getLifeSpan()),
					Math.max(princess.getLifeSpan(), this.drone.getLifeSpan()));
			float speed = Utils.random((float) Math.min(princess.getSpeed(), this.drone.getSpeed()),
					(float) Math.max(princess.getSpeed(), this.drone.getSpeed()));
			speed = (float) ((int) (speed * 100)) / 100;
			float pollination = Utils.random((float) Math.min(princess.getPollination(), this.drone.getPollination()),
					(float) Math.max(princess.getPollination(), this.drone.getPollination()));
			pollination = (float) ((int) (pollination * 100)) / 100;
			int fertility = (int) Utils.random(Math.min(princess.getFertility(), this.drone.getFertility()),
					Math.max(princess.getFertility(), this.drone.getFertility()));
			producedItems.add(new Bee(name, lifeSpan, speed, pollination, fertility, /* princess.getArea(), */
					princess.getColor(), true, princess.getPrice(), princess.getUsing(), princess.getWith(), false,
					null));
		}
	}

	private void produceItems() {
		float augmentation = 0;
		if (this.frame != null) {
			augmentation = (float) (this.frame.getAugmentation() / 100.0);
		}
		if (this.princess.getTier().equals("common")) {
			this.producingComb = Item.honeyComb;
		} else if (this.princess.getTier().equals("noble")) {
			this.producingComb = Item.drippingComb;
		} else if (this.princess.getTier().equals("industrious")) {
			this.producingComb = Item.stringyComb;
		} else if (this.princess.getTier().equals("heroic")) {
			this.producingComb = Item.cocoaComb;
		} else if (this.princess.getTier().equals("infernal")) {
			this.producingComb = Item.simmeringComb;
		} else if (this.princess.getTier().equals("austere")) {
			this.producingComb = Item.parchedComb;
		} else if (this.princess.getTier().equals("end")) {
			this.producingComb = Item.mysteriousComb;
		} else if (this.princess.getTier().equals("tropical")) {
			this.producingComb = Item.silkyComb;
		} else if (this.princess.getTier().equals("frozen")) {
			this.producingComb = Item.frozenComb;
		} else if (this.princess.getTier().equals("festive")) {
			this.producingComb = Item.frozenComb;
		} else if (this.princess.getTier().equals("agrarian")) {
			this.producingComb = Item.stringyComb;
		} else if (this.princess.getTier().equals("boggy")) {
			this.producingComb = Item.mossyComb;
		} else if (this.princess.getTier().equals("luxe")) {
			this.producingComb = Item.luxeComb;
		}
		if (this.princess.getName().equals("Imperial")) {
			if (Utils.random(0, 1) > 0.85f - augmentation) {
				producedItems.add(Item.royalJelly);
			}
		}
		if (this.princess.getName().equals("Industrious")) {
			if (Utils.random(0, 1) > 0.85f - augmentation) {
				producedItems.add(Item.pollenCluster);
			}
		}
		if (this.princess.getName().equals("Icy")) {
			if (Utils.random(0, 1) > 0.95f - augmentation) {
				producedItems.add(Item.iceShard);
			}
		}
		if (this.princess.getName().equals("Glacial")) {
			if (Utils.random(0, 1) > 0.85f - augmentation) {
				producedItems.add(Item.iceShard);
			}
		}
		if (this.princess.getName().equals("Diamond")) {
			if (Utils.random(0, 1) > 0.97f - augmentation) {
				producedItems.add(Item.diamond);
			}
		}
		if (this.princess.getName().equals("Emerald")) {
			if (Utils.random(0, 1) > 0.99f - augmentation) {
				producedItems.add(Item.emerald);
			}
		}
		if (this.princess.getName().equals("Golden")) {
			if (Utils.random(0, 1) > 0.90f - augmentation) {
				producedItems.add(Item.gold);
			}
		}
		if (this.princess.getName().equals("Titanium")) {
			if (Utils.random(0, 1) > 0.76f - augmentation) {
				producedItems.add(Item.metal);
			}
		}
		if (time % 5 == 0) {
			producedItems.add(producingComb);
		}

	}

	public void onRemove(Game game) {
		super.onRemove(game);
	}

	public void update() {
		if (this.princess != null && this.drone != null) {
			p = new Particle();
			p.setColor(this.princess.getColor());
			p.setLifeTime(this.princess.getLifeSpan() + 60);
			p.setDirection(new Vector2f(Utils.random(-1, 1), Utils.random(-1, 1)));
			p.setSpeed(this.princess.getSpeed() + 0.3f);
			p.setSize(8);
			p.setTexture(Texture.bee);
			ps.add(new ParticleSystem((int) position.x + Machine.machineSizeX / 2,
					(int) position.y + Machine.machineSizeY / 2, 3, p));
		}
		for (ParticleSystem particleSystem : ps) {
			particleSystem.update();
			particleSystem.render();
		}
	}

	public Bee getPrincess() {
		return princess;
	}

	public Bee getDrone() {
		return drone;
	}

	public void setPrincess(Bee queen) {
		this.princess = queen;
		life = this.princess.getLifeSpan();
	}

	public void setDrone(Bee drone) {
		this.drone = drone;
	}

	public ArrayList<Item> getProducedItems() {
		return producedItems;
	}

	public void putProducedItemsIntoInventory() {
		for (Item i : producedItems) {
			Inventory.inventory.add(i);
		}
		producedItems.clear();
	}

	public void openGUI() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		GUI.color(0.3f, 0.3f, 0.3f, 1.0f);
		GUI.drawString("APIARY", Display.getWidth() / 2 - "APIARY".length() * 24 / 2, 24 + Game.wheel, 24);
		if (this.princess == null
				&& GUI.button("ADD PRINCESS", Display.getWidth() / 2 - "ADD PRINCESS".length() * 24 / 2,
						Display.getHeight() / 2 - 128, "ADD PRINCESS".length() * 24 + 2, 2)) {
			Inventory.invGUIOpened = true;
			Main.getGameInstance().setProperties(Game.PROP_ID_CHOOSING_PRINCESS, true);
			Main.getGameInstance().setProperties(Game.PROP_ID_CHOOSING_DRONE, false);
			Main.getGameInstance().setProperties(Game.PROP_ID_CHOOSING_ADDON, false);
		} else if (this.princess != null) {
			GUI.color(0.9f, 0.1f, 0.1f, 1.0f);
			GUI.drawString("There is already a princess in this Apiary !",
					Display.getWidth() / 2 - "There is already a princess in this Apiary !".length() * 24 / 2,
					Display.getHeight() / 2 - 128, 24);
		}

		if (this.drone == null && GUI.button("ADD DRONE", Display.getWidth() / 2 - "ADD DRONE".length() * 24 / 2,
				Display.getHeight() / 2 - 88, "ADD DRONE".length() * 24 + 2, 2)) {
			Inventory.invGUIOpened = true;
			Main.getGameInstance().setProperties(Game.PROP_ID_CHOOSING_PRINCESS, false);
			Main.getGameInstance().setProperties(Game.PROP_ID_CHOOSING_DRONE, true);
			Main.getGameInstance().setProperties(Game.PROP_ID_CHOOSING_ADDON, false);
		} else if (this.drone != null) {
			GUI.color(0.9f, 0.1f, 0.1f, 1.0f);
			GUI.drawString("There is already a drone in this Apiary !",
					Display.getWidth() / 2 - "There is already a drone in this Apiary !".length() * 24 / 2,
					Display.getHeight() / 2 - 88, 24);
		}

		if (this.frame == null && GUI.button("ADD FRAME", Display.getWidth() / 2 - "ADD FRAME".length() * 24 / 2,
				Display.getHeight() / 2 - 48, "ADD FRAME".length() * 24 + 2, 2)) {
			Inventory.invGUIOpened = true;
			Main.getGameInstance().setProperties(Game.PROP_ID_CHOOSING_PRINCESS, false);
			Main.getGameInstance().setProperties(Game.PROP_ID_CHOOSING_DRONE, false);
			Main.getGameInstance().setProperties(Game.PROP_ID_CHOOSING_ADDON, true);
		} else if (this.frame != null) {
			GUI.color(0.9f, 0.1f, 0.1f, 1.0f);
			GUI.drawString("There is already an frame in this Apiary !",
					Display.getWidth() / 2 - "There is already an frame in this Apiary !".length() * 24 / 2,
					Display.getHeight() / 2 - 48, 24);
		}

		if (GUI.button("x", 0, 0, 32, 5)) {
			GUIOpened = false;
		}
	}

	public void setFrame(Frame frame) {
		this.frame = frame;
	}

	public Frame getFrame() {
		return this.frame;
	}
}
