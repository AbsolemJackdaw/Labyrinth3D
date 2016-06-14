package labyrinth3D.game.entity.enemy;

import java.awt.Graphics2D;

import labyrinth3D.game.entity.Entity;
import labyrinth3D.rscMngr.Texture;

public class EntityEnemy extends Entity {

	
	public EntityEnemy(int size, int id) {
		super(size, id);
	}
	
	@Override
	public void update() {
		super.update();
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
	}

	@Override
	public Texture getTexture() {
		return Texture.tex_enemytest;
	}
	
	public void hit() {
		
		System.out.println("Entity is hurt ! aawtch");
	}

}
