package fr.armenari.beeneticsserver.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientThread implements Runnable {

	private int uuid;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Thread t;

	public ClientThread(Socket socket, int uuid) {
		this.socket = socket;
		this.uuid = uuid;
		try {
			this.in = new ObjectInputStream(socket.getInputStream());
			this.out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		t = new Thread(this, "Client " + uuid + " thread");
	}
	
	@SuppressWarnings("deprecation")
	public void disconnect() {
		System.out.println("Client " + this.uuid + " disconnected");
		try {
			this.in.close();
			this.out.close();
			this.socket.close();
			t.stop();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
	}
	
	public void sendClient(Object object) {
		try {
			out.writeObject(object);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
