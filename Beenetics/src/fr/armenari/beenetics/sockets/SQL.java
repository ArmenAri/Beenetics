package fr.armenari.beenetics.sockets;

import java.util.ArrayList;

import fr.armenari.beenetics.main.Main;
import fr.armenari.beenetics.main.items.Bee;
import fr.armenari.beenetics.main.items.Item;
import fr.armenari.beenetics.main.utils.DataBaseConnection;

public class SQL {

	public static void removeItem(int id) {
		ArrayList<Object> obj = new ArrayList<>();
		obj.add("removeItem");
		obj.add(id);
		Main.getServerHandlerInstance().send(obj);
	}

	public static void insertUserData(String username, String password, String mail, float bp) {
		ArrayList<Object> obj = new ArrayList<>();
		obj.add("insertUserData");
		obj.add(username);
		obj.add(password);
		obj.add(mail);
		obj.add(bp);
		Main.getServerHandlerInstance().send(obj);
	}

	public static void setUserBP(String userName, float value) {
		ArrayList<Object> obj = new ArrayList<>();
		obj.add("setUserBP");
		obj.add(userName);
		obj.add(value);
		Main.getServerHandlerInstance().send(obj);
	}

	public static void setSellerBP(String userName, float value) {
		ArrayList<Object> obj = new ArrayList<>();
		obj.add("setSellerBP");
		obj.add(userName);
		obj.add(value);
		Main.getServerHandlerInstance().send(obj);
	}

	public static void getSellerBP(String userName) {
		ArrayList<Object> obj = new ArrayList<>();
		obj.add("getSellerBP");
		obj.add(userName);
		Main.getServerHandlerInstance().send(obj);
	}

	public static void insertItem(Item item) {
		ArrayList<Object> obj = new ArrayList<>();
		obj.add("insertItem");
		obj.add(item.getName());
		obj.add(item.getPrice());
		obj.add(item.getColor());
		obj.add(item.hasInfos());
		obj.add(DataBaseConnection.username);
		obj.add(item.getId());
		Main.getServerHandlerInstance().send(obj);
	}

	public static void insertBee(Bee bee) {
		ArrayList<Object> obj = new ArrayList<>();
		obj.add("insertBee");
		obj.add(bee.getName());
		obj.add(((Bee) bee).getLifeSpan());
		obj.add(((Bee) bee).getSpeed());
		obj.add(((Bee) bee).getPollination());
		obj.add(((Bee) bee).getFertility());
		obj.add(((Bee) bee).getColor());
		obj.add(((Bee) bee).isPrincess());
		obj.add(((Bee) bee).getPrice());
		obj.add(((Bee) bee).isAnalysed());
		obj.add(DataBaseConnection.username);
		obj.add(((Bee) bee).getId());
		Main.getServerHandlerInstance().send(obj);
	}

	public static void getDBBees() {
		ArrayList<Object> obj = new ArrayList<>();
		obj.add("getDBBees");
		Main.getServerHandlerInstance().send(obj);
	}

	public static void getDBItems() {
		ArrayList<Object> obj = new ArrayList<>();
		obj.add("getDBItems");
		Main.getServerHandlerInstance().send(obj);
	}

	public static void getUserBP(String userName) {
		ArrayList<Object> obj = new ArrayList<>();
		obj.add("getUserBP");
		obj.add(userName);
		Main.getServerHandlerInstance().send(obj);
	}

	public static void getAllUsers() {
		ArrayList<Object> obj = new ArrayList<>();
		obj.add("getAllUsers");
		Main.getServerHandlerInstance().send(obj);
	}

	public static void addUserBP(String username, float f) {
		ArrayList<Object> obj = new ArrayList<>();
		obj.add("addUserBP");
		obj.add(username);
		obj.add(f);
		Main.getServerHandlerInstance().send(obj);
	}
}