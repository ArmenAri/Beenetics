package fr.armenari.beeneticsserver.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Reception implements Runnable {

	ObjectInputStream in;
	ObjectOutputStream out;

	public Reception(ObjectInputStream in, ObjectOutputStream out) {
		this.in = in;
		this.out = out;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			while (true) {
				try {
					Object d_m = in.readObject();
					out.writeObject(Request.treatRequest((ArrayList<Object>) d_m));
					out.flush();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {

		}
	}
}
