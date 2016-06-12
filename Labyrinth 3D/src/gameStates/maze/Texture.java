package gameStates.maze;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Texture {
	public int[] pixels;
	private String loc;
	public int SIZE;
	
	public static Texture wood ;
	public static Texture brick ;
	public static Texture bluestone ;
	public static Texture stone ;
	public static Texture statue ;
	public static Texture pixelTest ;
	public static Texture walls ;
	public static Texture unicorn_blood ;
	public static Texture shriveled_head ;
	public static Texture portal ;
	public static Texture portal_1 ;
	public static Texture portal_2 ;
	public static Texture portal_3 ;
	public static Texture flat_stone ;
	public static Texture brickStone ;
	public static Texture brickStone1 ;
	public static Texture brickStone2 ;
	public static Texture brickStone3 ;
	public static Texture brickStone4 ;


	public Texture() {
		
	}
	
	public Texture(String location, int size) {
		loc = location;
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		load();
	}
	
	
	private void load(){
		try {
			BufferedImage image = ImageIO.read(Texture.class.getClass().getResourceAsStream(loc));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadTextures(){
		
		wood = new Texture("/wood.png", 64);
		brick = new Texture("/redbrick.png", 64);
		bluestone = new Texture("/bluestone.png", 64);
		stone = new Texture("/greystone.png", 64);
		statue = new Texture("/statue.png", 64);
		pixelTest = new Texture("/pixel.png", 4);
		walls = new Texture("/walls.png", 8);
		unicorn_blood = new Texture("/unicorn_blood.png", 64);
		shriveled_head = new Texture("/head.png", 64);
		portal = new Texture("/portal.png", 64);
		portal_1 = new Texture("/portal_active1.png", 64);
		portal_2 = new Texture("/portal_active2.png", 64);
		portal_3 = new Texture("/portal_active3.png", 64);
		flat_stone = new Texture("/flat_stone.png", 64);
		brickStone = new Texture("/wall_bricks.png", 64);
		brickStone1 = new Texture("/wall_bricks_1.png", 64);
		brickStone2 = new Texture("/wall_bricks_2.png", 64);
		brickStone3 = new Texture("/wall_bricks_3.png", 64);
		brickStone4 = new Texture("/wall_bricks_4.png", 64);

	}
}

