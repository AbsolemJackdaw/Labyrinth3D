package labyrinth3D.game.entity.collectables;


import static labyrinth3D.rscMngr.ImageLoader.*;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import labyrinth3D.game.entity.Entity;

public class EntityCollectible extends Entity{

	public static final int STATUE = 0;
	public static final int HEAD = 1;
	public static final int UNICORN_BLOOD = 2;
	public static final int WARFSWORD = 3;

	public EntityCollectible(int size, int id) {
		super(size, id);
	}

	public BufferedImage getImage() {
		switch (id) {
		case STATUE:
			return statue;
		case HEAD:
			return dried_head;
		case UNICORN_BLOOD:
			return unicorn_blood;
		case WARFSWORD:
			return warfsword;
		default : 
			return null;
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	
	public void onPickUp() {
		
	}
}
