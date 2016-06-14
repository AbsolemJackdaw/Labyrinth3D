package labyrinth3D.game.entity.collectables;

import labyrinth3D.rscMngr.Texture;

public class EntityFeather extends EntityAid {

	public EntityFeather(int size) {
		super(size, FEATHER);
	}

	@Override
	public Texture getTexture() {
		return Texture.tex_feather;
	}
}
