package fr.armenari.beeneticsserver.main;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	
	public static void main(String[] args) {
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(5588);
			Request.peer();
			Thread t1 = new Thread(new AcceptClient(serverSocket));
			t1.start();
			System.out.println("[SERVER] : Thread started !");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
