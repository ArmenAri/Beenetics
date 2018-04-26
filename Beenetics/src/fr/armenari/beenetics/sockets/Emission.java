package fr.armenari.beenetics.sockets;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import fr.armenari.beenetics.main.Main;

public class Emission {

	private ObjectOutputStream out;

	public Emission(ObjectOutputStream out) {
		this.out = out;
	}

	public void sending(ArrayList<Object> obj) {
		try {
			out.writeObject(obj);
			out.flush();
		} catch (IOException e) {
			try {
				Main.out.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}
