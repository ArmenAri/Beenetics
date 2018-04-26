package fr.armenari.beeneticsserver.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class AcceptClient implements Runnable {

	private ServerSocket socketserver;
	private Socket socket;

	public static Emission emission;

	ObjectInputStream in;
	ObjectOutputStream out;

	private Thread t1;

	public AcceptClient(ServerSocket s) {
		this.socketserver = s;
	}

	public void run() {
		try {
			while (true) {
				this.socket = this.socketserver.accept();
				in = new ObjectInputStream(socket.getInputStream());
				out = new ObjectOutputStream(socket.getOutputStream());
				//out.flush(); Je l'ai commenté et ça à l'air de quand même marcher ! :p
				t1 = new Thread(new Reception(in, out));
				t1.start();
				System.out.println("[SERVER] : Client " + this.socket.getInetAddress().getHostAddress() + " connected !");
			}
		} catch (IOException e) {
			e.printStackTrace();
			Request.close();
		}
	}
}
