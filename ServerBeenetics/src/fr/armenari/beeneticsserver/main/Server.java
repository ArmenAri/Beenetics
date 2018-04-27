package fr.armenari.beeneticsserver.main;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	private ServerSocket serverSocket;
	private Thread clientListenerThread;
	
	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
			Request.peer();
			clientListenerThread = new Thread(new AcceptClient(serverSocket), "Client Listener - Beenetics Server");
			clientListenerThread.start();
			System.out.println("[SERVER] : Thread started !");
		} catch (IOException e) {
			e.printStackTrace();
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
		} finally {
			if (null != s) s.stop();
			System.out.println("Server stopped.");
		}
	}
}
