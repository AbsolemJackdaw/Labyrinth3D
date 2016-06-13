package labyrinth3D.game.entity.collectables;


import static labyrinth3D.gamestates.content.ImageLoader.dried_head;
import static labyrinth3D.gamestates.content.ImageLoader.statue;
import static labyrinth3D.gamestates.content.ImageLoader.unicorn_blood;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import labyrinth3D.game.entity.Entity;

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
			return 0;
		case HEAD:
			return 2;
		case UNICORN_BLOOD:
			return 1;
		default : 
			return 0;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
	}
}
