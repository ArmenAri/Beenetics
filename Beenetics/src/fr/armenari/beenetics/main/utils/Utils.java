package fr.armenari.beenetics.main.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.List;
import java.util.Random;

import fr.armenari.beenetics.main.game.Game;
import fr.armenari.beenetics.main.game.Inventory;
import fr.armenari.beenetics.main.items.Item;
import fr.armenari.beenetics.main.machines.Machine;

public class Utils {

	static final int PERLIN_YWRAPB = 4;
	static final int PERLIN_YWRAP = 1 << PERLIN_YWRAPB;
	static final int PERLIN_ZWRAPB = 8;
	static final int PERLIN_ZWRAP = 1 << PERLIN_ZWRAPB;
	static final int PERLIN_SIZE = 4095;

	static int perlin_octaves = 4; // default to medium smooth
	static float perlin_amp_falloff = 0.5f; // 50% reduction/octave

	// [toxi 031112]
	// new vars needed due to recent change of cos table in PGraphics
	static int perlin_TWOPI;
	static int perlin_PI;
	static float[] perlin_cosTable;
	static float[] perlin;

	static Random perlinRandom;
	private static float[] sinLUT;
	private static float[] cosLUT;

	public static float DEG_TO_RAD = 0.01745329238474369f;

	static float SINCOS_PRECISION = 1.0f;
	static int SINCOS_LENGTH = (int) (360.0 / SINCOS_PRECISION);

	private static String savingDir = System.getenv("Appdata") + File.separator + ".beenetics" + File.separator
			+ "save";

	public static float random(float min, float max) {
		return (float) ((Math.random() * (max - min)) + min);
	}

	public static float noise(float x, float y, float z) {
		sinLUT = new float[SINCOS_LENGTH];
		cosLUT = new float[SINCOS_LENGTH];

		for (int i = 0; i < SINCOS_LENGTH; i++) {
			sinLUT[i] = (float) Math.sin(i * DEG_TO_RAD * SINCOS_PRECISION);
			cosLUT[i] = (float) Math.cos(i * DEG_TO_RAD * SINCOS_PRECISION);
		}

		if (perlin == null) {
			if (perlinRandom == null) {
				perlinRandom = new Random();
			}
			perlin = new float[PERLIN_SIZE + 1];
			for (int i = 0; i < PERLIN_SIZE + 1; i++) {
				perlin[i] = perlinRandom.nextFloat(); // (float)Math.random();
			}
			// [toxi 031112]
			// noise broke due to recent change of cos table in PGraphics
			// this will take care of it
			perlin_cosTable = cosLUT;
			perlin_TWOPI = perlin_PI = SINCOS_LENGTH;
			perlin_PI >>= 1;
		}

		if (x < 0)
			x = -x;
		if (y < 0)
			y = -y;
		if (z < 0)
			z = -z;

		int xi = (int) x, yi = (int) y, zi = (int) z;
		float xf = x - xi;
		float yf = y - yi;
		float zf = z - zi;
		float rxf, ryf;

		float r = 0;
		float ampl = 0.5f;

		float n1, n2, n3;

		for (int i = 0; i < perlin_octaves; i++) {
			int of = xi + (yi << PERLIN_YWRAPB) + (zi << PERLIN_ZWRAPB);

			rxf = noise_fsc(xf);
			ryf = noise_fsc(yf);

			n1 = perlin[of & PERLIN_SIZE];
			n1 += rxf * (perlin[(of + 1) & PERLIN_SIZE] - n1);
			n2 = perlin[(of + PERLIN_YWRAP) & PERLIN_SIZE];
			n2 += rxf * (perlin[(of + PERLIN_YWRAP + 1) & PERLIN_SIZE] - n2);
			n1 += ryf * (n2 - n1);

			of += PERLIN_ZWRAP;
			n2 = perlin[of & PERLIN_SIZE];
			n2 += rxf * (perlin[(of + 1) & PERLIN_SIZE] - n2);
			n3 = perlin[(of + PERLIN_YWRAP) & PERLIN_SIZE];
			n3 += rxf * (perlin[(of + PERLIN_YWRAP + 1) & PERLIN_SIZE] - n3);
			n2 += ryf * (n3 - n2);

			n1 += noise_fsc(zf) * (n2 - n1);

			r += n1 * ampl;
			ampl *= perlin_amp_falloff;
			xi <<= 1;
			xf *= 2;
			yi <<= 1;
			yf *= 2;
			zi <<= 1;
			zf *= 2;

			if (xf >= 1.0f) {
				xi++;
				xf--;
			}
			if (yf >= 1.0f) {
				yi++;
				yf--;
			}
			if (zf >= 1.0f) {
				zi++;
				zf--;
			}
		}
		return r;
	}

	private static float noise_fsc(float i) {
		// using bagel's cosine table instead
		return 0.5f * (1.0f - perlin_cosTable[(int) (i * perlin_PI) % perlin_TWOPI]);
	}

	public static double distance(float x1, float y1, float x2, float y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}

	public static float map(float value, float istart, float istop, float ostart, float ostop) {
		return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
	}

	public static void createSaveFiles() {
		File f1 = new File(savingDir + File.separator + DataBaseConnection.username + File.separator + "inv.sav");
		File f3 = new File(savingDir + File.separator + DataBaseConnection.username + File.separator + "mac.sav");
		File f5 = new File(savingDir + File.separator + DataBaseConnection.username + File.separator + "dna.sav");
		File f6 = new File(savingDir + File.separator + DataBaseConnection.username + File.separator + "days.sav");
		try {
			f1.getParentFile().mkdirs();
			f1.createNewFile();
			f3.getParentFile().mkdirs();
			f3.createNewFile();
			f5.getParentFile().mkdirs();
			f5.createNewFile();
			f6.getParentFile().mkdirs();
			f6.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void serializeArrayList(List<Object> arrays) {
		try {
			File file = new File(savingDir + File.separator + DataBaseConnection.username + File.separator + "inv.sav");
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(arrays.get(0));
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
		try {
			File file = new File(savingDir + File.separator + DataBaseConnection.username + File.separator + "mac.sav");
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(arrays.get(1));
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public static void deserializeArrayList(List<Object> arrays) {
		Object l = null;
		try {
			File file = new File(savingDir + File.separator + DataBaseConnection.username + File.separator + "inv.sav");
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			l = (List<Item>) in.readObject();
			Inventory.inventory = (List<Item>) l;

			in.close();
			fileIn.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("ERROR WHEN DESERIALIZING !");
			c.printStackTrace();
			return;
		}
		try {
			File file = new File(savingDir + File.separator + DataBaseConnection.username + File.separator + "mac.sav");
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			l = (List<Machine>) in.readObject();
			Machine.machines = (List<Machine>) l;

			fileIn.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("ArrayList not found !");
			c.printStackTrace();
			return;
		}

	}

	public static void saveDay() {
		try {
			File file = new File(
					savingDir + File.separator + DataBaseConnection.username + File.separator + "days.sav");
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(Game.days);

			fileOut.close();
			out.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public static void loadDay() {
		try {
			File file = new File(
					savingDir + File.separator + DataBaseConnection.username + File.separator + "days.sav");
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Game.days = (int) in.readObject();

			fileIn.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("ERROR WHEN DESERIALIZING !");
			c.printStackTrace();
			return;
		}
	}

	public static void saveDNA() {
		try {
			File file = new File(savingDir + File.separator + DataBaseConnection.username + File.separator + "dna.sav");
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(Game.DNAQuantity);

			fileOut.close();
			out.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public static void loadDNA() {
		try {
			File file = new File(savingDir + File.separator + DataBaseConnection.username + File.separator + "dna.sav");
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Game.DNAQuantity = (float) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("ERROR WHEN DESERIALIZING !");
			c.printStackTrace();
			return;
		}
	}

	public static String purge(String string) {
		string = string.toLowerCase();
		StringBuilder s = new StringBuilder(string.length());
		CharacterIterator it = new StringCharacterIterator(string);
		for (char ch = it.first(); ch != CharacterIterator.DONE; ch = it.next()) {
			if ((int) ch == 0) {
				s.append("");
			} else {
				s.append(ch);
			}
		}
		string = s.toString();
		return string;
	}
}
