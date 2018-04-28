package fr.armenari.beeneticsserver.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.List;

public class Server implements Runnable {
	private ServerSocket serverSocket;
	private Thread clientListenerThread;
	private List<ClientThread> clients = new LinkedList<>();
	private int uuidNextClient;

	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
			Request.peer();
			clientListenerThread = new Thread(this, "Beenetics Server");
			clientListenerThread.start();
			System.out.println("[SERVER] : Thread started !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				clients.add(new ClientThread(serverSocket.accept(), uuidNextClient++));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void stop() {

	}

	public static void main(String[] args) {
		Server s = null;
		try {
			s = new Server(Integer.parseInt(args[0]));
		} catch (NumberFormatException e) {
			System.err.println("You must enter a valid port !");
			e.printStackTrace();
		}
		if (null != s)
			s.stop();
		System.out.println("Server stopped.");
	}
}
