package labyrinth3D.rscMngr;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TextureLoader {

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
	
	private Texture load(String loc, int size){
		
		int[] pixels = new int[size * size];
		
		try {
			BufferedImage image = ImageIO.read(TextureLoader.class.getClass().getResourceAsStream(loc));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new Texture(size);
	}

	public void loadTextures(){

		tex_default = load("/default.png", 64);
		
		tex_statue = load("/entity/statue.png", 64);
		tex_unicorn_blood = load("/entity/unicorn_blood.png", 64);
		tex_head = load("/entity/head.png", 64);
		
		tex_portal = load("/entity/portal.png", 64);
		tex_portal_1 = load("/entity/portal_active1.png", 64);
		tex_portal_2 = load("/entity/portal_active2.png", 64);
		tex_portal_3 = load("/entity/portal_active3.png", 64);
		
		flat_stone = load("/mazeTextures/flat_stone.png", 64);
		brickStone = load("/mazeTextures/wall_bricks.png", 64);
		brickStone1 = load("/mazeTextures/wall_bricks_1.png", 64);
		brickStone2 = load("/mazeTextures/wall_bricks_2.png", 64);
		brickStone3 = load("/mazeTextures/wall_bricks_3.png", 64);
		brickStone4 = load("/mazeTextures/wall_bricks_4.png", 64);
		brickStone5 = load("/mazeTextures/wall_bricks_5.png", 64);

		tex_warfsword = load("/entity/warfsword.png", 128);
		
		tex_enemytest = load("/entity/enemy/enemytest.png", 64);

		tex_ladder = load("/entity/ladder.png", 64);
		tex_ladder_sport1 = load("/entity/ladder_sport1.png", 64);
		tex_ladder_sport2 = load("/entity/ladder_sport2.png", 64);
		tex_ladder_sport3 = load("/entity/ladder_sport3.png", 64);
		
		tex_sport = load("/entity/sport.png", 64);

		tex_feather = load("/entity/black_feather.png", 64);
		tex_cape = load("/entity/cape.png", 64);
		tex_grail = load("/entity/grail.png", 64);
		tex_scythe = load("/entity/scythe.png", 64);
	}
	
}
