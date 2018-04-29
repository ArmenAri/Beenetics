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

public class GenePool extends Machine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8727518004869755790L;

	private Bee bee;

	private ArrayList<ParticleSystem> ps;
	private Particle p;

	public static boolean GUIOpened;

	public GenePool() {
		super("Gene Pool", 850, 1, 50);
		ps = new ArrayList<>();
	}

	public void onChangingDay(Game game) {
		if (this.bee != null) {
			time++;
			Game.addDNA(10);
			if (time > life) {
				this.bee = null;
				time = 0;
				ps.clear();
			}
		}
	}

	public void update() {
		if (this.bee != null) {
			p = new Particle();
			p.setColor(new float[] { Utils.random(0.2f, 0.7f), Utils.random(0.2f, 0.7f), Utils.random(0.2f, 0.7f),
					Utils.random(0.2f, 0.7f) });
			p.setLifeTime(life * 10);
			p.setDirection(new Vector2f(Utils.random(-2, 2), Utils.random(2, -5)));
			p.setSpeed(.4f);
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

	public void openGUI(String name, Item[] content, String[] buttons, boolean[] guis, String[] already) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		GUI.color(0.3f, 0.3f, 0.3f, 1.0f);
		GUI.drawString(name, Display.getWidth() / 2 - name.length() * 24 / 2, 24 + Game.wheel, 24);

		for (int i = 0; i < content.length; i++) {
			if (content[i] == null && GUI.button(buttons[i], Display.getWidth() / 2 - buttons[i].length() * 24 / 2,
					Display.getHeight() / 2 - 128, buttons[i].length() * 24 + 2, 2)) {
				guis[i] = true;
				for (int j = 0; j < guis.length; j++) {
					if (j != i) {
						guis[j] = false;
					}
				}
				Inventory.invGUIOpened = true;
			} else if (content[i] != null) {
				GUI.color(0.9f, 0.1f, 0.1f, 1.0f);
				GUI.drawString(already[i], Display.getWidth() / 2 - already[i].length() * 24 / 2,
						Display.getHeight() / 2 - 24, 24);
			}

			if (GUI.button("x", 0, 0, 32, 5)) {
				GUIOpened = false;
			}
		}
	}

	public void openGUI() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		GUI.color(0.3f, 0.3f, 0.3f, 1.0f);
		GUI.drawString("GENE POOL", Display.getWidth() / 2 - "GENE POOL".length() * 24 / 2, 24 + Game.wheel, 24);

		if (this.bee == null && GUI.button("ADD BEE", Display.getWidth() / 2 - "ADD BEE".length() * 24 / 2,
				Display.getHeight() / 2 - 128, "ADD BEE".length() * 24 + 2, 2)) {
			Inventory.invGUIOpened = true;
			Game.getInstance().setProperties(Game.PROP_ID_CHOOSING_GENE_POOL_BEE, true);
		} else if (this.bee != null) {
			GUI.color(0.9f, 0.1f, 0.1f, 1.0f);
			GUI.drawString("There is already a bee in this gene pool !",
					Display.getWidth() / 2 - "There is already a bee in this gene pool !".length() * 24 / 2,
					Display.getHeight() / 2 - 24, 24);
		}

		if (GUI.button("x", 0, 0, 32, 5)) {
			GUIOpened = false;
		}

	}

	public void onRemove(Game game) {
		super.onRemove(game);
	}

	public Bee getBee() {
		return bee;
	}

	public void setBee(Bee bee) {
		this.life = bee.getLifeSpan();
		this.bee = bee;
	}

}
