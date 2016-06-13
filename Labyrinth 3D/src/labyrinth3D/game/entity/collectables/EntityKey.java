package labyrinth3D.game.entity.collectables;

import static labyrinth3D.rscMngr.ImageLoader.Texture.tex_head;
import static labyrinth3D.rscMngr.ImageLoader.Texture.tex_statue;
import static labyrinth3D.rscMngr.ImageLoader.Texture.tex_unicorn_blood;
import labyrinth3D.game.playerdata.PlayerData;
import labyrinth3D.rscMngr.ImageLoader.Texture;

public class EntityKey extends EntityCollectible {

	public EntityKey(int size, int id) {
		super(size, id);
	}
	
	@Override
	public void onPickUp() {
		super.onPickUp();
		
		PlayerData.collectedKeys ++;
	}

	@Override
	public Texture getTexture() {
		switch (id) {
		case STATUE:
			return tex_statue;
		case HEAD:
			return tex_head;
		case UNICORN_BLOOD:
			return tex_unicorn_blood;
		default : 
			return null;
		}
	}
}
