package fr.armenari.beeneticsserver.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Request {

	public static Connection cnx;
	static Statement st;
	static ResultSet rst;

	public static void peer() {
		cnx = connect();
	}

	public static void close() {
		try {
			cnx.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Object treatRequest(ArrayList<Object> query) {
		if (query.size() >= 1) {
			ArrayList<Object> r = new ArrayList<>();
			switch ((String) query.get(0)) {
			case "getUserBP":
				r.add("BP");
				r.add(getUserBP((String) query.get(1)));
				break;
			case "setUserBP":
				setUserBP((String) query.get(1), (float) query.get(2));
				r.add("SET_BP_DONE");
				break;
			case "getSellerBP":
				r.add("SELLER_BP");
				r.add(getUserBP((String) query.get(1)));
				break;
			case "setSellerBP":
				setUserBP((String) query.get(1), (float) query.get(2));
				r.add("SET_SELLER_BP_DONE");
				break;
			case "insertItem":
				insertItem((String) query.get(1), (float) query.get(2), (float[]) query.get(3), (boolean) query.get(4),
						(String) query.get(5), (int) query.get(6));
				System.out.println("[SERVER] : Item " + (int) query.get(6) + " added to market successfully !");
				r.add("INSERT_ITEM_TO_MARKET_DONE");
				break;
			case "insertBee":
				insertBee((String) query.get(1), (int) query.get(2), (float) query.get(3), (float) query.get(4),
						(int) query.get(5), (float[]) query.get(6), (boolean) query.get(7), (float) query.get(8),
						(boolean) query.get(9), (String) query.get(10), (int) query.get(11));
				System.out.println("[SERVER] : Bee " + (int) query.get(11) + " added to market successfully !");
				r.add("INSERT_BEE_TO_MARKET_DONE");
				break;
			case "getAllUsers":
				r.add("USERS");
				r.add(getAllUsers());
				break;
			case "getDBItems":
				r.add("ITEMS");
				r.add(getDBItems());
				break;
			case "getDBBees":
				r.add("BEES");
				r.add(getDBBees());
				break;
			case "removeItem":
				remove((int) query.get(1));
				System.out.println("[SERVER] : Item " + (int) query.get(1) + " removed from market successfully !");
				r.add("ITEM_REMOVE_DONE");
				break;
			case "insertUserData":
				insertUserData((String) query.get(1), (String) query.get(2), (String) query.get(3),
						(float) query.get(4));
				System.out.println("[SERVER] : User " + (String) query.get(1) + " added to DB successfully !");
				r.add("USER_ADDED_SUCCESSFULLY");
				break;
			case "addUserBP":
				addUserBP((String) query.get(1), (float) query.get(2));
				System.out.println("[SERVER] : Adding " + (float) query.get(2) + " BPs to " + (String) query.get(1)
						+ " successfully !");
				r.add("BP_ADDED_SUCCESSFULLY");
				break;
			default:
				break;
			}
			return r;
		} else {
			return "Can't treat request !";
		}
	}

	private static void addUserBP(String string, float f) {
		float userBP = 0;
		try {
			st = cnx.createStatement();
			rst = st.executeQuery("SELECT beepoints FROM users WHERE username = '" + string + "'");
			while (rst.next()) {
				userBP = rst.getFloat("beepoints");
			}
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			st = cnx.createStatement();
			System.out.println("[SERVER_INFO] : ADDING: " + f + " BPs to USER !");
			String query = "update users set beepoints = ? where username = ?";
			PreparedStatement preparedStmt = cnx.prepareStatement(query);
			preparedStmt.setFloat(1, userBP + f);
			preparedStmt.setString(2, string);
			preparedStmt.executeUpdate();
			preparedStmt.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/beenetics";
			String userName = "root";
			String password = "";
			Connection cnx = DriverManager.getConnection(url, userName, password);
			return cnx;
		} catch (Exception e) {
			System.out.println("[ERROR] SQLException");
			e.printStackTrace();
			return null;
		}
	}

	public static void insertBee(String name, int lifespan, float speed, float pollination, int fertility,
			float[] color, boolean isPrincess, float price, boolean isAnalysed, String player, int id) {
		String col = "";
		for (int i = 0; i < color.length; i++) {
			col += color[i] + ",";
		}
		col = col.substring(0, col.length() - 1);
		int isA = (isAnalysed) ? 0 : 1;
		int isP = (isPrincess) ? 0 : 1;
		try {
			String query = "INSERT INTO `market_bee` (`name`, `lifespan`, `speed`, `pollination`,`fertility`, `color`, `isPrincess`,`price`,`isAnalysed`, `seller`, `id`) VALUES ('"
					+ name + "','" + lifespan + "','" + speed + "','" + pollination + "','" + fertility + "','" + col
					+ "','" + isP + "','" + price + "','" + isA + "','" + player + "','" + id + "')";
			st = cnx.createStatement();
			st.executeUpdate(query);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void insertItem(String name, float price, float[] color, boolean hasInfos, String player, int id) {
		String col = "";
		for (int i = 0; i < color.length; i++) {
			col += color[i] + ",";
		}
		col = col.substring(0, col.length() - 1);
		int hi = (hasInfos) ? 0 : 1;
		try {
			String query = "INSERT INTO `market_item` (`name`, `price`, `color`, `hasInfos`, `seller`, `id`) VALUES ('"
					+ name + "','" + price + "','" + col + "','" + hi + "','" + player + "','" + id + "')";
			st = cnx.createStatement();
			st.executeUpdate(query);
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static ArrayList<String[]> getAllUsers() {
		ArrayList<String[]> users = new ArrayList<>();
		String name;
		String password;
		String mail;
		try {
			st = cnx.createStatement();
			rst = st.executeQuery("SELECT * FROM users");
			while (rst.next()) {
				String[] user = new String[3];
				name = rst.getString("username");
				password = rst.getString("password");
				mail = rst.getString("email");
				user[0] = name;
				user[1] = password;
				user[2] = mail;
				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	public static ArrayList<Object> getDBItems() {
		ArrayList<Object> dbitems = new ArrayList<>();
		String name;
		float price;
		int hi;
		boolean hasInfos;
		float[] color = new float[4];
		int id = 0;
		String seller;
		try {
			st = cnx.createStatement();
			rst = st.executeQuery("SELECT * FROM " + "market_item");
			while (rst.next()) {
				name = rst.getString("name");
				price = rst.getFloat("price");
				hi = rst.getInt("hasInfos");
				seller = rst.getString("seller");
				id = rst.getInt("id");
				hasInfos = (hi == 0) ? true : false;
				String[] parts = rst.getString("color").split(",");
				for (int i = 0; i < parts.length; i++) {
					color[i] = Float.parseFloat(parts[i]);
				}
				dbitems.add(name);
				dbitems.add(color);
				dbitems.add(price);
				dbitems.add(hasInfos);
				dbitems.add(seller);
				dbitems.add(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbitems;
	}

	public static ArrayList<Object> getDBBees() {
		ArrayList<Object> dbitems = new ArrayList<>();
		String name;
		int lifespan = 0;
		float pollination = 0;
		int fertility = 0;
		float speed = 0;
		boolean isPrincess;
		int isP;
		boolean isAnalaysed;
		int isA;
		float price;
		int id = 0;
		String seller;
		try {
			st = cnx.createStatement();
			rst = st.executeQuery("SELECT * FROM " + "market_bee");
			while (rst.next()) {
				float[] color = new float[4];
				name = rst.getString("name");
				price = rst.getFloat("price");
				lifespan = rst.getInt("lifespan");
				fertility = rst.getInt("fertility");
				pollination = rst.getFloat("pollination");
				speed = rst.getFloat("speed");
				isP = rst.getInt("isPrincess");
				seller = rst.getString("seller");
				isPrincess = (isP == 0) ? true : false;
				isA = rst.getInt("isAnalysed");
				id = rst.getInt("id");
				isAnalaysed = (isA == 0) ? true : false;
				String[] parts = rst.getString("color").split(",");
				for (int i = 0; i < parts.length; i++) {
					color[i] = Float.parseFloat(parts[i]);
				}
				dbitems.add(name);
				dbitems.add(lifespan);
				dbitems.add(speed);
				dbitems.add(pollination);
				dbitems.add(fertility);
				dbitems.add(color);
				dbitems.add(isPrincess);
				dbitems.add(price);
				dbitems.add(isAnalaysed);
				dbitems.add(seller);
				dbitems.add(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbitems;
	}

	private static void insertUserData(String name, String password, String mail, float beepoints) {
		try {
			String query = "INSERT INTO `users` (`username`, `password`, `email`, `beepoints`) VALUES ('" + name + "','"
					+ password + "','" + mail + "','" + beepoints + "')";
			st = cnx.createStatement();
			st.executeUpdate(query);
			System.out.println("[INFO] User added !");
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void setUserBP(String username, float beepoints) {
		try {
			st = cnx.createStatement();
			System.out.println("[SERVER_INFO] : SETTING_USER_BP to " + beepoints);
			String query = "update users set beepoints = ? where username = ?";
			PreparedStatement preparedStmt = cnx.prepareStatement(query);
			preparedStmt.setFloat(1, beepoints);
			preparedStmt.setString(2, username);
			preparedStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static float getUserBP(String username) {
		float res = 0.0f;
		try {
			st = cnx.createStatement();
			rst = st.executeQuery("SELECT beepoints FROM users WHERE username = '" + username + "'");
			while (rst.next()) {
				System.out.println(rst.getFloat("beepoints") + " : " + username);
				res = rst.getFloat("beepoints");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public static void remove(int id) {
		try {
			String query = "DELETE FROM market_bee WHERE id = '" + id + "'";
			st = cnx.createStatement();
			st.executeUpdate(query);
			System.out.println("[INFO] Bee bien retiré !");
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			String query = "DELETE FROM market_item WHERE id = '" + id + "'";
			st = cnx.createStatement();
			st.executeUpdate(query);
			System.out.println("[INFO] Bee bien retiré !");
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}