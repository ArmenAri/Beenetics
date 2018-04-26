package fr.armenari.beeneticsserver.main;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class Emission {

	ObjectOutputStream out;

	public Emission(ObjectOutputStream out) {
		this.out = out;
	}

	public void sendClient(Object object) {
		try {
			out.writeObject(object);
			out.flush();
		} catch (IOException e) {

		}
	}
}
