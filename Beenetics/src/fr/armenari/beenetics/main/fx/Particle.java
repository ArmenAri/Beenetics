package fr.armenari.beenetics.main.fx;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import fr.armenari.beenetics.main.guis.GUI;
import fr.armenari.beenetics.main.guis.Texture;

public class Particle {

	private static Random random = new Random();
	private float x, y; // Coordonnées de base de la particule.
	private double rx, ry; // Décalage aléatoire.
	private Vector2f direction;
	private double speed;
	private int size;
	private int time;
	private int lifeTime;
	private float[] color;
	private Texture texture;

	public Particle() {
	}

	public Particle setColor(float[] color) {
		this.color = color;
		return this;
	}

	public Particle setDirection(Vector2f direction) {
		this.direction = direction;
		return this;
	}

	public Particle setSpeed(double d) {
		this.speed = d;
		return this;
	}

	public Particle setSize(int size) {
		this.size = size;
		return this;
	}

	public Particle setLifeTime(int lifeTime) {
		this.lifeTime = lifeTime;
		return this;
	}

	public Particle setTexture(Texture texture) {
		this.texture = texture;
		return this;
	}

	public Particle(Particle p, int x, int y) {
		this.direction = p.direction;
		this.color = p.color;
		this.size = p.size;
		this.speed = p.speed;
		this.lifeTime = p.lifeTime;
		this.texture = p.texture;
		this.x = x;
		this.y = y;

		rx = random.nextGaussian();
		ry = random.nextGaussian();
	}

	public void update() {
		x += (rx + direction.x) * speed;
		y += (ry + direction.y) * speed;
		time++;
	}

	public void render() {
		if (null == texture)
			GUI.drawTexture(this.texture, x, y, size, size, color);
		else
			GUI.drawQuad((int) x, (int) y, size, size, color);

	}

	public boolean isDead() {
		return time > lifeTime;
	}

}
