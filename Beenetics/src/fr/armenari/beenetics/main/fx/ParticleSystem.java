package fr.armenari.beenetics.main.fx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ParticleSystem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2800577819770462750L;

	private List<Particle> particles = new ArrayList<Particle>();

	public ParticleSystem(int x, int y, int quantity, Particle p) {
		for (int i = 0; i < quantity; i++) {
			particles.add(new Particle(p, x, y));
		}
	}

	public void update() {
		for (int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);
			if (p.isRemoved) {
				particles.remove(p);
			}
			p.update();
		}
	}

	public void render() {
		for (Particle p : particles) {
			p.render();
		}
	}
}