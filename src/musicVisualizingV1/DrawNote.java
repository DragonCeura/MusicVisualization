package musicVisualizingV1;

import java.awt.Color;
import java.awt.Graphics2D;

public class DrawNote {

	private int x, y, width, height;
	private float offtime = 0;
	private Color color;
	
	public DrawNote() {
		x = y= width = height = 0;
		color = Color.black;
	}
	
	public DrawNote(int x, int y, int width, int height, Color color) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setColor(color);
	}
	
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.fillOval(x, y, width, height);
	}
	
	public float getOffTime() {
		return offtime;
	}
	
	public void setOffTime(float time) {
		this.offtime = time;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
}
