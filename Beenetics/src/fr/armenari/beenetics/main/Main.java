package fr.armenari.beenetics.main;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import fr.armenari.beenetics.main.game.Game;
import fr.armenari.beenetics.main.utils.IconLoader;
import fr.armenari.beenetics.sockets.Emission;
import fr.armenari.beenetics.sockets.Reception;

public class Main {

	public static Game game;

	public static Socket socket;

	public static ObjectOutputStream out = null;
	public static ObjectInputStream in = null;

	public static Emission emission;

	public Main() {
		game = new Game();
	}

	public void update() {
		game.update();
	}

	public void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glClearColor(0.89f, 0.89f, 0.89f, 1.0f);
		glOrtho(0, Display.getWidth(), Display.getHeight(), 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		game.render();
	}

	public void save() {
		game.save();
	}

	public void load() {
		Game.load();
	}

	public static void main(String[] args) {

		try {
			Display.setDisplayMode(new DisplayMode(1600, 900));
			Display.setTitle("Beenetics");
			Display.setResizable(false);
			Display.setIcon(IconLoader.load("/icon.png"));
			Display.create();
			glClearColor(0.89f, 0.89f, 0.89f, 1.0f);
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		/*
		 * Connection to the server
		 */
		try {
			System.out.println("[CLIENT] Connection to the server !");
			socket = new Socket(args[0], Integer.parseInt(args[1]));
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(socket.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		emission = new Emission(out);

		Thread t2 = new Thread(new Reception(in));
		t2.start();

		Main main = new Main();
		Game.firstMenu = true;

		while (!Display.isCloseRequested()) {
			Display.sync(60);
			main.update();
			main.render();
			Display.update();
		}
		main.save();
		Display.destroy();
		System.exit(0);
	}
}