package fr.armenari.beenetics.main;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import fr.armenari.beenetics.main.game.Game;
import fr.armenari.beenetics.main.utils.IconLoader;
import fr.armenari.beenetics.sockets.ServerHandler;

public class Main {

	private static Game game;
	private static ServerHandler serverHandler;

	/**
	 * 
	 * @return The game's instance
	 */
	public static Game getGameInstance() {
		return game;
	}

	/**
	 * 
	 * @return The server handler's instance
	 */
	public static ServerHandler getServerHandlerInstance() {
		return serverHandler;
	}

	private static void initDisplay() {
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
	}

	public static void main(String[] args) {
		initDisplay();
		serverHandler = new ServerHandler(args[0], args[1]);
		game = new Game();

		while (!Display.isCloseRequested()) {
			Display.sync(60);
			getGameInstance().update();
			getGameInstance().render();
			Display.update();
		}
		getGameInstance().save();
		Display.destroy();
	}
}
