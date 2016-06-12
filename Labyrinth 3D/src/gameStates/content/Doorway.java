package gameStates.content;

import java.awt.Rectangle;


public class Doorway{

	public static final int DOOR1 = 0;
	public static final int DOOR2 = 1;
	public static final int DOOR3 = 2;
	public static final int DOOR5 = 3;
	public static final int DOORSMITH = 4;
	
	private int id;
	private Rectangle rectangle;
	
	public Doorway(int size, int id, int x, int y) {
		rectangle = new Rectangle(x, y, size, size);
	}
	
	public int getId() {
		return id;
	}
	
	public Rectangle getBoundingBox() {
		return rectangle;
	}
}
