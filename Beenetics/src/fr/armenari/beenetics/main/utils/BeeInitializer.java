package fr.armenari.beenetics.main.utils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import fr.armenari.beenetics.main.items.Bee;

public class BeeInitializer {

	private static String decodeJson() {
		Scanner in;
		in = new Scanner(BeeInitializer.class.getResourceAsStream("/species.arm"));
		StringBuilder sb = new StringBuilder();
		while (in.hasNext()) {
			sb.append(in.next());
		}
		in.close();
		String outString = sb.toString();
		String key = "J@NcQfTjWnZr4u7x";
		String initVector = "5u8x/A?D(G+KbPeS";
		return FileCodecBase64.decrypt(key, initVector, outString);
	}

	private static String[] tiers = new String[] { "common", "noble", "industrious", "infernal", "austere", "end",
			"frozen", "festive", "agrarian", "boggy", "luxe" };

	public static HashMap<String, ArrayList<Bee>> readDronesFile() {
		String json = decodeJson();

		HashMap<String, ArrayList<Bee>> drones = new HashMap<>();

		JsonReader reader = null;

		reader = Json.createReader(new StringReader(json));

		int j = 0;
		JsonObject obj = reader.readObject();
		for (int i = 0; i < tiers.length; i++) {
			ArrayList<Bee> a = new ArrayList<Bee>();
			for (j = 0; j < obj.getJsonArray(tiers[i]).size(); j++) {
				ArrayList<String> using = new ArrayList<>();
				ArrayList<String> with = new ArrayList<>();
				// int[] area = new int[2];
				float[] color = new float[4];
				String name = obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonString("name").getString();
				int lifeSpan = obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonNumber("lifeSpan").intValue();
				float speed = (float) obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonNumber("speed").doubleValue();
				float pollination = (float) obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonNumber("pollination")
						.doubleValue();
				int fertility = obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonNumber("fertility").intValue();

				// area[0] =
				// obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonArray("area").getInt(0);
				// area[1] =
				// obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonArray("area").getInt(1);
				color[0] = (float) obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonArray("color").getJsonNumber(0)
						.doubleValue();
				color[1] = (float) obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonArray("color").getJsonNumber(1)
						.doubleValue();
				color[2] = (float) obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonArray("color").getJsonNumber(2)
						.doubleValue();
				color[3] = 1;

				for (int k = 0; k < obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonArray("using").size(); k++) {
					using.add(obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonArray("using").getString(k));
				}
				for (int k = 0; k < obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonArray("with").size(); k++) {
					with.add(obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonArray("with").getString(k));
				}
				a.add(new Bee(name, lifeSpan, speed, pollination, fertility, /* area, */ color, false, (i + 1) * 100,
						using, with, false, null));
			}
			drones.put(tiers[i], a);
		}
		return drones;
	}

	public static HashMap<String, ArrayList<Bee>> readQueensFile() {
		String json = decodeJson();
		HashMap<String, ArrayList<Bee>> queens = new HashMap<>();

		JsonReader reader = null;

		reader = Json.createReader(new StringReader(json));
		int j = 0;
		JsonObject obj = reader.readObject();
		for (int i = 0; i < tiers.length; i++) {
			ArrayList<Bee> a = new ArrayList<Bee>();
			for (j = 0; j < obj.getJsonArray(tiers[i]).size(); j++) {
				ArrayList<String> using = new ArrayList<>();
				ArrayList<String> with = new ArrayList<>();
				// int[] area = new int[2];
				float[] color = new float[4];
				String name = obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonString("name").getString();
				int lifeSpan = obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonNumber("lifeSpan").intValue();
				float speed = (float) obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonNumber("speed").doubleValue();
				float pollination = (float) obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonNumber("pollination")
						.doubleValue();
				int fertility = obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonNumber("fertility").intValue();
				// area[0] =
				// obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonArray("area").getInt(0);
				// area[1] =
				// obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonArray("area").getInt(1);
				color[0] = (float) obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonArray("color").getJsonNumber(0)
						.doubleValue();
				color[1] = (float) obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonArray("color").getJsonNumber(1)
						.doubleValue();
				color[2] = (float) obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonArray("color").getJsonNumber(2)
						.doubleValue();
				color[3] = 1;
				for (int k = 0; k < obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonArray("using").size(); k++) {
					using.add(obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonArray("using").getString(k));
				}
				for (int k = 0; k < obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonArray("with").size(); k++) {
					with.add(obj.getJsonArray(tiers[i]).getJsonObject(j).getJsonArray("with").getString(k));
				}
				a.add(new Bee(name, lifeSpan, speed, pollination, fertility, /* area, */ color, true, (i + 1) * 100,
						using, with, false, null));
			}
			queens.put(tiers[i], a);
		}
		return queens;
	}
}
