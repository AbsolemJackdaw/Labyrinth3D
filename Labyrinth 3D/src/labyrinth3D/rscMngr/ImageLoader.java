//package loading;
//
////picked up this piece of code from TBOL
//
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//
//import javax.imageio.ImageIO;
//
//
//public class Images {
//
//	public static Images instance;
//
//	public Images() {
//		instance = this;
//	}
//
//	public static void init(){
//		instance = new Images();
//	}
//
//	public static BufferedImage loadImage(String path){
//		BufferedImage img = null;
//		try {
//			img = ImageIO.read(Images.class.getClass().getResourceAsStream(path));
//		} catch (final Exception e) {
//			e.printStackTrace();
//			System.out.println("Error loading graphics." + " " + path + " might be an invalid directory");
//			System.exit(0);
//		}
//
//		return img;
//	}
//
//	public static ArrayList<BufferedImage[]> loadMultiAnimation(int[] frames, int width, int height, String path){
//		try {
//			ArrayList<BufferedImage[]> sprites;
//
//			final BufferedImage spritesheet = ImageIO.read(Images.class.getClass().getResourceAsStream(path));
//
//			sprites = new ArrayList<BufferedImage[]>();
//			for (int i = 0; i < frames.length; i++) {
//
//				final BufferedImage[] bi = new BufferedImage[frames[i]];
//
//				for (int j = 0; j < frames[i]; j++)
//					bi[j] = spritesheet.getSubimage(j * width, i * height, width, height);
//				sprites.add(bi);
//			}
//			return sprites;
//
//		} catch (final Exception e) {
//			e.printStackTrace();
//			System.out.println("Failed to load " + path + ". Shutting down system.");
//			System.exit(0);
//		}
//		return null;
//	}
//
//	public static BufferedImage[] loadMultiImage(String s, int x, int y, int sizeX, int sizeY, int subImages) {
//		BufferedImage[] ret;
//		try {
//			final BufferedImage spritesheet = ImageIO.read(Images.class.getResourceAsStream(s));
//
//			ret = new BufferedImage[subImages];
//
//			for (int i = 0; i < subImages; i++)
//				ret[i] = spritesheet.getSubimage(i * x, y, sizeX, sizeY);
//
//			return ret;
//		} catch (final Exception e) {
//			e.printStackTrace();
//			System.out.println("Error loading graphics." + " " + s + " might be an invalid directory");
//			System.exit(0);
//		}
//		return null;
//	}
//	
////	public static BufferedImage[] loadColoredMultiImage(String s, int x, int y, int sizeX, int sizeY, int subImages, float r, float g, float b) {
////		BufferedImage[] ret;
////		try {
////			final BufferedImage spritesheet = ImageIO.read(Images.class.getResourceAsStream(s));
////
////			ret = new BufferedImage[subImages];
////
////			for (int i = 0; i < subImages; i++)
////				ret[i] = Util.color(r, g, b, spritesheet.getSubimage(i * x, y, sizeX, sizeY));
////
////			return ret;
////		} catch (final Exception e) {
////			e.printStackTrace();
////			System.out.println("Error loading graphics." + " " + s + " might be an invalid directory");
////			System.exit(0);
////		}
////		return null;
////	}
//
//	public static BufferedImage[] loadMultiImage(String s, int x, int y, int subImages) {
//		return loadMultiImage(s, x, y, x, x, subImages);
//	}
//	
////	public static BufferedImage[] loadColoredMultiImage(String s, int x, int y, int subImages, Color color) {
////		return loadColoredMultiImage(s, x, y, x, x, subImages, (float)color.getRed()/255f,(float)color.getGreen()/255f,(float)color.getBlue()/255f);
////	}
//	
//	public static BufferedImage[] loadMultipleSingleImages(BufferedImage[] list, String p) {
//
//		for(int i = 0; i < list.length; i++){
//
//			String path =p+"_"+i+".png";
//
//			BufferedImage tempImg = null;
//
//			try {
//				tempImg = ImageIO.read(new File(path));
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//			if(tempImg != null)
//				list[i] = tempImg;
//		}
//		
//		return list;
//
//	}
//}

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

	public static BufferedImage player;
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



	public void load (){
		loadImages();
	}

	private void loadImages(){
		loadImages(walls, "/walls/Wall");

		default_image = loadSprite("/default.png");

		hud_elements = loadSprite("/hud/hudElements.png");
		shade = loadSprite("/hud/shade.png");
		hud_shade = loadSprite("/hud/hud_black.png");
		hud = loadSprite("/hud/hud.png");
		
		backGroundMain = loadSprite("/background/mainPlace.png");
		smithyBackground = loadSprite("/background/smithy.png");
		
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
