package fr.armenari.beenetics.main.fx;

import java.io.Serializable;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import fr.armenari.beenetics.main.guis.GUI;
import fr.armenari.beenetics.main.guis.Texture;

public class Particle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6306748665408966480L;

	public boolean isRemoved = false;

	float x, y;
	double rx, ry;
	Random random = new Random();

	private float[] color;
	private Vector2f direction;
	private int size;
	private double speed;
	private int lifeTime;
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

	int time;

	public void update() {
		time++;
		if (time > lifeTime) {
			isRemoved = true;
			time = 0;
		}
		x += (rx + direction.x) * speed;
		y += (ry + direction.y) * speed;
	}

	public void render() {
		if(this.texture != null) {
			GUI.drawTexture(this.texture, x, y, size, size, color);
		} else {
			GUI.drawQuad((int) x, (int) y, size, size, color);
		}
	}

}
