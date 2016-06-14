package labyrinth3D.rscMngr;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Texture {
	public int[] pixels;
	private String loc;
	public int SIZE;

	public static Texture tex_statue ;
	public static Texture tex_unicorn_blood ;
	public static Texture tex_head ;
	
	public static Texture tex_portal ;
	public static Texture tex_portal_1 ;
	public static Texture tex_portal_2 ;
	public static Texture tex_portal_3 ;
	
	public static Texture flat_stone ;
	public static Texture brickStone ;
	public static Texture brickStone1 ;
	public static Texture brickStone2 ;
	public static Texture brickStone3 ;
	public static Texture brickStone4 ;
	public static Texture brickStone5 ;
	
	public static Texture tex_warfsword ;

	public static Texture tex_enemytest;

	public static Texture tex_ladder ;
	public static Texture tex_ladder_sport1 ;
	public static Texture tex_ladder_sport2 ;
	public static Texture tex_ladder_sport3 ;
	
	public static Texture tex_default ;

	public static Texture tex_sport;
	
	public static Texture tex_feather;
	public static Texture tex_cape;
	public static Texture tex_grail;
	public static Texture tex_scythe;

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

		tex_default= new Texture("/default.png", 64);
		
		tex_statue = new Texture("/entity/statue.png", 64);
		tex_unicorn_blood = new Texture("/entity/unicorn_blood.png", 64);
		tex_head = new Texture("/entity/head.png", 64);
		
		tex_portal = new Texture("/entity/portal.png", 64);
		tex_portal_1 = new Texture("/entity/portal_active1.png", 64);
		tex_portal_2 = new Texture("/entity/portal_active2.png", 64);
		tex_portal_3 = new Texture("/entity/portal_active3.png", 64);
		
		flat_stone = new Texture("/mazeTextures/flat_stone.png", 64);
		brickStone = new Texture("/mazeTextures/wall_bricks.png", 64);
		brickStone1 = new Texture("/mazeTextures/wall_bricks_1.png", 64);
		brickStone2 = new Texture("/mazeTextures/wall_bricks_2.png", 64);
		brickStone3 = new Texture("/mazeTextures/wall_bricks_3.png", 64);
		brickStone4 = new Texture("/mazeTextures/wall_bricks_4.png", 64);
		brickStone5 = new Texture("/mazeTextures/wall_bricks_5.png", 64);

		tex_warfsword = new Texture("/entity/warfsword.png", 128);
		
		tex_enemytest = new Texture("/entity/enemy/enemytest.png", 64);

		tex_ladder = new Texture("/entity/ladder.png", 64);
		tex_ladder_sport1 = new Texture("/entity/ladder_sport1.png", 64);
		tex_ladder_sport2 = new Texture("/entity/ladder_sport2.png", 64);
		tex_ladder_sport3 = new Texture("/entity/ladder_sport3.png", 64);
		
		tex_sport = new Texture("/entity/sport.png", 64);

		tex_feather = new Texture("/entity/black_feather.png", 64);
		tex_cape = new Texture("/entity/cape.png", 64);
		tex_grail = new Texture("/entity/grail.png", 64);
		tex_scythe = new Texture("/entity/scythe.png", 64);
	}
}
