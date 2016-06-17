package labyrinth3D.utility;

import labyrinth3D.engine.GamePanel;

public class Scale {

	/**screen has to be bigger then original size : 1024x576*/
	private static float scale = (float)GamePanel.W / 1024f ;
	
	public static int scale(float nr) {
		return (int)(nr*scale);
	}
}
