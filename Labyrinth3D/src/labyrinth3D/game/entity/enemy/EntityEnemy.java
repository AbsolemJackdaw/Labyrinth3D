package labyrinth3D.game.entity.enemy;

import labyrinth3D.game.entity.Entity;
import labyrinth3D.rscMngr.Texture;
import labyrinth3D.rscMngr.TextureLoader;

public class EntityEnemy extends Entity {

	
	public EntityEnemy(int size, int id) {
		super(size, id);
	}
	
	@Override
	public Texture getTexture() {
		return TextureLoader.tex_enemytest;
	}
	
	public void hit() {
		
		System.out.println("Entity is hurt ! aawtch");
	}

}
