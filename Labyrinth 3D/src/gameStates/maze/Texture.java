package gameStates.maze;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Texture {
	public int[] pixels;
	private String loc;
	public final int SIZE;
	
	public Texture(String location, int size) {
		loc = location;
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		load();
	}
	
	private void load() {
		try {
			BufferedImage image = ImageIO.read(Texture.class.getClass().getResourceAsStream(loc));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Texture wood = new Texture("/wood.png", 64);
	public static Texture brick = new Texture("/redbrick.png", 64);
	public static Texture bluestone = new Texture("/bluestone.png", 64);
	public static Texture stone = new Texture("/greystone.png", 64);
	public static Texture statue = new Texture("/statue.png", 64);
	public static Texture pixelTest = new Texture("/pixel.png", 4);
	public static Texture walls = new Texture("/walls.png", 8);

}

