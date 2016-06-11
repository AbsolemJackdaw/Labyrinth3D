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

package gameStates.content;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Images {

	private static final int characterNumber = 2; // number of different characters / heads.  

	public static BufferedImage[] heads = new BufferedImage[characterNumber];
	public static BufferedImage[] bodies = new BufferedImage[characterNumber];
	public static BufferedImage[] arms = new BufferedImage[characterNumber];
	public static BufferedImage[] hair = new BufferedImage[characterNumber];

	public static BufferedImage[] heads_l = new BufferedImage[characterNumber];
	public static BufferedImage[] bodies_l = new BufferedImage[characterNumber];
	public static BufferedImage[] arms_l = new BufferedImage[characterNumber];
	public static BufferedImage[] hair_l = new BufferedImage[characterNumber];

	public static BufferedImage[] heads_r = new BufferedImage[characterNumber];
	public static BufferedImage[] bodies_r = new BufferedImage[characterNumber];
	public static BufferedImage[] arms_r = new BufferedImage[characterNumber];
	public static BufferedImage[] hair_r = new BufferedImage[characterNumber];

	public static BufferedImage[] bodies_b = new BufferedImage[characterNumber];
	public static BufferedImage[] hair_b = new BufferedImage[characterNumber];

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

	
	public static final int totalResources = 25;
	public static int resourcesLoaded;
	static int loader;

	public void load (){
		loadPlayerImages();
		loadSprites();
	}

	private void loadPlayerImages(){
			loadImages(heads, "resources/playerSprite/front/head/head");
			loadImages(bodies, "resources/playerSprite/front/body/body");
			loadImages(arms, "resources/playerSprite/front/arm/hands");
			loadImages(walls, "resources/Walls/Wall");
			loadImages(hair, "resources/playerSprite/front/hair/hair");
			loadImages(heads_l, "resources/playerSprite/sidel/head/head");
			loadImages(arms_l, "resources/playerSprite/sidel/arm/arm");
			loadImages(hair_l, "resources/playerSprite/sidel/hair/hair");
			loadImages(bodies_l, "resources/playerSprite/sidel/body/body");
			loadImages(heads_r, "resources/playerSprite/sider/head/head");
			loadImages(arms_r, "resources/playerSprite/sider/arm/arm");
			loadImages(hair_r, "resources/playerSprite/sider/hair/hair");
			loadImages(bodies_r, "resources/playerSprite/sider/body/body");

			loadImages(hair_b, "resources/playerSprite/back/hair/hair");
			loadImages(bodies_b, "resources/playerSprite/back/body/body");
	}

	private void loadSprites(){
			hud_elements = loadSprite("resources/Hud/hudElements.png");
			statue = loadSprite("resources/entity/statue.png");
			card = loadSprite("resources/entity/card.png");
			dried_head = loadSprite("resources/entity/dried_head.png");
			unicorn_blood = loadSprite("resources/entity/unicorn_blood.png");
			shade = loadSprite("resources/Hud/shade.png");
			hud_shade = loadSprite("resources/Hud/hud_black.png");
			hud = loadSprite("resources/Hud/hud.png");
			portal = loadSprite("resources/entity/portal/portal.png");
			loadImages(portalAnim,"resources/entity/portal/portal");
			backGroundMain = loadSprite("resources/background/mainPlace.png");
	}

	private void loadImages(BufferedImage[] list, String p) {

		for(int i = 0; i < list.length; i++){

			String path =p+"_"+i+".png";

			BufferedImage tempImg = null;

			try {
				tempImg = ImageIO.read(new File(path));
			} catch (IOException e) {
				e.printStackTrace();
			}

			if(tempImg != null)
				list[i] = tempImg;
		}

		resourcesLoaded++;
		loader++;
	}

	public BufferedImage loadSprite(String path) {

		BufferedImage tempImg = null;

		try {
			tempImg = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(path);
		}

		resourcesLoaded++;
		loader++;

		if(tempImg != null)
			return tempImg;

		System.out.println("image " +path+ " could not be loaded");
		return null;

	}
}
