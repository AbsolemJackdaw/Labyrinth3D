package game.entity.collectables;

import static gameStates.content.Images.dried_head;
import static gameStates.content.Images.statue;
import static gameStates.content.Images.unicorn_blood;
import game.entity.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class EntityCollectible extends Entity{

	public static final int STATUE = 0;
	public static final int HEAD = 1;
	public static final int UNICORN_BLOOD = 2;

	public EntityCollectible(int size, int id) {
		super(size, id);

	}

	@Override
	public BufferedImage getTexture() {
		switch (id) {
		case STATUE:
			return statue;
		case HEAD:
			return dried_head;
		case UNICORN_BLOOD:
			return unicorn_blood;
		default : 
			return null;
		}
	}

	@Override
	public int getTextureIndex() {
		switch (id) {
		case STATUE:
			return 5;
		case HEAD:
			return 7;
		case UNICORN_BLOOD:
			return 6;
		default : 
			return 5;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
	}
}
