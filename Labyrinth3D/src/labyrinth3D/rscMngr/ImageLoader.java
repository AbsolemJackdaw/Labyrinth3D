package labyrinth3D.rscMngr;


import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {

	public static BufferedImage[] walls = new BufferedImage[12];

	public static BufferedImage shade; 
	public static BufferedImage hud_shade;
	public static BufferedImage hud_elements;
	public static BufferedImage hud;

	public static BufferedImage card;

	public static BufferedImage portal;
	public static BufferedImage[] portalAnim = new BufferedImage[3];

	public static BufferedImage statue;
	public static BufferedImage dried_head;
	public static BufferedImage unicorn_blood;

	public static BufferedImage backGroundMain;
	public static BufferedImage smithyBackground;
	public static BufferedImage crashBackGround;

	public static BufferedImage player;
	public static BufferedImage[] player_walking = new BufferedImage[4];
	public static BufferedImage smith;
	public static BufferedImage bubble;
	public static BufferedImage bubble_empty;

	public static BufferedImage shadow;

	public static BufferedImage warfSword_fp;
	public static BufferedImage warfsword;
	public static BufferedImage fp_weapons;

	public static BufferedImage default_image;

	public static BufferedImage sport;
	public static BufferedImage feather;
	public static BufferedImage cape;
	public static BufferedImage grail;
	public static BufferedImage scythe;

//	public static BufferedImage[] intro = new BufferedImage[191];


	public void load (){
		loadImages();
	}

	private void loadImages(){
		loadImages(walls, "/walls/Wall");
		loadImages(player_walking, "/player/cycle");

		default_image = loadSprite("/default.png");

		hud_elements = loadSprite("/hud/hudElements.png");
		shade = loadSprite("/hud/shade.png");
		hud_shade = loadSprite("/hud/hud_black.png");
		hud = loadSprite("/hud/hud.png");
		
		backGroundMain = loadSprite("/background/mainPlace.png");
		smithyBackground = loadSprite("/background/smithy.png");
		crashBackGround = loadSprite("/background/stranded.png");

		card = loadSprite("/entity/card/card.png");
		statue = loadSprite("/entity/card/statue.png");
		dried_head = loadSprite("/entity/card/dried_head.png");
		unicorn_blood = loadSprite("/entity/card/unicorn_blood.png");
		sport = loadSprite("/entity/card/sport.png");
		feather = loadSprite("/entity/card/black_feather.png");
		grail = loadSprite("/entity/card/grail.png");
		cape = loadSprite("/entity/card/cape.png");
		scythe= loadSprite("/entity/card/scythe.png");

		player = loadSprite("/player/player_hurt.png");
		smith = loadSprite("/entity/reaper32.png");

		bubble = loadSprite("/hud/bubble.png");
		bubble_empty = loadSprite("/hud/bubble_empty.png");
		shadow = loadSprite("/entity/shadow.png");

		warfSword_fp = loadSprite("/hud/first_person_warfSword.png");
		warfsword = loadSprite("/entity/card/warfsword.png");
		fp_weapons = loadSprite("/hud/first_person_weapons.png");
		
//		loadSequence(intro);
		
	}

	private void loadImages(BufferedImage[] list, String p) {

		for(int i = 0; i < list.length; i++){

			String path =p+"_"+i+".png";

			BufferedImage tempImg = null;

			try {
				tempImg = ImageIO.read(ImageLoader.class.getClass().getResourceAsStream(path));
			} catch (IOException e) {
				e.printStackTrace();
			}

			if(tempImg != null)
				list[i] = tempImg;
		}

	}

	public BufferedImage loadSprite(String path) {

		BufferedImage tempImg = null;

		try {
			tempImg = ImageIO.read(ImageLoader.class.getClass().getResourceAsStream(path));
		} catch (IOException e) {
			System.out.println(path);
			e.printStackTrace();
		}

		if(tempImg != null)
			return tempImg;

		System.out.println("image " +path+ " could not be loaded");
		return null;

	}
}
