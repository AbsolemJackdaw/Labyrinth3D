package labyrinth3D.game.entity.collectables;

import labyrinth3D.rscMngr.Texture;
import labyrinth3D.rscMngr.TextureLoader;

public class EntityCape extends EntityAid {

	public EntityCape(int size) {
		super(size, CAPE);
	}

	@Override
	public Texture getTexture() {
		return TextureLoader.tex_cape;
	}
}
