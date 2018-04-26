package fr.armenari.beenetics.main.guis;

import org.lwjgl.opengl.Display;

public class Notification {

	private float y;
	private float time;
	private int s;
	private boolean finished;
	private String msg;
	private float[] color;

	public Notification() {
		setFinished(true);
	}

	public void launch(String msg, float[] color) {
		setFinished(false);
		y = -32;
		time = 0;
		s = 1;
		this.setMsg(msg);
		this.setColor(color);
	}

	public void animate(String msg, float[] color) {
		int errorMsgQuadSize = 32;
		int errorMsgTextSize = 24;
		GUI.color(color[0], color[1], color[2], color[3]);
		GUI.drawQuad(0, (int) this.y, Display.getWidth(), errorMsgQuadSize);
		GUI.color(1, 1, 1, 1);
		GUI.drawString(msg, Display.getWidth() / 2 - errorMsgTextSize * msg.length() / 2, 7 + y, errorMsgTextSize);
		if (this.y >= 0 && this.time <= 100) {
			this.s = 0;
		}
		this.y += this.s;
		if (time > 100) {
			this.s = -1;
		}
		this.time++;
		if (time > 250) {
			setFinished(true);
		}
	}

	public boolean isFinished() {
		return finished;
	}

	public Notification setFinished(boolean finished) {
		this.finished = finished;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public float[] getColor() {
		return color;
	}

	public void setColor(float[] color) {
		this.color = color;
	}
}
