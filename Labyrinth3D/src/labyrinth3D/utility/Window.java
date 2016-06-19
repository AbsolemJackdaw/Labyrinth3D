package labyrinth3D.utility;

import java.awt.Dimension;
import java.awt.Toolkit;


public class Window {

	private static int screenWidth;
	private static int screenHeight;

	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public Window(){
		screenWidth = screenSize.width;
		screenHeight = screenWidth * 9 / 16;
	}
	
	public static int getWidth(){
		return screenWidth;
	}
	
	public static int getHeight(){
		return screenHeight;
	}
}
