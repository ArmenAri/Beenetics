package fr.armenari.beenetics.main.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import fr.armenari.beenetics.main.game.Game;
import fr.armenari.beenetics.main.guis.GUI;
import fr.armenari.beenetics.main.items.Bee;
import fr.armenari.beenetics.main.items.Item;
import fr.armenari.beenetics.sockets.SQL;

public class DataBaseConnection {

	static Connection cnx;
	static Statement st;
	static ResultSet rst;
	public static boolean signupGUIOpened = false;
	public static boolean signinGUIOpened = false;

	public static String name = "";
	public static String password = "";
	public static String mail = "";
	public static String hidedPass = "";

	static int x = Display.getWidth() / 2 + 24 * 1;
	static int y = Display.getHeight() / 2 - 64;

	public static int count = 0;
	public static String username = "";

	public static Connection connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7233472";
			String userName = "sql7233472";
			String password = "gjepIbrCMu";
			Connection cnx = DriverManager.getConnection(url, userName, password);
			return cnx;
		} catch (Exception e) {
			System.out.println("[ERROR] SQLException" + e);
			return null;
		}
	}

	public static void insertItem(String table, Item item, String player) {
		insertItem(table, item.getName(), item.getPrice(), item.getColor(), item.hasInfos(), player, item.getId());
	}

	public static void insertBee(String table, Bee bee, String player) {
		insertBee(table, bee.getName(), bee.getLifeSpan(), (float) bee.getSpeed(), (float) bee.getPollination(),
				bee.getFertility(), bee.getColor(), bee.isPrincess(), bee.getPrice(), bee.isAnalysed(), player,
				bee.getId());
	}

	public static void insertBee(String table, String name, int lifespan, float speed, float pollination, int fertility,
			float[] color, boolean isPrincess, float price, boolean isAnalysed, String player, int id) {
		System.out.println(id);
		String col = "";
		for (int i = 0; i < color.length; i++) {
			col += color[i] + ",";
		}
		col = col.substring(0, col.length() - 1);
		int isA = (isAnalysed) ? 0 : 1;
		int isP = (isPrincess) ? 0 : 1;
		try {
			String query = "INSERT INTO `" + table
					+ "`(`name`, `lifespan`, `speed`, `pollination`,`fertility`, `color`, `isPrincess`,`price`,`isAnalysed`, `seller`, `id`) VALUES ('"
					+ name + "','" + lifespan + "','" + speed + "','" + pollination + "','" + fertility + "','" + col
					+ "','" + isP + "','" + price + "','" + isA + "','" + player + "','" + id + "')";
			cnx = connect();
			st = cnx.createStatement();
			st.executeUpdate(query);
			System.out.println("[INFO] Bee bien ajouté !");
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void insertItem(String table, String name, float price, float[] color, boolean hasInfos,
			String player, int id) {
		String col = "";
		for (int i = 0; i < color.length; i++) {
			col += color[i] + ",";
		}
		col = col.substring(0, col.length() - 1);
		int hi = (hasInfos) ? 0 : 1;
		try {
			String query = "INSERT INTO `" + table + "`(`name`, `price`, `color`, `hasInfos`, `seller`, `id`) VALUES ('"
					+ name + "','" + price + "','" + col + "','" + hi + "','" + player + "','" + id + "')";
			cnx = connect();
			st = cnx.createStatement();
			st.executeUpdate(query);
			System.out.println("[INFO] Item bien ajouté !");
			cnx.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void signup() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GUI.color(0.3f, 0.3f, 0.3f, 1.0f);
		GUI.drawString("Sign up", Display.getWidth() / 2 - "Sign up".length() * 24 / 2, 24 + Game.wheel, 24);

		while (Keyboard.next() && count < 3) {
			Keyboard.enableRepeatEvents(false);
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
					count += 1;
				}
				if (count == 0) {
					if (Keyboard.getEventKey() == Keyboard.KEY_BACK && name.length() > 0) {
						name = name.substring(0, name.length() - 1);
					} else {
						name += Character.toString(Keyboard.getEventCharacter());
					}
				} else if (count == 1) {
					if ((Keyboard.getEventKey() != Keyboard.KEY_RETURN)) {
						if (Keyboard.getEventKey() == Keyboard.KEY_BACK && password.length() > 0) {
							password = password.substring(0, password.length() - 1);
						} else {
							password += Character.toString(Keyboard.getEventCharacter());
						}
					}
				} else if (count == 2) {
					if ((Keyboard.getEventKey() != Keyboard.KEY_RETURN)
							&& (Keyboard.getEventKey() != Keyboard.KEY_RMENU)) {
						if (Keyboard.getEventKey() == Keyboard.KEY_BACK && mail.length() > 0) {
							mail = mail.substring(0, mail.length() - 1);
						} else {
							mail += Character.toString(Keyboard.getEventCharacter());
						}
					}
				}
			}
		}
		if (count == 3) {
			count++;
		}

		GUI.drawString("USERNAME:", Display.getWidth() / 2 - 24 * 8, Display.getHeight() / 2 - 64, 24);
		GUI.drawString("PASSWORD:", Display.getWidth() / 2 - 24 * 8, Display.getHeight() / 2 - 20, 24);
		GUI.drawString("E-MAIL:", Display.getWidth() / 2 - 24 * 8, Display.getHeight() / 2 + 24, 24);

		name = Utils.purge(name);
		password = Utils.purge(password);
		mail = Utils.purge(mail);
		GUI.drawString(name, x, y, 24);
		System.out.println(password.length());
		hidedPass = "";
		for (int i = 0; i < password.length(); i++) {
			hidedPass += "°";
		}
		GUI.drawString(hidedPass, x, y + 44, 24);
		GUI.drawString(mail, x, y + 88, 24);

		if (GUI.button("SIGN UP", x - 24 * 9, y + 44 + 74, "SIGN UP".length() * 24, 1)) {
			if (mail.length() <= 0 || password.length() <= 0 || name.length() <= 0) {
				Game.firstMenu = true;
				signupGUIOpened = false;
			} else {
				signupGUIOpened = false;
				Game.firstMenu = true;
				SQL.insertUserData(name, password, mail, 300);
			}
		}
		SQL.getAllUsers();
	}

	public static void signin() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GUI.color(0.3f, 0.3f, 0.3f, 1.0f);
		GUI.drawString("Sign in", Display.getWidth() / 2 - "Sign in".length() * 24 / 2, 24 + Game.wheel, 24);

		while (Keyboard.next() && count < 2) {
			Keyboard.enableRepeatEvents(false);
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
					count += 1;
				}
				if (count == 0) {
					if (Keyboard.getEventKey() == Keyboard.KEY_BACK && name.length() > 0) {
						name = name.substring(0, name.length() - 1);
					} else {
						name += Character.toString(Keyboard.getEventCharacter());
					}
				} else if (count == 1) {
					if ((Keyboard.getEventKey() != Keyboard.KEY_RETURN)) {
						if (Keyboard.getEventKey() == Keyboard.KEY_BACK && password.length() > 0) {
							password = password.substring(0, password.length() - 1);
						} else {
							password += Character.toString(Keyboard.getEventCharacter());
						}
					}
				}
			}
		}
		if (count >= 2 && count < 3) {
			count++;
		}

		GUI.drawString("USERNAME:", Display.getWidth() / 2 - 24 * 8, Display.getHeight() / 2 - 64, 24);
		GUI.drawString("PASSWORD:", Display.getWidth() / 2 - 24 * 8, Display.getHeight() / 2 - 20, 24);

		name = Utils.purge(name);
		password = Utils.purge(password);
		mail = Utils.purge(mail);

		GUI.drawString(name, x, y, 24);
		hidedPass = "";
		for (int i = 0; i < password.length(); i++) {
			hidedPass += "°";
		}
		GUI.drawString(hidedPass, x, y + 44, 24);

		if (GUI.button("sign in", x - 24 * 9, y + 44 + 44, "sign in".length() * 24, 1)) {
			signinGUIOpened = false;
			for (int i = 0; i < Game.users.size(); i++) {
				if (Game.users.get(i)[0].equals(name) && Game.users.get(i)[1].equals(password)) {
					Game.firstMenu = false;
					signinGUIOpened = false;
					username = name;

					Game.notifications.get(0).launch("Connected", new float[] { 0, 1, 0, 0.5f });
					Utils.createSaveFiles();
					Game.load();
				}
			}
			if (username.length() <= 0) {
				Game.firstMenu = true;
				Game.notifications.get(0).launch("INCORRECT PASSWORD OR ID", new float[] { 1, 0, 0, 0.5f });
			}
		}
	}

	public static void remove(int id) {
		try {
			String query = "DELETE FROM market_bee WHERE id = '" + id + "'";
			cnx = connect();
			st = cnx.createStatement();
			st.executeUpdate(query);
			System.out.println("[INFO] Bee bien retiré !");
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			String query = "DELETE FROM market_item WHERE id = '" + id + "'";
			cnx = connect();
			st = cnx.createStatement();
			st.executeUpdate(query);
			System.out.println("[INFO] Bee bien retiré !");
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
