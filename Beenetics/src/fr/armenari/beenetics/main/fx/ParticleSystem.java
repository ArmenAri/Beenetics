package fr.armenari.beenetics.main.fx;

import java.util.LinkedList;
import java.util.List;

public class ParticleSystem {


	private List<Particle> particles = new LinkedList<>();

	public ParticleSystem(int x, int y, int quantity, Particle p) {
		for (int i = 0; i < quantity; i++) {
			particles.add(new Particle(p, x, y));
		}
	}

	public void update() {
		for (Particle particle : particles) {
			if (null != particle) {
				if (particle.isDead())
					particles.remove(particle);
				else
					particle.update();
			}
		}
	}

	public void render() {
		for (Particle p : particles)
			p.render();
	}
}