package fr.armenari.beenetics.main.guis;

import static org.lwjgl.opengl.GL11.GL_POINT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import fr.armenari.beenetics.main.utils.Utils;

public class GUI {
	
	public static int count = 0;
	private static Texture font = Texture.loadTexture("/font.png");
	public static String chars = "abcdefghijklmnopqrstuvwxyz " + "0123456789:!?.,()€+-/*#|{} "
			+ "=%@°                       " + "                           " + "";

	public static void drawString(String msg, float x, float y, int size) {
		msg = msg.toLowerCase();
		glEnable(GL_TEXTURE_2D);
		font.bind();
		glBegin(GL_QUADS);
		for (int i = 0; i < msg.length(); i++) {
			char c = msg.charAt(i);
			int offs = i * size;
			charData(c, x + offs, y, size);
		}

		glEnd();
		font.unbind();
		glDisable(GL_TEXTURE_2D);
	}

	public static String randomMsg() {
		String msg = "";
		for (int i = 0; i < (int) Utils.random(5, 7); i++) {
			msg += chars.charAt((int) Utils.random(0, 26));
		}
		return msg;
	}

	public static void pushMatrix() {
		glPushMatrix();
	}

	public static void popMatrix() {
		glPopMatrix();
	}

	public static void translate(float x, float y) {
		glTranslatef(x, y, 0);
	}

	public static void rotate(double angle, float x, float y, float z) {
		glRotatef((float) angle, x, y, z);
	}

	public static void charData(char c, float f, float y, int size) {
		int i = chars.indexOf(c);
		int xo = i % 27;
		int yo = i / 27;
		glTexCoord2f((0 + xo) / 27.0f, (0 + yo) / 4.0f);
		glVertex2f(f, y);
		glTexCoord2f((1 + xo) / 27.0f, (0 + yo) / 4.0f);
		glVertex2f(f + size, y);
		glTexCoord2f((1 + xo) / 27.0f, (1 + yo) / 4.0f);
		glVertex2f(f + size, y + size);
		glTexCoord2f((0 + xo) / 27.0f, (1 + yo) / 4.0f);
		glVertex2f(f, y + size);
	}

	public static void drawQuad(int x, int y, int w, int h) {
		glBegin(GL_QUADS);
		glVertex2f(x, y);
		glVertex2f(x + w, y);
		glVertex2f(x + w, y + h);
		glVertex2f(x, y + h);
		glEnd();
	}

	public static void drawQuad(int x, int y, int size, int size2, float[] color) {
		glBegin(GL_QUADS);
		glColor4f(color[0], color[1], color[2], color[3]);
		glVertex2f(x, y);
		glVertex2f(x + size, y);
		glVertex2f(x + size, y + size2);
		glVertex2f(x, y + size2);
		glEnd();
	}

	public static void drawTexture(Texture tex, float f, float g, float x, float y, float[] color) {
		glEnable(GL_TEXTURE_2D);
		tex.bind();
		glBegin(GL_QUADS);
		glColor4f(color[0], color[1], color[2], color[3]);
		glTexCoord2f(0, 0);
		glVertex2f(f, g);
		glTexCoord2f(0, 1);
		glVertex2f(f, g + y);
		glTexCoord2f(1, 1);
		glVertex2f(f + x, g + y);
		glTexCoord2f(1, 0);
		glVertex2f(f + x, g);
		glEnd();
		tex.unbind();
		glDisable(GL_TEXTURE_2D);
	}

	public static void drawTexture(int tex, int x, int y, int w, int h) {
		int xo = tex % 8;
		int yo = tex / 8;
		glEnable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		glTexCoord2f((0 + xo) / 8.0f, (0 + yo) / 8.0f);
		glVertex2f(x, y);
		glTexCoord2f((0 + xo) / 8.0f, (0 + yo) / 8.0f);
		glVertex2f(x + w, y);
		glTexCoord2f((0 + xo) / 8.0f, (0 + yo) / 8.0f);
		glVertex2f(x + w, y + h);
		glTexCoord2f((0 + xo) / 7.0f, (0 + yo) / 8.0f);
		glVertex2f(x, y + h);
		glEnd();
		glDisable(GL_TEXTURE_2D);
	}

	public static boolean button(String name, int x, int y, int w) {
		return button(name, x, y, w, 32);
	}

	public static boolean button(String name, int x, int y, int w, int textStart) {
		boolean r = false;

		int mx = Mouse.getX();
		int my = Display.getHeight() - Mouse.getY();
		if (mx > x && mx < x + w && my > y && my < y + 32) {
			GUI.color(0.4f, 0.4f, 0.4f, 1.0f);
			while (Mouse.next()) {
				if (Mouse.isButtonDown(0)) {
					GUI.color(0.7f, 0.7f, 0.7f, 1.0f);

					r = true;
				}
			}
		} else {
			GUI.color(0.3f, 0.3f, 0.3f, 1.0f);
		}

		GUI.drawQuad(x, y, w, 32);
		GUI.drawQuad(x, y, w, 32);
		GUI.color(1f, 1f, 1f, 1.0f);

		GUI.drawString(name, x + textStart, y + 6, 24);

		return r;
	}

	public static boolean button(int x, int y, int w) {
		boolean r = false;

		int mx = Mouse.getX();
		int my = Display.getHeight() - Mouse.getY();
		if (mx > x && mx < x + w && my > y && my < y + 32) {
			while (Mouse.next()) {
				if (Mouse.isButtonDown(0)) {
					r = true;
				}
			}
		}
		GUI.drawQuad(x, y, w, w);
		GUI.drawQuad(x, y, w, w);
		GUI.color(1f, 1f, 1f, 1.0f);
		return r;
	}

	public static void color(float r, float g, float b, float a) {
		glColor4f(r, g, b, a);
	}

	public static void blob() {
		glBegin(GL_POINT);
		float xoff = 0;
		for (int a = 0; a < Math.PI * 2; a += 0.1) {
			int offset = (int) Utils.map(Utils.noise(xoff, 5, 0), 0, 1, -25, 25);
			float r = 150 + offset;
			float x = (float) (r * Math.cos(a));
			float y = (float) (r * Math.sin(a));
			glVertex2f(x, y);
			xoff += 0.1;
		}
		glEnd();
	}

}
