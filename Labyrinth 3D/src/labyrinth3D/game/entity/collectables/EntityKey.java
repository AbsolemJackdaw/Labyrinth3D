package labyrinth3D.game.entity.collectables;

import labyrinth3D.game.playerdata.PlayerData;
import labyrinth3D.rscMngr.Texture;
import labyrinth3D.rscMngr.TextureLoader;

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
			return TextureLoader.tex_sport;
	}
}
