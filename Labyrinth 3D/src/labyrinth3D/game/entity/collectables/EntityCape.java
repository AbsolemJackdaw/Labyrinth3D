package labyrinth3D.game.entity.collectables;

import labyrinth3D.rscMngr.Texture;

public class EntityCape extends EntityAid {

	public EntityCape(int size) {
		super(size, CAPE);
	}

	@Override
	public Texture getTexture() {
		return Texture.tex_cape;
	}
}
